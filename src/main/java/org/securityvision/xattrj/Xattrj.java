package org.securityvision.xattrj;

import java.io.File;

import org.securityvision.util.LibraryLoader;

/**
 * 
 * @author IsNull
 *
 */
public class Xattrj {

	/**
	 * JNI Test Method
	 */
	public native void hello();


	/**
	 * Write the extended attribute to the given file
	 * @param file
	 * @param attrKey
	 * @param attrValue
	 */
	public void writeAttribute(File file, String attrKey, String attrValue){
		writeAttribute(file.getAbsolutePath(), attrKey, attrValue);
	}

	/**
	 * Read the extended attribute from the given file
	 * @param file
	 * @param attrKey
	 * @return
	 */
	public String readAttribute(File file, String attrKey){
		return readAttribute(file.getAbsolutePath(), attrKey);
	}

	/**
	 * Write the extended attribute to the given file
	 * @param file
	 * @param attrKey
	 * @param attrValue
	 */
	private native void writeAttribute(String file, String attrKey, String attrValue);

	/**
	 * Read the extended attribute from the given file
	 * @param file
	 * @param attrKey
	 * @return
	 */
	private native String readAttribute(String file, String attrKey);


	static {
		try {
			LibraryLoader.loadLibrary("xattrj");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}