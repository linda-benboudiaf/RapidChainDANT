package node;

import tcp.Connection;
import tcp.ServerFactory;

public class NodeServerFactory extends ServerFactory {

	@Override
	public NodeConnectionManager createClientManager(Connection client, String motd, String id,String prefix) {
		return new NodeConnectionManager(client, motd, id, prefix);
	}

}
