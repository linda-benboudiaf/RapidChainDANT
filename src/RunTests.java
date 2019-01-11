import java.io.IOException;

import blockchain.Pocket;
import common.Log;
import common.Store;
import node.Node;
import node.NodeServer;
import node.RouteTable;

public class RunTests {
	public static volatile Store store  = new Store("data");
	protected static RouteTable routeTable;
	protected static Pocket pocket;

	public static void main(String[] args) {
		
		// set loglevel to debug
		Log.setLevel(2);
		
		// runserver
		new Thread(new NodeServer(3023, 20)).start();

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
			pocket = new Pocket(4);
			pocket.main();
			store.register(pocket, "pocket");
			store.save("pocket");
			store.load("pocket");
			
		} catch (IOException e) {
			Log.error(e);
		}
		
		
		Log.info(routeTable.toString());
		Log.info(pocket.toString());
		Log.debug(pocket.isChainValid());
		routeTable.requestAll("yo");
	}
}
