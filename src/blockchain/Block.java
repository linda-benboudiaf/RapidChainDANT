
package blockchain;

import java.io.FileNotFoundException;
import java.util.Date;

import node.App;
import node.Node;
import common.JsonSerialStrategy;
import common.Log;
import common.Serializable;
import common.Storable;


public class Block implements Storable{

	protected String hash;
	protected String previousHash;
	protected Sentance data;
	protected long timeStamp;
	protected int nonce;
	
	/**
	 * Constructor for new blocks
	 * @param data
	 * @param previousHash
	 */
	public Block(Sentance data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); // sera appliquer une fois les autres valuers sont initialsé.
		mineBlock(Pocket.level);
	}

	/**
	 * Constructor for already mined blocks
	 * @param hash
	 */
	private Block(String hash) {
		this.hash = hash;
	}

	/**
	 * Get a block from the store
	 * @param hash
	 * @throws FileNotFoundException
	 */
	public static Block get(String hash) throws FileNotFoundException {
		Block b = new Block(hash);
		App.store.get(b, b.file(), new JsonSerialStrategy());
		return b;
	}
	
	public String file() {
		return "blocks/" + hash;
	}

	// Calculate new hash based on blocks contents
	// On applique le Hash sur toutes la données qu'on veut protéger: Date,
	// HashPrécédent, Data
	public String calculateHash() {
		String calculatedhash = Hash.ApplyHash(
			previousHash +
			Long.toString(timeStamp) +
			Integer.toString(nonce) +
			data.calculateHash() // calculateHash de la classe sentance
		);
		return calculatedhash;
	}

	// Increases no value until hash target is reached.
	public void mineBlock(int difficulty) {
		Log.debug("Mining Block: " + data);
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

	@Override
	public boolean isEmpty() {
		return data.isEmpty() ;
	}

	@Override
	public void overwrite(Serializable obj) {
		Block bloc = (Block) obj;
		hash = bloc.hash;
		previousHash = bloc.previousHash;
		data = bloc.data;
		timeStamp = bloc.timeStamp;
		nonce = bloc.nonce;
	}


}
