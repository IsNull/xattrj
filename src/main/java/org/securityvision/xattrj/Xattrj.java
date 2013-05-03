package org.securityvision.xattrj;

public class Xattrj {

	public native void hello();


	static {
		try {
			LibraryLoader.loadLibrary("xattrj");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}