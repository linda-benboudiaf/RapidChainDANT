import java.io.IOException;

import blockchain.Pocket;
import common.JsonSerialStrategy;
import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Store;
import node.Node;
import node.NodeServer;
import node.RouteTable;

public class App implements Runnable {
	private Node node;
	public static volatile Store store = new Store("data");
	protected RouteTable routeTable;
	protected Pocket pocket;
	protected NodeServer server;

	public App(Node node) {
		this.node = node;

	}

	@Override
	public void run() {
		new Thread(new NodeServer(3023, 20)).start();
		try {
			// tests routetable
			routeTable = new RouteTable();
			// routeTable.add(new Node("128.78.51.131", 3032));
			routeTable.add(new Node("localhost", 3023));
			store.register(routeTable, "routes", new JsonSerialStrategy());
			store.load("routes");
			pocket = new Pocket(4);
			pocket.main();
			store.register(pocket, "pocket", new PrettyJsonSerialStrategy());
			store.save("pocket");
			store.load("pocket");

		} catch (IOException e) {
			Log.error(e);
		}
	}

}
