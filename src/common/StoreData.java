package common;

public class StoreData {
	protected Storable data;
	protected SerialStrategy strat;

	public StoreData(Storable data, SerialStrategy strat) {
		this.data = data;
		this.strat = strat;
	}

	public Storable getData() {
		return data;
	}

	public SerialStrategy getStrat() {
		return strat;
	}
}
