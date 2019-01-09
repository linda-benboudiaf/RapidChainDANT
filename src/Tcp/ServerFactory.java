package Tcp;

import java.net.Socket;


public class ServerFactory {
	
	public ConnectionManager createClientManager(Connection client, String motd, String id, int logLevel, String type) {
		return new ConnectionManager(client, motd, id, logLevel, type);
	}
	
	public Connection createClient(Socket sock, int logLevel, String type) {
		return new Connection(sock, logLevel, type);
	}

}
