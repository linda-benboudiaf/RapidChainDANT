package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import com.google.gson.*;

import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Storable;
import node.App;
import node.Node;

@SuppressWarnings("serial")
public class Pocket implements java.io.Serializable, Storable {
	public ArrayList<Head> heads = new ArrayList<Head>();
	public static int level = 5;
	
	// L'idee est de crée une ArrayList afin de stocker les blocks puis l'importer
	// dans un fichier JSON.

	public Pocket(int level) {
		Pocket.level = level;
	}

	public Pocket() {
	}

	/**
	 * Est sensée prendre le hash de la head la plus avancée
	 * 
	 * @return
	 */
	public String highestValidHash() {
		return highestValidHead().hash;
	}
	
	public Head highestValidHead() {
		for (Head head : heads) {
			if (head.fullyValid()) {
				return head;
			}
		}
		Head genesis = new Head(new Sentance("Im the first 1 Im the Genesis Block"), "0");
		addBlock(genesis);
		return genesis;
	}
	
	/**
	 * Fonction pour vérifier qu'un block peut être miné par ce noeud au bout de la plus longue chaine
	 * @return
	 */
	public boolean canMineBlock() {
		Head head = highestValidHead();
		return head.fullyValid();
	}

	public void addBlock(Block newBlock) {
		int i;
		boolean isNewHead = true;
		for  (i = 0; i < heads.size(); i++) {
			Block head = heads.get(i);
			if (newBlock.previousHash.equals(head.hash)) {
				isNewHead = false;
				break;
			}
		}
		try {
			App.store.register(newBlock, newBlock.file(), new PrettyJsonSerialStrategy());
			App.store.save(newBlock.file());
		} catch (IOException e) {
			Log.error(e);
		}
		Head newHead = new Head(newBlock);
		if (isNewHead) {
			heads.add(newHead);
			newHead.setChecked();
			newHead.setValid();
		} else {
			heads.set(i, newHead);
			isChainValid(newHead);
		}
		
	}

	public void tests() {

		addBlock(new Block(new Sentance("Im the second 2"), highestValidHash()));

		addBlock(new Block(new Sentance("Im the third 3"), highestValidHash()));

		// On verifie après le minage que la chaine est toujours valide.
		Log.debug("Is the chaine always valid " + isChainValid());

		/*
		 * On crée une instance GsonBuilder avec la méthode create(),
		 * SetPrettyPrinting() veut dire que le Output est un JSON.
		 */
		String BCJson = new GsonBuilder().setPrettyPrinting().create().toJson(getFullChain(highestValidHead()));
		System.out.println("BlockChain list:");
		System.out.println(BCJson);
		Log.debug("blockchain length: " + getFullChainHashes(highestValidHead()).size());

	}

	/**
	 * Récupère la longueur d'une chaine à partir de sa head.
	 * N'est pas encore utilisée...
	 * @param head
	 * @return
	 */
	public ArrayList<Block> getFullChain(Head head) {
		ArrayList<Block> prevs = new ArrayList<Block>();
		Stack<Block> stack = new Stack<Block>();
		Block current;
		prevs.add(head);
		stack.push(head);
		while(!stack.empty()) {
			current = stack.pop();
			try {
				Block prev = Block.get(current.previousHash);
				prevs.add(prev);
				if (!isGenesisBlock(prev)) {
					stack.push(prev);
				}
			} catch (IOException e) {
				Log.error(e);
			}
		}
		return prevs;

	}

	public HashSet<String> getFullChainHashes(Head head) {
		Stack<Block> stack = new Stack<Block>();
		Block current;
		Block previous;
		HashSet<String> hashes = new HashSet<>();
		hashes.add(head.hash);
		stack.push(head);
		while(!stack.empty()) {
			current = stack.pop();
			// try to get previous block from store
			try {
				previous = Block.get(current.previousHash);
				hashes.add(previous.hash);
				if (!isGenesisBlock(previous)) {
					stack.push(previous);
				}
			} catch (IOException e) {
				Log.error(e);
			}
		}
		return hashes;
	}

	/**
	 * Vérifie la validité de l'ensemble des chaines
	 * 
	 * @return
	 */
	public Boolean isChainValid() {
		for (Head head : heads) {
			if (!isChainValid(head)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fonction vérifiant la totalité d'une chaine en partant de sa head
	 * 
	 * @param current
	 * @return
	 */
	public Boolean isChainValid(Head head) {
		if (head.checked && head.valid) {
			Log.debug("Already checked");
			return true;
		}
		head.setChecked();
		Stack<Block> stack = new Stack<Block>();
		Block current;
		Block previous;
		// use explicit stack call instead of recursivity to save memory
		stack.push(head);
		while(!stack.empty()) {
			current = stack.pop();
			// try to get previous block from store
			try {
				previous = Block.get(current.previousHash);
			} catch (IOException e) {
				Log.error(e);
				return false;
			}
			// check if block is totally valid
			if (!isBlockValid(current, previous)) {
				return false;
			}
			// if not genesis block, check if previous blocks are valid
			if (!isGenesisBlock(previous)) {
				stack.push(previous);
			} 
		}
		head.setValid();
		return true;
	}

	/**
	 * Envoie l'ensemble des chaines
	 * 
	 * @return
	 */
	public void sendAllBlocks(Node node) {
		for (Block head : heads) {
			sendAllBlocks(head, node);
		}
	}

	/**
	 * Fonction envoyant la totalité d'une chaine en partant de sa head
	 * 
	 * @param current
	 * @return
	 */
	public void sendAllBlocks(Block head, Node node) {
		Stack<Block> stack = new Stack<Block>();
		Block previous;
		// use explicit stack call instead of recursivity to save memory
		node.send(head);
		stack.push(head);
		while(!stack.empty()) {
			head = stack.pop();
			// try to get previous block from store
			try {
				previous = Block.get(head.previousHash);
				node.send(previous);
			} catch (IOException e) {
				Log.error(e);
				break;
			}
			// if not genesis block, check if previous blocks are valid
			if (!isGenesisBlock(previous)) {
				stack.push(previous);
			} 
		}
	}

	private boolean isGenesisBlock(Block block) {
		return block.previousHash.equals("0");
	}

	/**
	 * Verifie un block par rapport à son précédent
	 * 1. le hash qui déjà stocké dans la chaine et celui qu'on vient de calculer.
	 * 2. la valeur de "PreviousHash" du block en cours.
	 * 3. la difficulté du hash
	 * 
	 * @param current
	 * @param previous
	 * @return true or false
	 */
	public boolean isBlockValid(Block current, Block previous) {
		Log.debug("Checking block: " + current);
		String hashTarget = new String(new char[level]).replace('\0', '0');
		// On compare le hash stocké et le hash calculé
		if (!current.hash.equals(current.calculateHash())) {
			Log.debug("Current hash not valid, check hash values");
			return false;
		}
		// On compare le précédent hash et le hash précédent stocké dans le block en
		// cours.
		if (!previous.hash.equals(current.previousHash)) {
			Log.debug("Previous hashes not valid");
			return false;
		}
		// On verifie si le hash a été résolue
		if (!current.hash.substring(0, level).equals(hashTarget)) {
			Log.debug("This block hasn't been mined");
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "heads: " + heads.toString();
	}

	@Override
	public boolean isEmpty() {
		return heads.isEmpty();
	}

	@Override
	public void overwrite(Serializable obj) {
		Pocket p = (Pocket) obj;
		heads = p.heads;
	}

	@Override
	public String command() {
		return "pocket";
	}
}
