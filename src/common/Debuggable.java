package common;

/**
 * Class to print a debug message if set as verbose
 * @author Nicolas
 *
 */
public abstract class Debuggable {
	
	protected String prefix = "DEBUG";
	
	public Debuggable(String prefix) {
		this.prefix = prefix;
	}
	
	public Debuggable() {
	}

	/**
	 * Print a debug message in the standard output if verbose
	 * @param msg
	 */
	protected void debug(Object msg) {
		Log.debug(msg, prefix);
	}
	
	/**
	 * Print an info message
	 * @param msg
	 */
	protected void info(String msg) {
		Log.info(msg, prefix);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	protected void error(String msg) {
		Log.error(msg, prefix);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	protected void error(Exception e) {
		Log.error(e, prefix);
	}

}
