package tcp;

import java.net.Socket;


public class ServerFactory {
	
	public ConnectionManager createConnManager(Connection client, String id, String prefix) {
		return new ConnectionManager(client, id, prefix);
	}
	
	public Connection createConn(Socket sock, String prefix) {
		return new Connection(sock, prefix);
	}

}
