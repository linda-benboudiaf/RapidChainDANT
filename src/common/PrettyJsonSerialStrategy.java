package common;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.GsonBuilder;

public class PrettyJsonSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Serializable obj) {
		return new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setPrettyPrinting()
			.create()
			.toJson(obj);
	}

	@Override
	public Serializable unserialize(InputStream str, Serializable target) {
		Reader r = new InputStreamReader(str);
		return new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setPrettyPrinting()
			.create()
			.fromJson(r, target.getClass());
	}

	@Override
	public String ext() {
		return "json";
	}


}
