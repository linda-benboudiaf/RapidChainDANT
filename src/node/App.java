package node;
import java.io.IOException;

import blockchain.Identity;
import blockchain.Pocket;
import common.Debuggable;
import common.JsonSerialStrategy;
import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Store;

public class App extends Debuggable implements Runnable {
	private Node node;
	public static volatile Store store = new Store("data");
	protected boolean runtests = false;
	protected static RouteTable routeTable;
	protected static Identity id; 
	protected static Pocket pocket;
	protected static NodeServer server;

	public App(int port) {
		this.node = new Node("localhost", port);
		this.prefix = "APP";
		
		//set log level to info
		Log.start(store, 1);

	}
	
	public App(int port, boolean runtests) {
		this(port);
		Log.setLevel(2);
		this.runtests = runtests;
	}

	@Override
	public void run() {
		new Thread(new NodeServer(node, 20)).start();
		try {
			// tests routetable
			routeTable = new RouteTable();
			store.register(routeTable, "routes", new JsonSerialStrategy());
			store.load("routes");

			id = new Identity(); 
			store.register(id, "identity", new PrettyJsonSerialStrategy());
			store.load("identity");
			if(id.isEmpty()) {
				id.generateKeyPair();
				store.save("identity");
			}
			
			pocket = new Pocket(4);
			store.register(pocket, "pocket", new PrettyJsonSerialStrategy());
			store.load("pocket");
			
			if (runtests) {
				runTests();
			}

		} catch (IOException e) {
			Log.error(e);
		}
	}
	
	protected void runTests() {

		// tests
		try {
			
			//tests routetable
//			routeTable.add(new Node("128.78.51.131", 3032));
			routeTable.add(new Node("localhost", 3023));
			store.save("routes");
			store.load("routes");
			
			//tests pocket
			pocket.main();
			store.save("pocket");
//			store.load("pocket");
			
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
