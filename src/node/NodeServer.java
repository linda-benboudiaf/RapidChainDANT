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

	public NodeServer(int port, int pool) {
		super(port, pool);
		this.motd = "Welcome to RapidChain server 12.7";
		this.factory = new NodeServerFactory();
	}
}
