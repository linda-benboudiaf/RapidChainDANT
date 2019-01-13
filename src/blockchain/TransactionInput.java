package blockchain;
/*
Transaction inputs are references to previous transaction outputs.
From this point on we will follow bitcoins convention and call unspent transaction outputs: UTXO’s.
This class will be used to reference TransactionOutputs that have not yet been spent. 
The transactionOutputId will be used to find the relevant TransactionOutput, 
allowing miners to check your ownership.
 * */

public class TransactionInput {
	public String transactionOutputId; // Reference to TransactionOutputs -> transactionId
	public TransactionOutput UTXO; // Contains the Unspent transaction output

	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

}
