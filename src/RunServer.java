
public class RunServer {

	public static void main(String[] args) {
		new Thread(new RapidChainServer(3023, 20)).start();
	}
}
