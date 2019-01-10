package tcp;

import java.net.Socket;


public class ServerFactory {
	
	public ConnectionManager createClientManager(Connection client, String motd, String id, String prefix) {
		return new ConnectionManager(client, motd, id, prefix);
	}
	
	public Connection createClient(Socket sock, String prefix) {
		return new Connection(sock, prefix);
	}

}
