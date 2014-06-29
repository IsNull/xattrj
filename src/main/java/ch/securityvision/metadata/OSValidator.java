package ch.securityvision.metadata;


/**
 * Provides methods to identify the current executing OS
 * @author IsNull
 *
 */
class OSValidator {


	/**
	 * Is the current OS a windows?
	 * @return
	 */
	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	/**
	 * Is the current operating system OS X ?
	 * @return
	 */
	public static boolean isOSX() {
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
	}

	/**
	 * Is the current OS a Linux/Unix?
	 * @return
	 */
	public static boolean isUnix() {

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	/**
	 * Is the current OS a Linux Solaris?
	 * @return
	 */
	public static boolean isSolaris() {

		String os = System.getProperty("os.name").toLowerCase();
		// Solaris
		return (os.indexOf("sunos") >= 0);

	}

	/**
	 * IS the current OS a Android OS?
	 * @return
	 */
	public static boolean isAndroid(){
		String os = System.getProperty("java.runtime.name").toLowerCase();
		boolean isAndorid = (os.indexOf("android") >= 0);
		// Android
		System.out.println("'"+os + "' is android := " + isAndorid);
		return isAndorid;
	}

	/**
	 * Gets the default app data folder
	 * @return
	 */
	public static String defaultAppData()
	{
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
			return System.getenv("APPDATA");
		else if (OS.contains("MAC"))
			return System.getProperty("user.home") + "/Library/Application Support";
		else if (OS.contains("NUX"))
			return System.getProperty("user.home");
		return System.getProperty("user.dir");
	}

	private static boolean ForceHDPI = false;

	/**
	 * Force HDPI rendering
	 * This will cause {@code isHDPI()} to always return true.
	 * @param forceHDPI
	 */
	public static void setForceHDPI(boolean forceHDPI){
		ForceHDPI = forceHDPI;
		System.out.println("OSValidator: setForceHDPI = " + forceHDPI);
	}

	/**
	 * Determines if the current display has a HDPI (retina) resolution
	 * @return
	 */
	public static boolean isHDPI() {

		// TODO also support android

		if(ForceHDPI)
			return true;

		if (isOSX()) {
			// This should probably be reset each time there's a display change.
			// A 5-minute search didn't turn up any such event in the Java API.
			// Also, should we use the Toolkit associated with the editor window?

			// NOTE: apple.awt.contentScaleFactor is only avaiable in Java 1.6 from Apple
			// Java 8 does also support HDPI rendering, but currently there is no easy way
			// to detect it automatically.
			boolean retinaProp=false;
			Float prop = (Float)
					java.awt.Toolkit.getDefaultToolkit().getDesktopProperty("apple.awt.contentScaleFactor");
			if (prop != null) {
				retinaProp = prop == 2;
			}

			return retinaProp;
		}
		return false;
	}



}
