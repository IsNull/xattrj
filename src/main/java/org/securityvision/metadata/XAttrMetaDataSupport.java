package org.securityvision.metadata;

import java.io.File;
import java.io.IOException;

import org.securityvision.TestApp;
import org.securityvision.xattrj.Xattrj;

/**
 * OS X implementation for xattr using native C API
 * 
 * @author IsNull
 *
 */
public class XAttrMetaDataSupport implements IFileMetaDataSupport {

	private Xattrj xattrj;


	public static void main(String[] args) {
		new TestApp().test();
	}


	private static IFileMetaDataSupport instance;

	/**
	 * Get the platform specific XAttr implementation
	 * @return
	 */
	public synchronized static IFileMetaDataSupport getInstance() throws MetaDataNotSupportedException{
		if(instance == null)
		{
			try {
				instance = new XAttrMetaDataSupport();
			} catch (Exception e) {
				throw new MetaDataNotSupportedException("OS X native metadata support is not avaiable.", e);
			}
		}
		return instance;
	}



	XAttrMetaDataSupport() throws UnsatisfiedLinkError, IOException{
		xattrj = new Xattrj();
		System.out.println("started xattrj.");
	}


	@Override
	public boolean isMetaDataSupported(File file) {
		// xattr emulates the attributes with the "._" Prefix
		// on file systems which do not support extended attributes
		return true;
	}

	/**
	 * writeAttribute
	 * xattr -w [-rsx] attr_name attr_value file ...
	 * 
	 */
	@Override
	public void writeAttribute(File file, String attrKey, String attrValue){
		xattrj.writeAttribute(file, attrKey, attrValue);
	}

	/**
	 * readAttribute
	 * xattr -p [-lrsvx] attr_name file ...
	 * 
	 */
	@Override
	public String readAttribute(File file, String attrKey){
		return xattrj.readAttribute(file, attrKey);
	}
}
