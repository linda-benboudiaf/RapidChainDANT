package Tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Runnable class to create a client that can send and receive messages to a
 * server
 * 
 * @author Nicolas
 *
 */
public class Client extends Connection implements Runnable {
	protected String host = "localhost";
	protected int port = 3023;
	protected String exitCommand;

	public Client(int port) {
		this(port, "exit", 0);
	}

	public Client(int port, String exitCommand) {
		this(port, exitCommand, 0);
	}

	public Client(int port, String exitCommand, int logLevel) {
		super(logLevel, "CLIENT");
		this.port = port;
		this.exitCommand = exitCommand;
	}

	@Override
	public void run() {
		try (Socket c = new Socket(); Scanner sc = new Scanner(System.in);) {
			InetSocketAddress sock = new InetSocketAddress(this.host, this.port);
			c.connect(sock);
			this.debug("connection established");
			this.setFromSocket(c);
			new Thread(() -> {
				while (true) {
					try {
						System.out.println(this.receive());
					} catch (IOException e) {
						this.error(e);
					}
				}
			}).start();
			String msg = "";
			while (true) {
				msg = sc.next();
				send(msg);
			}

		} catch (Exception e) {
			this.error(e);
			e.printStackTrace();
		}
	}
}
