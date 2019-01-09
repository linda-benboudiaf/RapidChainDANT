
import Tcp.Connection;
import Tcp.ServerFactory;

public class RapidChainServerFactory extends ServerFactory {

	@Override
	public RapidChainConnectionManager createClientManager(Connection client, String motd, String id, int logLevel, String type) {
		return new RapidChainConnectionManager(client, motd, id, logLevel, type);
	}

}
