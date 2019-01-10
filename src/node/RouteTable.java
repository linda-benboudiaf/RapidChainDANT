package node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.*;

@SuppressWarnings("serial")
public class RouteTable extends HashMap<Ip, Node> {
	protected static final String dataDir = "data";
	protected static final String storeLocation = dataDir + "/routes.json";

	public RouteTable() throws IOException {
		super();
	}

	private void createFile() throws IOException {
		new File(dataDir).mkdirs();
		new File(storeLocation).createNewFile();
	}

	public void add(Node node) {
		this.put(node.getIp(), node);
	}

	public void save() throws IOException {
		try (FileWriter fw = new FileWriter(storeLocation)) {
			Gson gson = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.setPrettyPrinting()
					.create();
			String json = gson.toJson(this);
			fw.write(json);
		} catch (FileNotFoundException e) {
			createFile();
		}

	}

	public void load() throws IOException {
		try (FileReader fr = new FileReader(storeLocation)) {
			Gson gson = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.create();
			RouteTable table = gson.fromJson(fr, this.getClass());
			if (table != null && !table.isEmpty()) {
				this.putAll(table);
			}
		} catch (FileNotFoundException e) {
			createFile();
		}

	}
}
