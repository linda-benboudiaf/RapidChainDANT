package node;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import blockchain.Identity;
import blockchain.Pocket;
import common.Debuggable;
import common.JsonSerialStrategy;
import common.Log;
import common.PrettyJsonSerialStrategy;
import common.Store;

public class App extends Debuggable implements Runnable {
	public static volatile Store store = new Store("data");
	protected boolean runtests = false;
	protected Address addr;
	protected static final int defaultPort = 3023;
	protected static RouteTable peers;
	protected static Identity id; 
	protected static Pocket pocket;
	protected static NodeServer server;
	
	public App() {
		this (defaultPort);
	}

	public App(int port) {
		this.addr = new Address("localhost", port);
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
		new Thread(new NodeServer(addr, 20)).start();
		try {
			// tests routetable
			peers = new RouteTable();
			store.register(peers, "peers", new JsonSerialStrategy());
			store.load("peers");

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
		
		initPeersFromDns();

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
//			store.load("pocket");
			
		} catch (IOException e) {
			Log.error(e);
		}
		
		
		Log.debug(peers);
		Log.debug(pocket);
		Log.debug(pocket.isChainValid());
		RouteTable test = (RouteTable) peers.requestAll(new RouteTable());
		Log.debug(test);
		Pocket test2 = (Pocket) peers.requestAll(new Pocket());
		Log.debug(test2);
		peers.putAll(test);
		Log.debug(peers);
	}
	
	protected void initPeersFromDns() {
		try {
            InetAddress[] ipAddr = InetAddress.getAllByName("blurchain.club1.fr");
            for(int i=0; i < ipAddr.length ; i++) {
                if (ipAddr[i] instanceof Inet4Address) {
                    Log.debug("IPv4 : " + ipAddr[i].getHostAddress());
                    peers.addIp(ipAddr[i].getHostAddress());
                }
            }
        } catch (UnknownHostException e) {
            Log.error(e);
        }
	}

}
