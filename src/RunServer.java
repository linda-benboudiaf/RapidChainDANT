
import common.Log;
import node.NodeServer;

public class RunServer {

	public static void main(String[] args) {
		
		//set log level to info
		Log.setLevel(1);
		new Thread(new NodeServer(3023, 20)).start();
	}
}
