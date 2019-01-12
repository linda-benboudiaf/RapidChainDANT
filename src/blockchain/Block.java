
package blockchain;

import java.io.IOException;
import java.util.Date;
import node.Node;
import common.JsonSerialStrategy;
import common.Log;
import common.Serializable;
import common.Storable;
import common.Store;

/**
 * Class Block {@DATA: date, number}, {@PreviousHash}, {@HashOfCurrentBlock},
 */
public class Block implements Storable{

	protected String hash;
	protected String previousHash;
	protected String data;
	protected long timeStamp;
	protected int nonce;
	protected Node validator; // on garde une trace du validateur = node qui a validé le block
	protected int reputation; 
	
	/**
	 * Constructor for new blocks
	 * @param data
	 * @param previousHash
	 */
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.reputation=0;
		this.hash = calculateHash(); // sera appliquer une fois les autres valuers sont initialsé.
		mineBlock(Pocket.level);
	}
	
	/**
	 * Constructor for already minedBlocks
	 * @param store
	 * @param hash
	 * @throws IOException
	 */
	public Block(Store store, String hash) throws IOException {
		this.hash = hash;
		store.register(this, file(), new JsonSerialStrategy());
		store.load(file());
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
			data
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
		validator = bloc.validator;
	}


}
