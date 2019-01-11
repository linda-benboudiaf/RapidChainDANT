package node;

import tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class NodeServer extends Server {

	public NodeServer(int port, int pool) {
		super(port, pool);
		this.factory = new NodeServerFactory();
	}
	
	public NodeServer(Node node, int pool) {
		this(node.getAddr().port, pool);
	}
}
