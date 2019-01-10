package node;
import java.util.HashMap;

import common.Storable;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Address, Node> implements Storable {

	public void add(Node node) {
		this.put(node.getAddr(), node);
	}
	
	public int requestAll(String msg) {
		int count = 0;
		for (Node node : this.values()) {
			node.request(msg);
			count ++;
		}
		return count;
	}

	@Override
	public void overwrite(Storable obj) {
		this.putAll((RouteTable) obj);
	}
}
