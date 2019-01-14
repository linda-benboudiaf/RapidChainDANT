package tcp;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Callable;

import common.Debuggable;

public class ConnectionManager extends Debuggable implements Callable<String> {

	protected Connection client;
	protected String motd = "Welcome to this server";
	protected String id;
	protected String exitMessage = "disconnected";

	public ConnectionManager(Connection client, String id, String type) {
		super(type + "/CLIENT" + id);
		client.setPrefix(this.prefix);
		this.client = client;
		this.id = id;
	}

	public ConnectionManager(Connection client,  String id) {
		this(client, id, "");
	}

	/**
	 * Executes an action with the client
	 */
	@Override
	public String call() throws Exception {
		String msg = "";

		try {
			if (motd != null) {
				answer(motd);
			}
			
			// running server loop
			while (true) {
				msg = (String) client.receive();
				msg.toLowerCase();
				if (Protocol.exit.equals(msg)) {
					this.info("client disconnected");
					break;
				}
				answer(this.response(msg));
			}
		} catch (Exception e) {
			this.error(e);
		}
		
		return onSuccess(msg);

	}

	protected Serializable response(String msg) throws IOException {
		switch (msg) {
			case "yo":
				return "yo man";
			default:
				return "unknown command: " + msg;
		}
	}

	protected void answer(Serializable msg) {
		client.send(msg);
	}
	
	protected String onSuccess(String msg) {
		client.send(exitMessage);
		return exitMessage;
	}
}
