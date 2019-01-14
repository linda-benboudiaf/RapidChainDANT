package node;
import java.net.Socket;

import common.SerialStrategy;
import common.StdJavaSerialStrategy;
import tcp.Connection;

public class NodeConnection extends Connection {
	protected SerialStrategy serialStrategy = new StdJavaSerialStrategy();

	public NodeConnection(String prefix) {
		super(prefix);
		// TODO Auto-generated constructor stub
	}

	public NodeConnection(Socket sock, String prefix) {
		super(sock, prefix);
		// TODO Auto-generated constructor stub
	}

	public NodeConnection(Socket sock) {
		super(sock);
		// TODO Auto-generated constructor stub
	}

}
