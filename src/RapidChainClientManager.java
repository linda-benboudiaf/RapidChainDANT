

import Tcp.ClientManager;
import Tcp.Connection;

public class RapidChainClientManager extends ClientManager {

	public RapidChainClientManager(Connection client, String motd, String id, int logLevel, String type) {
		super(client, motd, id, logLevel, type);
		this.exitCommands = new String[] { "exit", "quit" };
	}

	/**
	 * Dialog with the client
	 * 
	 * @param client
	 */
	@Override
	protected String response(String msg) {
		switch (msg) {
			case "game":
				return "what game ?";
			case "prout":
				return "oh yeah baby";
			default:
				return super.response(msg);
		}
	}
}
