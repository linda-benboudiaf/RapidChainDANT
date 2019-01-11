package common;

import java.io.InputStream;

public class LogSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Serializable obj) {
		return obj.toString();
	}

	@Override
	public Serializable unserialize(InputStream str, Serializable target) {
		return null;
	}

	@Override
	public String ext() {
		return "log";
	}

	@Override
	public Serializable unserialize(String str, Serializable target) {
		return null;
	}
	
	@Override
	public boolean writeMode() {
		return true;
	}


}
