package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.GsonBuilder;

public class LogSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Storable obj) {
		return obj.toString();
	}

	@Override
	public Storable unserialize(InputStream str, Storable target) {
		return null;
	}

	@Override
	public String ext() {
		return "log";
	}

	@Override
	public Storable unserialize(String str, Storable target) {
		return null;
	}
	
	@Override
	public boolean writeMode() {
		return true;
	}

	@Override
	public void serialize(OutputStream os, Storable obj) {
		try {
			String str = new GsonBuilder().setPrettyPrinting().create().toJson(obj);  
			os.write(str.getBytes());
			os.close();			
		}catch(IOException e) {
			Log.error(e);
		}
	}


}
