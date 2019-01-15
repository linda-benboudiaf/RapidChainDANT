package blockchain;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import node.App;


@SuppressWarnings("serial")

/**
 * Classe qui represente la donnee stockée dans les blocks
 */
public class Sentance implements Serializable{
	public String value;
	//public PublicKey sender; // senders address/public key.
	public String sender; // senders address/public key.

	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.


	// Constructor:
	public Sentance(String value) {
		this.value = value;
		this.sender=StringUtil.getStringFromKey(App.getKeys().publicKey);
		

		if(signature==null)
			generateSignature(App.getKeys().privateKey);
	}
	//Calcule le hash de la DATA
	public String calculateHash() {
		return StringUtil.applySha256(value);
	}
	
	public void generateSignature(PrivateKey privateKey) {
		String data = sender + value;
		signature = StringUtil.applyECDSASig(privateKey, data);
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = sender + value;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	public boolean isEmpty() {
		if (value.isEmpty())
			return true;
		else
			return false;
	}

	public String toString() {
		return "data : " + this.value;
	}

}