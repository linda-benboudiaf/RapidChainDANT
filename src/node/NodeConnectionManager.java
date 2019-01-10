package node;


import tcp.Connection;
import tcp.ConnectionManager;

public class NodeConnectionManager extends ConnectionManager {

	public NodeConnectionManager(Connection client, String motd, String id, String prefix) {
		super(client, motd, id, prefix);
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
