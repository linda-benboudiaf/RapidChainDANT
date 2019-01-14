package node;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Debuggable;
import common.Requestable;
import tcp.Address;
import tcp.Protocol;
import tcp.ServerFactory;

public class Node extends Debuggable {
	protected Address addr;
	protected Long lastConnection;
	protected List<Long> fails = new ArrayList<Long>();
	protected static ServerFactory factory = new NodeServerFactory();

	public Node(String host, int port) {
		this(new Address(host, port));
	}
	
	public Node(Address addr) {
		this.addr = addr;
		this.prefix = this.toString();
	}

	public Address getAddr() {
		return addr;
	}
	
	public int nbFailsThisWeek() {
		int nbFails = 0;
		Long now = new Date().getTime();
		for (Long date : fails) {
			long diff = now - date;
			int maxDiff = 1000 * 60 * 60 * 24 * 7;
			if (diff < maxDiff) {
				nbFails ++;
			}
		}
		return nbFails;
	}
	
	public Requestable request(Requestable obj) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date().getTime();
			NodeConnection c = (NodeConnection) factory.createConn(s, this.prefix);
			c.send("pull");
			c.receive();
			c.setPrefix(prefix);
			c.send(obj.command());
			Requestable res = c.receive(obj);
			this.debug(res);
			c.send(Protocol.exit);
			c.receive();
			return res;
		} catch (IOException e) {
			this.fails.add(new Date().getTime());
			this.error(e);
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "node@" + this.addr;
	}
	
}
