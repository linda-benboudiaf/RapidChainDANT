
package blockchain;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Date;

import node.App;
import common.Log;
import common.JsonSerialStrategy;
import common.Storable;


@SuppressWarnings("serial")
public class Block implements Serializable, Storable{

	protected String hash;
	protected String previousHash;
	protected Sentance data;
	protected long timeStamp;
	protected int nonce;
	protected String merkleRoot;
	
	/**
	 * Constructeur des nouveaux blocs
	 * @param data la phrase a ajouter
	 * @param previousHash le hash du bloc precedent
	 */
	public Block(Sentance data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); // sera appliqué une fois que les autres valeurs sont initialisées.
		mineBlock(Pocket.level);
	}

	/**
	 * Constructor for already mined blocks
	 * @param hash
	 */
	private Block(String hash) {
		this.hash = hash;
	}
	
	public Block() {
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public Sentance getData() {
		return data;
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

	// Calcule le hash en utilisant les données
	// On applique le Hash sur toutes la données qu'on veut protéger: Date,
	// HashPrécédent, Data
	public String calculateHash() {
		String calculatedhash = Hash.ApplyHash(
			previousHash +
			Long.toString(timeStamp) +
			Integer.toString(nonce) +
			merkleRoot
		);
		return calculatedhash;
	}

	// Increases no value until hash target is reached.
	public void mineBlock(int difficulty) {
		Log.debug("Mining Block: " + data);
		String target = Hash.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		merkleRoot=data.calculateHash();
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
		merkleRoot=bloc.merkleRoot;
	}

	@Override
	public String command() {
		return "block";
	}

}
