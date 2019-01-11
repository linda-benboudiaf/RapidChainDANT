package node;


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
	protected String response(String msg) {
		switch (msg) {
			case "game":
				return "what game ?";
			case "iptables":
				RouteTable routeTable = new RouteTable();
				routeTable.add(new Node("10.0.25.113", 3032));
				routeTable.add(new Node("10.0.25.114", 3023));
				return new JsonSerialStrategy().serialize(routeTable);
			default:
				return super.response(msg);
		}
	}

	@Override
	protected String prompt() {
		return null;
	}
}
