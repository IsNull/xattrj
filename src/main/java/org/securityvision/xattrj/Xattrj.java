package org.securityvision.xattrj;

import java.io.File;
import java.io.IOException;

import org.securityvision.util.LibraryLoader;

/**
 * Xattrj
 * Provides Native extended attribute access for Java!
 * 
 * @author IsNull
 *
 */
public class Xattrj {

	private boolean libLoaded = false;

	public Xattrj() throws UnsatisfiedLinkError, IOException{
		if(!libLoaded){
			loadNativeLibrary();
		}
	}

	private void loadNativeLibrary() throws UnsatisfiedLinkError, IOException{
		System.out.println("loading xattrj...");
		LibraryLoader.loadLibrary("xattrj");
		libLoaded = true;
		System.out.println("loaded!");
	}






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



}