package org.securityvision.xattrj;

public class Xattrj {

	public native void hello();

	static {
		try {
			LibraryLoader.loadLibrary("xattrj");
			//System.load("/Users/IsNull/Documents/Projects/xattrj/target/classes/libXattrj.dylib");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}