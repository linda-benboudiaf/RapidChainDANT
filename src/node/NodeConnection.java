package node;
import java.io.IOException;
import java.net.Socket;

import common.JsonSerialStrategy;
import common.Requestable;
import common.SerialStrategy;
import common.Serializable;
import tcp.Connection;

public class NodeConnection extends Connection {
	protected SerialStrategy serialStrategy = new JsonSerialStrategy();

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
	
	public void send(Serializable obj) {
		super.send(serialStrategy.serialize(obj));
	}
	
	public Requestable receive(Requestable target) throws IOException{
		return (Requestable) serialStrategy.unserialize(receive(), target);
	}

}
