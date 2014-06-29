package org.securityvision.metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.securityvision.TestApp;
import org.securityvision.xattrj.Xattrj;

/**
 * OS X implementation for xattr using native C API
 * 
 * @author IsNull
 *
 */
class XAttrMetaDataSupport implements IFileMetaDataSupport {

	private Xattrj xattrj;

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
				throw new MetaDataNotSupportedException("OS X native metadata support is not available.", e);
			}
		}
		return instance;
	}


    /**
     * Creates a new XAttrMetaDataSupport
     * @throws UnsatisfiedLinkError
     * @throws IOException
     */
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
	public void writeAttribute(File file, String attrKey, String attrValue) throws MetadataIOException{
		xattrj.writeAttribute(file, attrKey, attrValue);
	}

	/**
	 * readAttribute
	 * xattr -p [-lrsvx] attr_name file ...
	 * 
	 */
	@Override
	public String readAttribute(File file, String attrKey) throws MetadataIOException{
		return xattrj.readAttribute(file, attrKey);
	}

    @Override
    public void removeAttribute(File file, String attrKey) throws MetadataIOException {
        if(!xattrj.removeAttribute(file, attrKey)){
            throw new MetadataIOException(
                    String.format("Removing attribute '%s' from file '%s' failed.", attrKey, file ));
        }
    }

    @Override
    public List<String> listAttributes(File file) throws MetadataIOException {
        String[] attributes = xattrj.listAttributes(file);
        if(attributes != null){
            return Arrays.asList(attributes);
        }else{
            throw new MetadataIOException(
                    String.format("Listing attributes for file '%s' failed.", file));
        }
    }
}
