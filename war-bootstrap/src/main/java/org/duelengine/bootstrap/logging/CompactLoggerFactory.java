package org.duelengine.bootstrap.logging;

import java.io.OutputStream;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class CompactLoggerFactory implements ILoggerFactory {

	// set up logging defaults
	private static boolean isTraceEnabled = false;
	private static boolean isDebugEnabled = false;
	private static boolean isInfoEnabled = true;
	private static boolean isWarnEnabled = true;
	private static boolean isErrorEnabled = true;

	private static OutputStream output;

	public static void setLoggerOutput(OutputStream output) {
		CompactLoggerFactory.output = output;
	}

	@Override
	public Logger getLogger(String name) {
		return new CompactLogger(name, output);
	}

	public static boolean isTraceEnabled() {
		return isTraceEnabled;
	}

	public static void setTraceEnabled(boolean value) {
		isTraceEnabled = value;
	}

	public static boolean isDebugEnabled() {
		return isDebugEnabled;
	}

	public static void setDebugEnabled(boolean value) {
		isDebugEnabled = value;
	}

	public static boolean isInfoEnabled() {
		return isInfoEnabled;
	}

	public static void setInfoEnabled(boolean value) {
		isInfoEnabled = value;
	}

	public static boolean isWarnEnabled() {
		return isWarnEnabled;
	}

	public static void setWarnEnabled(boolean value) {
		isWarnEnabled = value;
	}

	public static boolean isErrorEnabled() {
		return isErrorEnabled;
	}

	public static void setErrorEnabled(boolean value) {
		isErrorEnabled = value;
	}
}
