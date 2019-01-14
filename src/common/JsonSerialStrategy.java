package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import com.google.gson.GsonBuilder;

public class JsonSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Serializable obj) {
		return new GsonBuilder()
			.enableComplexMapKeySerialization()
			.create()
			.toJson(obj);
	}

	@Override
	public Serializable unserialize(InputStream str, Serializable target) {
		Reader r = new InputStreamReader(str);
		return new GsonBuilder()
			.enableComplexMapKeySerialization()
			.create()
			.fromJson(r, target.getClass());
	}

	@Override
	public String ext() {
		return "json";
	}

	@Override
	public Serializable unserialize(String str, Serializable target) {
		return new GsonBuilder()
				.enableComplexMapKeySerialization()
				.create()
				.fromJson(str, target.getClass());
	}

	@Override
	public void serialize(OutputStream os, Serializable obj) {
		try {
			String str = new GsonBuilder().setPrettyPrinting().create().toJson(obj);  
			os.write(str.getBytes());
			os.close();			
		}catch(IOException e) {
			Log.error(e);
		}
	}


}
