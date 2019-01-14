package tcp;

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
		this(port, "exit");
	}

	public Client(int port, String exitCommand) {
		super("CLIENT");
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
					System.out.println(this.receive());
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
