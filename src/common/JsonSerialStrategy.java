package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class JsonSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Storable obj) {
		return new GsonBuilder()
			.enableComplexMapKeySerialization()
			.create()
			.toJson(obj);
	}

	@Override
	public Storable unserialize(InputStream str, Storable target) {
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
	public Storable unserialize(String str, Storable target) {
		return new GsonBuilder()
				.enableComplexMapKeySerialization()
				.create()
				.fromJson(str, target.getClass());
	}

	@Override
	public void serialize(OutputStream os, Storable obj) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			JsonWriter jw = new JsonWriter(osw);
			new GsonBuilder().setPrettyPrinting().create().toJson(obj, obj.getClass(), jw);
		} catch(IOException e) {
			Log.error(e);
		}
	}


}
