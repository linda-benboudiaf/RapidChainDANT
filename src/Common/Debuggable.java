package Common;

/**
 * Class to print a debug message if set as verbose
 * @author Nicolas
 *
 */
public abstract class Debuggable {
	
	protected String type = "DEBUG";
	protected int logLevel = 0;
	
	public Debuggable(int logLevel, String type) {
		this(logLevel);
		this.type = type;
	}

	public Debuggable(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Print a debug message in the standard output if verbose
	 * @param msg
	 */
	protected void debug(String msg) {
		if (this.logLevel > 1) {
			this.info(msg);;
		}
	}
	
	/**
	 * Print an info message
	 * @param msg
	 */
	protected void info(String msg) {
		if (this.logLevel > 0) {
			System.out.println(this.typeString() + msg);
		}
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	protected void error(String msg) {
		System.err.println(this.typeString() + msg);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	protected void error(Exception e) {
		System.err.println(this.typeString() + e.getMessage());
		if (logLevel > 2) {
			e.printStackTrace();
		}
	}
	
	private String typeString() {
		String[] parts = this.type.split("\\.");
		return "["+ String.join("][", parts) + "] ";
	}

}
