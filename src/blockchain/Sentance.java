package blockchain;

import java.security.*;
import java.util.ArrayList;

public class Sentance {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public String value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

	private static int sequence = 0; // a rough count of how many transactions have been generated.

	// Constructor:
	public Sentance(PublicKey from, String value) {
		this.sender = from;
		this.value = value;
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + value + sequence);
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

}