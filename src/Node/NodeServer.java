package Node;

import Tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class NodeServer extends Server {
	protected RouteTable routeTable;
	
	public NodeServer(int port, int pool) {
		this(port, pool, 1);
	}

	public NodeServer(int port, int pool, int logLevel) {
		super(port, pool, logLevel);
		this.motd = "Welcome to RapidChain server 12.7";
		this.factory = new NodeServerFactory();
		this.routeTable = new RouteTable();
	}
}
