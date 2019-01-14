package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Class to easily send and receive message trough input and output streams
 * @author Nicolas
 *
 */
public class IOTransmitter extends Debuggable {
	protected OutputStream os;
	protected InputStream is;
	
	public IOTransmitter(String type) {
		super(type);
	}

	public OutputStream getOs() {
		return os;
	}

	public InputStream getIs() {
		return is;
	}
	
	public ObjectOutputStream getOos() throws IOException {
		return new ObjectOutputStream(os);
	}
	
	public ObjectInputStream getOis() throws IOException {
		return new ObjectInputStream(is);
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	/**
	 * Wait for a massage to receive
	 * @return the message received
	 * @throws IOException
	 */
	public Serializable receive() {
		try {
			Serializable obj = (Serializable) this.getOis().readObject();
			this.debug("receive message: \"" + obj.toString() + "\"");
			return obj;
		} catch (ClassNotFoundException e) {
			this.error(e);
		} catch (IOException e) {
			this.error(e);
		}
		return null;
	}

	/**
	 * Send a message
	 * @param msg the message
	 */
	public void send(Serializable obj) {
		try {
			this.debug("sending message: \"" + obj.toString() + "\"");
			this.getOos().writeObject(obj);
		} catch (IOException e) {
			this.error(e);
		}
	}
}
