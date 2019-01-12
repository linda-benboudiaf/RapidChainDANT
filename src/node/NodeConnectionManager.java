package node;


import java.io.IOException;

import common.JsonSerialStrategy;
import tcp.Connection;
import tcp.ConnectionManager;

public class NodeConnectionManager extends ConnectionManager {

	public NodeConnectionManager(Connection client, String id, String prefix) {
		super(client, id, prefix);
		this.motd = null;
	}

	/**
	 * Dialog with the client
	 * 
	 * @param client
	 */
	@Override
	protected String response(String msg) throws IOException {
		if (msg.equals("pull")) {
			client.send("what");
			msg = client.receive();
			switch (msg) {
				case "peers":
					return new JsonSerialStrategy().serialize(App.peers);
				case "pocket":
					return new JsonSerialStrategy().serialize(App.pocket);
				default:
					return super.response(msg);
			}
		} else if (msg.equals("push")) {
			client.send("what");
			msg = client.receive();
			switch (msg) {
			case "peers":
				return "ok";
			case "pocket":
				return "ok";
			default:
				return super.response(msg);
		}
		} else {
			return super.response(msg);
		}
	}

	@Override
	protected String prompt() {
		return null;
	}
}
