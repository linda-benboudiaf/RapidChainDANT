package common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

/**
 * Classe permettant de stocker des objets sous la formes de fichiers json
 * @author Nicolas
 *
 */
public class Store {
	protected final String dir;
	protected HashMap<String, Storable> stores = new HashMap<>();
	
	public Store(String dir) {
		this.dir = dir;
	}

	public void register(Storable object, String file) throws IOException {
		stores.put(file, object);
		checkFile(file);
	}

	protected void checkFile(String file) throws IOException {
		File fileObject = new File(name2path(file));
		if (!fileObject.exists()) {
			File dirObject = fileObject.getParentFile();
			if (!dirObject.exists()) {
				dirObject.mkdirs();
			}
			fileObject.createNewFile();
		}
	}

	public void save(String name) throws IOException {
		Storable obj = stores.get(name);
		try (FileWriter fw = new FileWriter(name2path(name))) {
			String json = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.create()
					.toJson(obj);
			fw.write(json);
		}

	}
	
	public void saveAll() throws IOException {
		for (String store : stores.keySet()) {
			save(store);
		}
	}

	public void load(String name) throws IOException {
		Storable obj = stores.get(name);
		try (FileReader fr = new FileReader(name2path(name))) {
			Storable table = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.create()
					.fromJson(fr, obj.getClass());
			if (table != null && !table.isEmpty()) {
				obj.overwrite(table);
			}
		}
	}
	
	public void loadAll() throws IOException {
		for (String store : stores.keySet()) {
			load(store);
		}
	}
	
	protected String name2path(String file) {
		return  dir + "/" + file + ".json";
	}
}
