package Node;

import java.util.HashMap;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Ip, Node>  {
	protected static final String storeLocation = "./data/routes.json";
	
	public void add(Node node) {
		this.put(node.getIp(), node);
	}
	
	public void store() {
		
	}
	
	public void load() {
		
	}
}
