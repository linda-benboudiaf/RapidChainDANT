package blockchain;

import java.io.IOException;
import java.util.*;
import com.google.gson.*;

import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Requestable;
import common.Serializable;
import common.Storable;
import node.App;

public class Pocket implements Storable, Requestable {
	public ArrayList<Block> heads = new ArrayList<Block>();
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
	public String lastHash() {
		return hiestHead().hash;
	}
	
	public Block hiestHead() {
		for (Block head : heads) {
			return head;
		}
		Block genesis = new Block("Im the first 1 Im the Genesis Block", "0");
		addBlock(genesis);
		return genesis;
	}

	public void addBlock(Block newBlock) {
		int i;
		boolean newHead = true;
		for  (i = 0; i < heads.size(); i++) {
			Block head = heads.get(i);
			if (newBlock.previousHash.equals(head.hash)) {
				newHead = false;
				break;
			}
		}
		try {
			App.store.register(newBlock, newBlock.file(), new PrettyJsonSerialStrategy());
			App.store.save(newBlock.file());
		} catch (IOException e) {
			Log.error(e);
		}
		if (newHead) {
			heads.add(newBlock);
		} else {
			heads.set(i, newBlock);
		}
	}

	public void tests() {

		addBlock(new Block("Im the second 2", lastHash()));

		addBlock(new Block("Im the third 3", lastHash()));

		// On verifie après le minage que la chaine est toujours valide.
		Log.debug("Is the chaine always valid " + isChainValid());

		/*
		 * On crée une instance GsonBuilder avec la méthode create(),
		 * SetPrettyPrinting() veut dire que le Output est un JSON.
		 */
		String BCJson = new GsonBuilder().setPrettyPrinting().create().toJson(getFullChain(hiestHead()));
		System.out.println("BlockChain list:");
		System.out.println(BCJson);
		Log.debug("blockchain length: " + getChainLength(hiestHead()));

	}

	/**
	 * Récupère la longueur d'une chaine à partir de sa head.
	 * N'est pas encore utilisée...
	 * @param head
	 * @return
	 */
	public ArrayList<Block> getFullChain(Block head) {
		ArrayList<Block> prevs = new ArrayList<Block>();
		Stack<Block> stack = new Stack<Block>();
		prevs.add(head);
		stack.push(head);
		while(!stack.empty()) {
			head = stack.pop();
			try {
				Block prev = Block.get(head.previousHash);
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

	public int getChainLength(Block head) {
		Stack<Block> stack = new Stack<Block>();
		Block previous;
		int count = 1;
		stack.push(head);
		while(!stack.empty()) {
			head = stack.pop();
			// try to get previous block from store
			try {
				previous = Block.get(head.previousHash);
				count ++;
				if (!isGenesisBlock(previous)) {
					stack.push(previous);
				}
			} catch (IOException e) {
				Log.error(e);
			}
		}
		return count;
	}

	/**
	 * Vérifie la validité de l'ensemble des chaines
	 * 
	 * @return
	 */
	public Boolean isChainValid() {
		for (Block head : heads) {
			if (!isChainValid(head)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fonction récursive vérifiant la totalité d'une chaine en partant de sa head
	 * 
	 * @param current
	 * @return
	 */
	public Boolean isChainValid(Block head) {
		Stack<Block> stack = new Stack<Block>();
		Block previous;
		// use explicit stack call instead of recursivity to save memory
		stack.push(head);
		while(!stack.empty()) {
			head = stack.pop();
			// try to get previous block from store
			try {
				previous = Block.get(head.previousHash);
			} catch (IOException e) {
				Log.error(e);
				return false;
			}
			// check if block is totally valid
			if (!isBlockValid(head, previous)) {
				return false;
			}
			// if not genesis block, check if previous blocks are valid
			if (!isGenesisBlock(previous)) {
				stack.push(previous);
			} 
		}
		return true;
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
