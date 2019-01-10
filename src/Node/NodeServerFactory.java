package Node;

import Tcp.Connection;
import Tcp.ServerFactory;

public class NodeServerFactory extends ServerFactory {

	@Override
	public NodeConnectionManager createClientManager(Connection client, String motd, String id, int logLevel, String type) {
		return new NodeConnectionManager(client, motd, id, logLevel, type);
	}

}
