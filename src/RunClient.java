

import Tcp.Client;

public class RunClient {
	public static void main(String[] args) {
		new Thread(new Client(3023, "exit")).start();
	}

}
