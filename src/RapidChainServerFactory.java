
import Tcp.Connection;
import Tcp.ServerFactory;

public class RapidChainServerFactory extends ServerFactory {

	@Override
	public RapidChainClientManager createClientManager(Connection client, String motd, String id, int logLevel, String type) {
		return new RapidChainClientManager(client, motd, id, logLevel, type);
	}

}
