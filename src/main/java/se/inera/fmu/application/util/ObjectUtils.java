package se.inera.fmu.application.util;


/**
 * Utility for object operations.
 */
public final class ObjectUtils {

	/**
	 * Performs null-safe equality check on given objects.
	 *
	 * @param obj1 left-side object
	 * @param obj2 right-side object
	 * @return <tt>true</tt> if objects are both <tt>null</tt> or equal; otherwise <tt>false</tt>
	 */
	public static boolean safeEquals(Object obj1, Object obj2) {
		return (obj1 != null) ? obj1.equals(obj2) : (obj2 == null);
	}

	/**
	 * Computes hash code based on given objects.
	 * <p/>
	 * If any given objects are <tt>null</tt>,
	 * then these are not included in the computation of the hash code.
	 *
	 * @param objs objects
	 * @return computed hash code
	 */
	public static int computeHashCode(Object... objs) {
		int hashCode = 0;

		if (objs != null) {
			for (Object obj : objs) {
				if (obj != null) {
					hashCode *= 37;
					hashCode += obj.hashCode();
				}
			}
		}

		return hashCode;
	}

	/**
	 * Performs null-safe extract of hash code from given object.
	 *
	 * @param obj object to get hash code for, if available
	 * @return hash code, if object is not null; otherwise <tt>0</tt>
	 */
	public static int safeHashCode(Object obj) {
		return (obj != null) ? obj.hashCode() : 0;
	}

	private ObjectUtils() {
	}
}
