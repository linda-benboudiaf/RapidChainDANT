
import common.Log;
import common.Store;
import node.NodeServer;

public class RunServer {

	public static void main(String[] args) {
		
		//set log level to info
		Log.start(new Store("data"), 1);
		new Thread(new NodeServer(3023, 20)).start();
	}
}
