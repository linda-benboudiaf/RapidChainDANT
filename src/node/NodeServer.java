package node;

import java.io.IOException;

import common.Store;
import tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class NodeServer extends Server {
	protected Store store  = new Store();
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
			routeTable = new RouteTable();
			routeTable.add(new Node("128.78.51.131", 3032));
			routeTable.add(new Node("localhost", 3023));
			store.register(routeTable, "routes");
			store.save("routes");
			store.load("routes");
		} catch (IOException e) {
			this.error(e);
		}
		this.info(this.routeTable.toString());
	}
}
