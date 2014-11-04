package se.inera.fmu.application.util;

/**
 * String utility class.
 */
public final class StringUtils {

	/**
	 * Checks if given string is either blank or null.
	 *
	 * @param str string to check
	 * @return <tt>true</tt> if string is blank or <tt>null</tt>; otherwise <tt>false</tt>
	 */
	public static boolean isBlankOrNull(String str) {
		return (str == null) || "".equals(safeTrim(str));
	}

	/**
	 * Performs null-safe trim on given string and returns <tt>null</tt> if trimmed string is blank.
	 *
	 * @param str string to trim
	 * @return trimmed string, if length is not zero; otherwise <tt>null</tt>
	 */
	public static String safeTrimToNullIfBlank(String str) {
		String trimmedStr = safeTrim(str);
		return "".equals(trimmedStr) ? null : trimmedStr;
	}

	/**
	 * Performs null-safe trim on given string.
	 *
	 * @param str string to trim
	 * @return trimmed string, if available; otherwise <tt>null</tt>
	 */
	public static String safeTrim(String str) {
		return (str != null) ? str.trim() : null;
	}

}

