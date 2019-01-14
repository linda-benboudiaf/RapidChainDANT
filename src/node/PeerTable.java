package node;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import common.Log;
import common.Requestable;
import common.Serializable;
import common.Storable;
import tcp.Address;

@SuppressWarnings("serial")
public class PeerTable extends HashMap<Address, Node> implements Storable, Requestable {
	public static final int tolerance = 3;

	public void add(Node node) {
		this.put(node.getAddr(), node);
	}

	
	/**
	 * Ajoute l'adresse si elle n'exite pas déjà à la table de routage
	 * @param ip
	 */
	public void addAddr(Address addr) {
		if (!this.containsKey(addr)) {
			Node node = new Node(addr);
			this.put(addr, node);
		}
	}
	
	/**
	 * Ajoute l'ip si elle n'exite pas déjà à la table de routage
	 * @param ip
	 */
	public void addIp(String ip) {
		Address addr = new Address(ip, App.defaultPort);
		addAddr(addr);
	}
	
	public Requestable requestAll(Requestable obj) {
		int fails;
		PeerTable nodes = (PeerTable) this.clone();
		for (Node node : nodes.values()) {
			fails = node.nbFailsThisWeek();
			if (fails < tolerance) {
				Requestable res = node.request(obj);
				if (res != null) {
					obj.overwrite(res);
				}
			}
		}
		return obj;
		
	}

	@Override
	public void overwrite(Serializable obj) {
		this.putAll((PeerTable) obj);
	}

	@Override
	public String command() {
		return "peers";
	}
	
	public void initFromDns() {
		try {
            InetAddress[] ipAddr = InetAddress.getAllByName("blurchain.club1.fr");
            for(int i=0; i < ipAddr.length ; i++) {
                if (ipAddr[i] instanceof Inet4Address) {
                    Log.debug("IPv4 : " + ipAddr[i].getHostAddress());
                    this.addIp(ipAddr[i].getHostAddress());
                }
            }
        } catch (UnknownHostException e) {
            Log.error(e);
        }
	}
}
