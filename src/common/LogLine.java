package common;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class LogLine implements Storable {
	protected String str;

	public LogLine(String str) {
		this.str = str;
	}

	@Override
	public boolean isEmpty() {
		return  str.isEmpty();
	}

	@Override
	public void overwrite(Serializable obj) {
		LogLine line = (LogLine) obj;
		str = line.str;
	}
	
	@Override
	public String toString() {
		return new Date().toString() + " " + str + "\n";
	}

	@Override
	public String command() {
		return "logline";
	}
}
