package node;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import common.Debuggable;
import common.Requestable;
import tcp.Protocol;
import tcp.ServerFactory;

public class Node extends Debuggable {
	protected Address addr;
	protected Date lastConnection;
	protected static ServerFactory factory = new NodeServerFactory();

	public Node(String host, int port) {
		addr = new Address(host, port);
		prefix = this.toString();
	}

	public Address getAddr() {
		return addr;
	}
	
	public Requestable request(Requestable obj) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date();
			NodeConnection c = (NodeConnection) factory.createConn(s, this.prefix);
			c.setPrefix(prefix);
			c.send(obj.command());
			Requestable res = c.receive(new RouteTable());
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
