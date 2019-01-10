package node;

import java.net.InetSocketAddress;

public class Address {
	protected String host;
	protected int port;

	public Address(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public InetSocketAddress inet() {
		return new InetSocketAddress(host, port);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		Address a = (Address) o;
		return host.equals(a.host) && port == a.port;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();

	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}

}
