package node;
import java.io.IOException;
import java.util.ArrayList;

import blockchain.Block;
import blockchain.Identity;
import blockchain.Pocket;
import blockchain.Sentance;
import common.Debuggable;
import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Store;
import tcp.Address;

public class App extends Debuggable implements Runnable {
	public static volatile Store store = new Store("data");
	protected boolean runtests = false;
	protected Address addr;
	protected static final int defaultPort = 3023;
	protected static volatile PeerTable peers;
	protected static volatile Pocket pocket;
	protected static volatile NodeServer server;
	protected static volatile ArrayList<Sentance> phrases=new ArrayList<Sentance>();
	static Identity keys; //cle privee et cle publique
	
	public static Identity getKeys() {
		return keys;
	}

	public App() {
		this (defaultPort);
	}

	public App(int port) {
		this.addr = new Address("localhost", port);
		this.prefix = "APP";
		if(keys.isEmpty()) {
			keys.generateKeyPair();
		}
		
		//set log level to info
		Log.start(store, 1);

	}
	
	public App(int port, boolean runtests) {
		this(port);
		Log.setLevel(2); // log level debug
		this.runtests = runtests;
	}

	public static PeerTable getPeers() {
		return peers;
	}

	public static Pocket getPocket() {
		return pocket;
	}

	public static ArrayList<Sentance> getPhrases() {
		return phrases;
	}

	@Override
	public void run() {
		new Thread(new NodeServer(addr, 20)).start();
		try {
			// tests routetable
			peers = new PeerTable();
			peers.initFromDns();
			store.register(peers, "peers", new PrettyJsonSerialStrategy());
			store.load("peers");			
			pocket = new Pocket(4);
			store.register(pocket, "pocket", new PrettyJsonSerialStrategy());
			store.load("pocket");
			
			new Thread(new PeriodicPulls(30)).start();
			new Thread(new NodeClient()).start();
			
			
			if (phrases.size() > 1 && pocket.canMineBlock()) {
				Sentance sentance = phrases.remove(0);
				Block block = new Block(sentance, pocket.highestValidHash());
				pocket.addBlock(block);
				peers.sendAll(block);
			}
			
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
			peers.add(new Node("localhost", 3023));
			store.save("peers");
			store.load("peers");
			
			//tests pocket
			pocket.tests();
			store.save("pocket");			
			Log.debug(peers);
			Log.debug(pocket);
			PeerTable test = (PeerTable) peers.requestAll(new PeerTable());
			Log.debug(test);
			Pocket test2 = (Pocket) peers.requestAll(new Pocket());
			Log.debug(test2);
			peers.putAll(test);
			store.save("peers");
			Log.debug(peers);
			
		} catch (IOException e) {
			Log.error(e);
		}
	}

}
