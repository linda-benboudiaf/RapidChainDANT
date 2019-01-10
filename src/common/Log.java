package common;


public abstract class Log {
	protected static int Level = 0;
	protected static String prefix = "LOG";
	
	public static void setLevel(int level) {
		Log.Level = level;
	}

	/**
	 * Print a debug message in the standard output if verbose
	 * @param msg
	 */
	public static void debug(Object msg) {
		debug(msg, prefix);
	}

	/**
	 * Print a debug message in the standard output if verbose
	 * @param msg
	 * @param prefix
	 */
	public static void debug(Object msg, String prefix) {
		if (Log.Level > 1) {
			info(msg.toString(), prefix);
		}
	}
	
	/**
	 * Print an info message
	 * @param msg
	 */
	public static void info(String msg) {
		info(msg, prefix);
	}
	
	/**
	 * Print an info message
	 * @param msg
	 * @param prefix
	 */
	public static void info(String msg, String prefix) {
		if (Log.Level > 0) {
			System.out.println(prefixString(prefix) + msg);
		}
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	public static void error(String msg) {
		error(msg, prefix);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 * @param prefix
	 */
	public static void error(String msg, String prefix) {
		System.err.println(prefixString(prefix) + msg);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 */
	public static void error(Exception e) {
		error(e, prefix);
	}
	
	/**
	 * Print an error message in the standard output
	 * @param msg
	 * @param prefix
	 */
	public static void error(Exception e, String prefix) {
		System.err.println(prefixString(prefix) + e.getMessage());
		if (Log.Level > 1) {
			e.printStackTrace();
		}
	}
	
	private static String prefixString(String prefix) {
		String[] parts = prefix.split("\\.");
		return "["+ String.join("][", parts) + "] ";
	}

}
