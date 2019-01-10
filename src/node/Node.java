package node;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import common.Debuggable;
import tcp.Connection;

public class Node extends Debuggable {
	Address addr;
	protected Date lastConnection;

	public Node(String host, int port) {
		addr = new Address(host, port);
	}

	public Address getAddr() {
		return addr;
	}
	
	public String request(String msg) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date();
			Connection c = new Connection(s);
			c.send(msg);
			String res = c.receive();
			this.debug(res);
			return res;
		} catch (IOException e) {
			this.error(e);
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "node@" + this.addr;
	}
	
}
