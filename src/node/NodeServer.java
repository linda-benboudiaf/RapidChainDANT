package node;

import java.io.IOException;

import blockchain.Pocket;
import common.Store;
import tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class NodeServer extends Server {
	public static volatile Store store  = new Store("data");
	protected RouteTable routeTable;
	protected Pocket pocket;

	public NodeServer(int port, int pool) {
		super(port, pool);
		this.motd = "Welcome to RapidChain server 12.7";
		this.factory = new NodeServerFactory();
		
		// tests
		try {
			
			//tests routetable
			routeTable = new RouteTable();
			routeTable.add(new Node("128.78.51.131", 3032));
			routeTable.add(new Node("localhost", 3023));
			store.register(routeTable, "routes");
			store.save("routes");
			store.load("routes");
			
			//tests pocket
			pocket = new Pocket();
			pocket.main();
			store.register(pocket, "pocket");
			store.save("pocket");
			store.load("pocket");
			
		} catch (IOException e) {
			this.error(e);
		}
		
		
		this.info(routeTable.toString());
		this.info(pocket.toString());
		this.debug(pocket.isChainValid());
	}
}
