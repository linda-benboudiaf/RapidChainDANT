package blockchain;

import java.util.*;
import com.google.gson.*;

import common.Log;
import common.Storable;

public class Pocket implements Storable {
	public ArrayList<Block> BlockChain = new ArrayList<Block>();
	public int level = 5;
	//L'idee est de crée une ArrayList afin de stocker les blocks puis l'importer dans un fichier JSON.
	
	public Pocket(int level) {
		this.level = level;
	}
	
	public Pocket() {
	}

	public void main() {
		// On mine 3 block + verification si la chaine est toujours valid.
		BlockChain.add(new Block("Im the first 1 Im the Genesis Block", "0"));
		BlockChain.get(0).mineBlock(level);

		BlockChain.add(new Block("Im the second 2", BlockChain.get(BlockChain.size() - 1).hash));
		Log.debug("Mining Block 2 ...");
		BlockChain.get(1).mineBlock(level);

		BlockChain.add(new Block("Im the third 3", BlockChain.get(BlockChain.size() - 1).hash));
		Log.debug("Mining Block 3 ...");
		BlockChain.get(2).mineBlock(level);

		// On verifie après le minage que la chaine est toujours valide.
		Log.debug("Is the chaine always valid " + isChainValid());

		/*
		 * On crée une instance GsonBuilder avec la méthode create(),
		 * SetPrettyPrinting() veut dire que le Output est un JSON.
		 */
		String BCJson = new GsonBuilder().setPrettyPrinting().create().toJson(BlockChain);
		Log.debug("\n BlockChain list:");
		Log.debug(BCJson);

	}
	
	/*
	 * Class isChainValid : Boolean. Une boucle qui parcours ArrayList afin de
	 * verifie: Le Hash du block précédent est égale a la valeur de "PreviousHash"
	 * du block en cours. Pareil pour le hash qui dèja stocké dans la chaine et
	 * celui qu'on vient de calculé.
	 */
	public Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[level]).replace('\0', '0');
		// On parcours la BlockChain.
		for (int i = 1; i < BlockChain.size(); i++) {
			currentBlock = BlockChain.get(i);
			previousBlock = BlockChain.get(i - 1);
			// On compare le hash stocké et le hash calculé
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				Log.debug("Current hash not valid, check hash values");
				return false;
			}
			// On compare le précédent hash et le hash précédent stocké dans le block en
			// cours.
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				Log.debug("Previous hashes not valid");
				return false;
			}
			// On verifie si le hash a été résolue
			if (!currentBlock.hash.substring(0, level).equals(hashTarget)) {
				Log.debug("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}

	public void addBlocks(Block newBlock) {
		newBlock.mineBlock(level);
		BlockChain.add(newBlock);
	}
	
	@Override
	public String toString() {
		return BlockChain.toString();
	}

	@Override
	public boolean isEmpty() {
		return BlockChain.isEmpty();
	}

	@Override
	public void overwrite(Storable obj) {
		Pocket p = (Pocket) obj;
		BlockChain = p.BlockChain;
	}
}
