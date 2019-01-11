package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Classe permettant de stocker des objets sous la formes de fichiers json
 * @author Nicolas
 *
 */
public class Store {
	protected final String dir;
	protected HashMap<String, StoreData> stores = new HashMap<>();
	
	public Store(String dir) {
		this.dir = dir;
	}

	public void register(Storable object, String file, SerialStrategy strat) throws IOException {
		stores.put(file, new StoreData (object, strat));
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
		StoreData pair = stores.get(name);
		Storable obj = pair.getData();
		SerialStrategy strat = pair.getStrat();
		try (FileWriter fw = new FileWriter(name2path(name), strat.writeMode())) {
			String json = strat.serialize(obj);
			fw.write(json);
		}

	}
	
	public void saveAll() throws IOException {
		for (String store : stores.keySet()) {
			save(store);
		}
	}

	public void load(String name) throws IOException {
		StoreData pair = stores.get(name);
		Storable target = pair.getData();
		SerialStrategy strat = pair.getStrat();
		try (InputStream fi = new FileInputStream(name2path(name))) {
			Storable obj = (Storable) strat.unserialize(fi, target);
			if (obj != null && !obj.isEmpty()) {
				target.overwrite(obj);
			}
		}
	}
	
	public void loadAll() throws IOException {
		for (String store : stores.keySet()) {
			load(store);
		}
	}
	
	protected String name2path(String file) {
		String ext = stores.get(file).getStrat().ext();
		return  dir + "/" + file + "." + ext;
	}
}
