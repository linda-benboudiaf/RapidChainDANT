package node;

import tcp.Address;
import tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class NodeServer extends Server {

	public NodeServer(int port, int pool) {
		super(port, pool);
		this.factory = new NodeServerFactory();
	}
	
	public NodeServer(Address addr, int pool) {
		this(addr.getPort(), pool);
	}
}
