package Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.*;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Ip, Node>  {
	protected static final String dataDir = "data";
	protected static final String storeLocation = dataDir + "/routes.json";
	protected static FileReader fr;
	protected static FileWriter fw;
	
	
	public RouteTable() throws IOException {
		super();
		initFile();
	}

	private void initFile() throws IOException {
		try {
			fr = new FileReader(storeLocation);
			fw = new FileWriter(storeLocation, true);
		} catch (FileNotFoundException e) {
			createFile();
			initFile();
		}
	}
	
	private void createFile() throws IOException {
		new File(dataDir).mkdirs();
		new File(storeLocation).createNewFile();
	}

	public void add(Node node) {
		this.put(node.getIp(), node);
	}
	
	public void save() throws IOException {
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		fw.write(json);
		
	}
	
	public void load() throws IOException {
		RouteTable table = new GsonBuilder().create().fromJson(fr, this.getClass());
		this.putAll(table);
	}
}
