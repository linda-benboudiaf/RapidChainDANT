package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class StdJavaSerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Storable obj) {
		return null;
	}

	@Override
	public void serialize(OutputStream os, Storable obj) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
		} catch (IOException e) {
			Log.error(e);
		}
	}

	@Override
	public Storable unserialize(InputStream str, Storable target) {
		try {
			ObjectInputStream ois = new ObjectInputStream(str);
			target = target.getClass().cast(ois.readObject());
			return target;
		} catch (ClassNotFoundException e) {
			Log.error(e);
		} catch (IOException e) {
			Log.error(e);
		}
		return null;
	}

	@Override
	public Storable unserialize(String str, Storable target) {
		return null;
	}

	@Override
	public String ext() {
		return "ser";
	}

}
