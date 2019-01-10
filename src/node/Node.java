package node;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import tcp.Connection;

public class Node {
	Address addr;
	protected Date lastConnection;

	public Node(String host, int port) {
		addr = new Address(host, port);
	}

	public Address getAddr() {
		return addr;
	}
	
	public Connection getConnection() {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date();
			return new Connection(s, 2);
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "node@" + this.addr;
	}
	

}
