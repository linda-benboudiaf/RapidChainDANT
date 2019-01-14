package common;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class SerialStrategy {
	public abstract String serialize(Storable obj);
	
	public abstract void serialize(OutputStream os, Storable obj);
	
	public abstract Storable unserialize(InputStream str, Storable target);

	public abstract Storable unserialize(String str, Storable target);
	
	public abstract String ext();
	
	public boolean writeMode() {
		return false;
	}
}
