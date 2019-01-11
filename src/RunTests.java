import java.io.IOException;

import blockchain.Pocket;
import common.JsonSerialStrategy;
import common.Log;
import common.PrettyJsonSerialStrategy;
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
//			routeTable.add(new Node("128.78.51.131", 3032));
			routeTable.add(new Node("localhost", 3023));
			store.register(routeTable, "routes", new JsonSerialStrategy());
			store.save("routes");
			store.load("routes");
			
			//tests pocket
			pocket = new Pocket(4);
			pocket.main();
			store.register(pocket, "pocket", new PrettyJsonSerialStrategy());
			store.save("pocket");
			store.load("pocket");
			
		} catch (IOException e) {
			Log.error(e);
		}
		
		
		Log.debug(routeTable);
		Log.debug(pocket);
		Log.debug(pocket.isChainValid());
		RouteTable test = (RouteTable) routeTable.requestAll(new RouteTable());
		Log.debug(test);
		Pocket test2 = (Pocket) routeTable.requestAll(new Pocket());
		Log.debug(test2);
		routeTable.putAll(test);
		Log.debug(routeTable);
	}
}
