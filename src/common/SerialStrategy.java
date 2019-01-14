package common;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class SerialStrategy {
	public abstract String serialize(Serializable obj);
	
	public abstract void serialize(OutputStream os, Serializable obj);
	
	public abstract Serializable unserialize(InputStream str, Serializable target);

	public abstract Serializable unserialize(String str, Serializable target);
	
	public abstract String ext();
	
	public boolean writeMode() {
		return false;
	}
}
