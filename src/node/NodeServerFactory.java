package node;

import tcp.Connection;
import tcp.ServerFactory;

public class NodeServerFactory extends ServerFactory {

	@Override
	public NodeConnectionManager createClientManager(Connection client, String id,String prefix) {
		return new NodeConnectionManager(client, id, prefix);
	}

}
