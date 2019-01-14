package node;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Debuggable;
import common.Storable;
import tcp.Address;
import tcp.Protocol;
import tcp.ServerFactory;

@SuppressWarnings("serial")
public class Node extends Debuggable implements Serializable {
	protected Address addr;
	protected Long lastConnection;
	volatile protected List<Long> fails = new ArrayList<Long>();
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
	
	public void merge(Node node) {
	}
	
	public int nbFailsLast24h() {
		int nbFails = 0;
		Long now = new Date().getTime();
		for (Long date : fails) {
			long diff = now - date;
			int maxDiff = 1000 * 60 * 60 * 24;
			if (diff < maxDiff) {
				nbFails ++;
			}
		}
		return nbFails;
	}
	
	public Serializable request(Storable obj) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date().getTime();
			NodeConnection c = (NodeConnection) factory.createConn(s, this.prefix);
			c.send("pull");
			c.receive();
			c.setPrefix(prefix);
			c.send(obj.command());
			Serializable res = (Serializable) c.receive();
			
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
	
	public void send(Storable obj) {
		try (Socket s = new Socket()) {
			s.connect(addr.inet());
			lastConnection = new Date().getTime();
			NodeConnection c = (NodeConnection) factory.createConn(s, this.prefix);
			c.setPrefix(prefix);
			c.send("push");
			c.receive();
			c.send(obj.command());
			String ok = (String) c.receive();
			if (ok.equals(ok)) {
				c.send(obj);
				c.receive();
			}
			c.send(Protocol.exit);
			c.receive();
		} catch (IOException e) {
			this.fails.add(new Date().getTime());
			this.error(e);
		}
	}
	
	@Override
	public String toString() {
		return "node@" + this.addr;
	}
	
}
