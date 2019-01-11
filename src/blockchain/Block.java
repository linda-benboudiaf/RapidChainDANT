
package blockchain;

import java.util.Date;
import node.Node;

import common.Log;

/**
 * Class Block {@DATA: date, number}, {@PreviousHash}, {@HashOfCurrentBlock},
 */
public class Block {

	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	private int nonce;
	public Node validator; // on garde une trace du validateur = node qui a validé le block
	

	//Block Constructor.
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); // sera appliquer une fois les autres valuers sont initialsé.
	}

	// Calculate new hash based on blocks contents
	// On applique le Hash sur toutes la données qu'on veut protéger: Date,
	// HashPrécédent, Data
	public String calculateHash() {
		String calculatedhash = Hash.ApplyHash(
			previousHash +
			Long.toString(timeStamp) +
			Integer.toString(nonce) +
			data
		);
		return calculatedhash;
	}

	// Increases no value until hash target is reached.
	public void mineBlock(int difficulty) {
		String target = Hash.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		Log.debug("Block Mined!!! : " + hash);
	}

	@Override
	public String toString() {
		return data.toString();
	}


}
