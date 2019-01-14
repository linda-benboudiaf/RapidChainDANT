package node;


import java.io.IOException;
import java.io.Serializable;

import blockchain.Block;
import common.PrettyJsonSerialStrategy;
import tcp.Connection;
import tcp.ConnectionManager;

public class NodeConnectionManager extends ConnectionManager {

	public NodeConnectionManager(Connection client, String id, String prefix) {
		super(client, id, prefix);
		this.motd = null;
		App.peers.addAddr(client.getAddr());
	}

	/**
	 * Dialog with the client
	 * 
	 * @param client
	 */
	@Override
	protected Serializable response(String msg) throws IOException {
		NodeConnection c = (NodeConnection) this.client;
		if (msg.equals("pull")) {
			c.send("what");
			msg = (String) c.receive();
			switch (msg) {
				case "peers":
					return App.peers;
				case "pocket":
					return App.pocket;
				default:
					return super.response(msg);
			}
		} else if (msg.equals("push")) {
			c.send("what");
			msg = (String) c.receive();
			switch (msg) {
			case "peers":
				c.send("ok");
				PeerTable peers = (PeerTable) c.receive();
				App.peers.merge(peers);
				return "ok";
			case "pocket":
				return "nope";
			case "block":
				c.send("ok");
				Block block = (Block) c.receive();
				App.store.register(block, block.file(), new PrettyJsonSerialStrategy());
				App.store.save(block.file());
				return "ok";
			default:
				return super.response(msg);
			}
		} else {
			return super.response(msg);
		}
	}

}
