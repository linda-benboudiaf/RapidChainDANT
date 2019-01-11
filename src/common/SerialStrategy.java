package common;

import java.io.InputStream;

public abstract class SerialStrategy {
	public abstract String serialize(Serializable obj);
	
	public abstract Serializable unserialize(InputStream str, Serializable target);

	public abstract Serializable unserialize(String str, Serializable target);
	
	public abstract String ext();
}
