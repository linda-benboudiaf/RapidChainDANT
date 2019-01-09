package Blockchain;
import java.util.Date;
public class Block {

	public String hash;
	public String previousHash;
	private String data; //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int no; 
	//Block Constructor.
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //sera appliquer une fois les autres valuers sont initialsé.  
	}

	
	//Calculate new hash based on blocks contents
	// On applique le Hash sur toutes la données qu'on veut protéger: Date, HashPrécédent, Data
	public String calculateHash() {
		String calculatedhash = Hash.ApplyHash( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(no) + 
				data 
				);
		return calculatedhash;
	}
	
	//Increases no value until hash target is reached.
	public void mineBlock(int difficulty) {
		String target = Hash.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			no ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}



}

