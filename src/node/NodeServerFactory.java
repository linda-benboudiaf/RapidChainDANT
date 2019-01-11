package node;

import java.net.Socket;

import tcp.Connection;
import tcp.ServerFactory;

public class NodeServerFactory extends ServerFactory {

	@Override
	public NodeConnectionManager createConnManager(Connection client, String id,String prefix) {
		return new NodeConnectionManager(client, id, prefix);
	}
	
	@Override
	public Connection createConn(Socket sock, String prefix) {
		return new NodeConnection(sock, prefix);
	}

}
