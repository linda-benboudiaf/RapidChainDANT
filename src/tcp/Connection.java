package tcp;

import java.io.IOException;
import java.net.Socket;

import common.IOTransmitter;

public class Connection extends IOTransmitter {
	protected Address addr;

	public Connection(String prefix) {
		super(prefix);
	}
	
	public Connection(Socket sock, String prefix) {
		this(prefix);
		this.setFromSocket(sock);
	}
	
	public Connection(Socket sock) {
		this(sock, "");
	}

	public Address getAddr() {
		return addr;
	}

	/**
	 * Set the input and output streams from a TCP socket
	 * @param sock
	 */
	public void setFromSocket(Socket sock) {
		try {
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
			this.addr = new Address(sock.getLocalAddress().getHostAddress(), sock.getLocalPort());
		} catch (IOException e) {
//			e.printStackTrace();
			this.error(e);
		}
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix + "/STREAM";
	}

}
