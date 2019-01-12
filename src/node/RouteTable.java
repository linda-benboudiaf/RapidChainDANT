package node;
import java.util.HashMap;

import common.Requestable;
import common.Serializable;
import common.Storable;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Address, Node> implements Storable, Requestable {
	public static final int tolerance = 3;

	public void add(Node node) {
		this.put(node.getAddr(), node);
	}
	
	/**
	 * Ajoute l'ip si elle n'exite pas déjà à la table de routage
	 * @param ip
	 */
	public void addIp(String ip) {
		Address addr = new Address(ip, App.defaultPort);
		if (!this.containsKey(addr)) {
			Node node = new Node(addr);
			this.put(addr, node);
		}
	}
	
	public Requestable requestAll(Requestable obj) {
		int fails;
		for (Node node : this.values()) {
			fails = node.fails;
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
		this.putAll((RouteTable) obj);
	}

	@Override
	public String command() {
		return "peers";
	}
}
