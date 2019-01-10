package node;

import tcp.Connection;
import tcp.ServerFactory;

public class NodeServerFactory extends ServerFactory {

	@Override
	public NodeConnectionManager createClientManager(Connection client, String motd, String id, int logLevel, String type) {
		return new NodeConnectionManager(client, motd, id, logLevel, type);
	}

}
