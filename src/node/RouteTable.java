package node;
import java.util.HashMap;

import common.Requestable;
import common.Serializable;
import common.Storable;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Address, Node> implements Storable, Requestable {

	public void add(Node node) {
		this.put(node.getAddr(), node);
	}
	
	public Requestable requestAll(Requestable obj) {
		for (Node node : this.values()) {
			obj.overwrite(node.request(obj));
		}
		return obj;
		
	}

	@Override
	public void overwrite(Serializable obj) {
		this.putAll((RouteTable) obj);
	}

	@Override
	public String command() {
		return "iptables";
	}
}
