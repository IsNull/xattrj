package org.securityvision.metadata;

import org.securityvision.xattrj.Xattrj;

import java.io.File;
import java.io.IOException;

/**
 * OS X implementation for xattr using native C API
 * 
 * @author IsNull
 *
 */
public class XAttrMetaDataSupport implements IFileMetaDataSupport {

    /***************************************************************************
     *                                                                         *
     * Private Fields                                                          *
     *                                                                         *
     **************************************************************************/

	private Xattrj xattrj;
	private static IFileMetaDataSupport instance;

    /***************************************************************************
     *                                                                         *
     * Singleton                                                               *
     *                                                                         *
     **************************************************************************/

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

    /**
     * Private Singleton constructor
     * @throws UnsatisfiedLinkError
     * @throws IOException
     */
	private XAttrMetaDataSupport() throws UnsatisfiedLinkError, IOException{
		xattrj = new Xattrj();
		System.out.println("started xattrj.");
	}

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**{@inheritDoc}*/
	@Override
	public boolean isMetaDataSupported(File file) {
		// xattr emulates the attributes with the "._" Prefix
		// on file systems which do not support extended attributes
		return true;
	}

	/**
     * {@inheritDoc}
	 * xattr -w [-rsx] attr_name attr_value file ...
	 * 
	 */
	@Override
	public void writeAttribute(File file, String attrKey, String attrValue){
		xattrj.writeAttribute(file, attrKey, attrValue);
	}

	/**
     * {@inheritDoc}
	 * xattr -p [-lrsvx] attr_name file ...
	 * 
	 */
	@Override
	public String readAttribute(File file, String attrKey){
		return xattrj.readAttribute(file, attrKey);
	}
}
