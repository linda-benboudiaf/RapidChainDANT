package common;

import java.io.IOException;

public abstract class Log {
	protected static int Level = 0;
	protected static String prefix = "LOG";
	protected static Store store;
	
	public static void start(Store store, int level) {
		Log.Level = level;
		Log.store = store;
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
		msg = "info: " + prefixString(prefix) + msg;
		if (Log.Level > 0) {
			System.out.println(msg);
		}
		append(msg);
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
		msg = "err: " + prefixString(prefix) + msg;
		System.err.println(msg);
		append(msg);
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
		error(e.getMessage(), prefix);
		if (Log.Level > 1) {
			e.printStackTrace();
		}
	}
	
	private static String prefixString(String prefix) {
		String[] parts = prefix.split("/");
		return "["+ String.join("][", parts) + "] ";
	}
	
	private static void append(String msg) {
		if (store != null) {
			try {
				LogLine line = new LogLine(msg);
				store.register(line, "app", new LogSerialStrategy());
				store.save("app");
			} catch (IOException e) {
			}
		}
	}

}
