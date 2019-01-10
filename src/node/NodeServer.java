package node;

import java.io.IOException;

import tcp.Server;

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
		
		// tests routetable
		try {
			this.routeTable = new RouteTable();
			this.routeTable.add(new Node("128.78.51.131", 3032));
			this.routeTable.add(new Node("192.23.34.55", 3032));
			this.routeTable.save();
			this.routeTable = new RouteTable();
			this.routeTable.load();
		} catch (IOException e) {
			this.error(e);
		}
		this.info(this.routeTable.toString());
	}
}
