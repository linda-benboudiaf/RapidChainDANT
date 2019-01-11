import java.io.IOException;

import blockchain.Pocket;
import common.Debuggable;
import common.JsonSerialStrategy;
import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Store;
import node.Node;
import node.NodeServer;
import node.RouteTable;

public class App extends Debuggable implements Runnable {
	private Node node;
	public static volatile Store store = new Store("data");
	protected RouteTable routeTable;
	protected Pocket pocket;
	protected NodeServer server;

	public App(int port) {
		this.node = new Node("localhost", port);
		this.prefix = "APP";
		
		//set log level to info
		Log.start(store, 1);

	}

	@Override
	public void run() {
		Log.start(new Store("data"), 1);
		new Thread(new NodeServer(node, 20)).start();
		try {
			// tests routetable
			routeTable = new RouteTable();
			store.register(routeTable, "routes", new JsonSerialStrategy());
			store.load("routes");

			pocket = new Pocket(4);
			store.register(pocket, "pocket", new PrettyJsonSerialStrategy());
			store.load("pocket");

		} catch (IOException e) {
			Log.error(e);
		}
	}

}
