
import Blockchain.Hash;
import Tcp.Server;

/**
 * Class to run a shifumi server
 * @author Nicolas
 *
 */
public class RapidChainServer extends Server {
	protected Hash i; 
	
	public RapidChainServer(int port, int pool) {
		this(port, pool, 1);
	}

	public RapidChainServer(int port, int pool, int logLevel) {
		super(port, pool, logLevel);
		this.motd = "Welcome to RapidChain server 12.7";
		this.factory = new RapidChainServerFactory();
		this.i = new Hash(); 
	}
}
