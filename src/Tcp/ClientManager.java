package Tcp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import Common.Debuggable;

public class ClientManager extends Debuggable implements Callable<String> {

	protected Connection client;
	protected String motd;
	protected String id;
	protected String[] exitCommands = {"exit"};
	protected String exitMessage = "disconnected";

	public ClientManager(Connection client, String motd, String id, int logLevel, String type) {
		super(logLevel, type + ".CLIENT" + id);
		client.setType(this.type);
		this.client = client;
		this.motd = motd;
		this.id = id;
	}

	public ClientManager(Connection client, String motd, String id, int logLevel) {
		this(client, motd, id, logLevel, "");
	}

	public ClientManager(Connection client, String motd, String id) {
		this(client, motd, id, 0);
	}

	/**
	 * Executes an action with the client
	 */
	@Override
	public String call() throws Exception {
		String msg = "";

		try {
			answer(motd);
			// running server loop
			while (true) {
				msg = client.receive().toLowerCase();
				List<String> exitcommands = Arrays.asList(this.exitCommands);
				if (exitcommands.contains(msg)) {
					this.info("client disconnected");
					break;
				}
				answer(this.response(msg));
			}
		} catch (Exception e) {
//			e.printStackTrace();
			error(e);
		}
		
		return onSuccess(msg);

	}

	protected String response(String msg) {
		switch (msg) {
			case "yo":
				return "yo man";
			default:
				return "unknown command: " + msg;
		}
	}

	protected void answer(String msg) {
		client.send(msg + "\n" + this.prompt());
	}

	protected String prompt() {
		return "$>";
	}
	
	protected String onSuccess(String msg) {
		client.send(exitMessage);
		return exitMessage;
	}
}
