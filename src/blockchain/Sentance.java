package blockchain;
public class Sentance {
/**
 * Classe qui represente la donnee stockée dans les blocks
 */
	protected String value;

	// Constructor:
	public Sentance(String value) {
		this.value = value;
	}
	//Calcule le hash de la DATA
	public String calculateHash() {
		return StringUtil.applySha256(value);
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