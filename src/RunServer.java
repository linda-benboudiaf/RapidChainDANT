import common.Log;
import node.NodeServer;

public class RunServer {

	public static void main(String[] args) {
		Log.setLevel(2);
		new Thread(new NodeServer(3023, 20)).start();
	}
}
