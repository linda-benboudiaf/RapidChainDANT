package node;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import common.Debuggable;
import tcp.Protocol;
import tcp.Connection;

public class Node extends Debuggable {
	Address addr;
	protected Date lastConnection;

	public Node(String host, int port) {
		addr = new Address(host, port);
		prefix = this.toString();
	}

	public Address getAddr() {
		return addr;
	}
	
	public String request(String msg) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date();
			Connection c = new Connection(s, this.prefix + ".conn");
			c.send(msg);
			String res = c.receive();
			this.debug(res);
			c.send(Protocol.exit);
			c.receive();
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
