package blockchain;

import java.security.*;

import node.App;

public class Sentance {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public String value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.


	// Constructor:
	public Sentance(PublicKey from, String value) {
		this.sender = from;
		this.value = value;
	}
	
	public Sentance(String value) {
		this(App.getId().publicKey, value);
	}

	// This Calculates the transaction hash (which will be used as its Id)
	public String calculateHash() {
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + value);
	}

	// Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + value;
		signature = StringUtil.applyECDSASig(privateKey, data);
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + value;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	public boolean isEmpty() {
		if (value.isEmpty())
			return true;
		else
			return false;
	}
	public String toString() {
		return "data : "+this.value.toString()+" , sender public key : "+sender.toString();
	}

}