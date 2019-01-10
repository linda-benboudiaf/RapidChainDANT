package node;

public class Ip {
	protected String address;

	public Ip(String address) {
		this.address = address;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		Ip p = (Ip) o;
		return address == p.address;
	}
	
	@Override
	public int hashCode() {
		return this.address.hashCode();
	}
	
	@Override
	public String toString() {
		return this.address;
	}

}
