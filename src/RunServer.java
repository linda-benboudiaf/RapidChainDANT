import Node.NodeServer;

public class RunServer {

	public static void main(String[] args) {
		new Thread(new NodeServer(3023, 20, 3)).start();
	}
}
