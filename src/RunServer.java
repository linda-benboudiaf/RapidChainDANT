import Tcp.Server;

public class RunServer {

	public static void main(String[] args) {
		new Thread(new Server(3023, 20)).start();
	}
}
