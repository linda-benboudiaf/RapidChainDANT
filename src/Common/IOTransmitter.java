package Common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class to easily send and receive message trough input and output streams
 * @author Nicolas
 *
 */
public class IOTransmitter extends Debuggable {
	protected OutputStream os;
	protected InputStream is;
	
	public IOTransmitter(int verbose, String type) {
		super(verbose, type);
	}
	
	public IOTransmitter(int verbose) {
		super(verbose);
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
	public String receive() throws IOException {
		int i;
		StringBuffer buff = new StringBuffer();
		while ((i = this.is.read()) != -1 && i != 4) { // 4 is the end of transmission byte
			buff.append((char) i); // add the character to the buffer
		}
		String msg = buff.toString(); // convert buffer to string)
		this.debug("receive message: \"" + msg + "\"");
		return msg;
	}

	/**
	 * Send a message
	 * @param msg the message
	 */
	public void send(String msg) {
		try {
			this.debug("sending message: \"" + msg + "\"");
			byte[] bytes = msg.getBytes();
			this.os.write(bytes);
			this.os.write(4); // means end of transmission
		} catch (IOException e) {
			this.debug("error while sending message");
			e.printStackTrace();
		}
	}
}
