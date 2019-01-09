package Tcp;

import java.io.IOException;
import java.net.Socket;

import Common.IOTransmitter;

public class Connection extends IOTransmitter {

	public Connection(int logLevel, String type) {
		super(logLevel);
		this.setType(type);
	}
	
	public Connection(Socket sock, int logLevel, String type) {
		this(logLevel, type);
		this.setFromSocket(sock);
	}
	
	public Connection(Socket sock, int logLevel) {
		this(sock, logLevel, "");
	}

	/**
	 * Set the input and output streams from a TCP socket
	 * @param sock
	 */
	public void setFromSocket(Socket sock) {
		try {
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
		} catch (IOException e) {
//			e.printStackTrace();
			this.error(e);
		}
	}
	
	public void setType(String type) {
		this.type = type + ".STREAM";
	}

}
