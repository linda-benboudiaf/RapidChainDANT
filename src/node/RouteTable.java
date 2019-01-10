package node;
import java.util.HashMap;

import common.Storable;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Address, Node> implements Storable {

	public void add(Node node) {
		this.put(node.getAddr(), node);
	}

	@Override
	public void overwrite(Storable obj) {
		this.putAll((RouteTable) obj);
	}
}
