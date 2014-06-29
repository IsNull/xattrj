package ch.securityvision.metadata;

import java.io.File;
import java.util.List;

/**
 * Provides file meta data support (reading extended file attributes)
 * 
 * @author IsNull
 *
 */
public interface IFileMetaDataSupport {

	/**
	 * Checks if meta data is supported for the given file.
	 * @param file
	 * @return
	 */
	public abstract boolean isMetaDataSupported(File file);

	/**
	 * Write the extended attribute to the given file.
	 * @param file
	 * @param attrKey
	 * @param attrValue
	 */
	public abstract void writeAttribute(File file, String attrKey, String attrValue) throws MetadataIOException;

	/**
	 * Read the extended attribute from the given file.
	 * @param file
	 * @param attrKey
	 * @return
	 */
	public abstract String readAttribute(File file, String attrKey) throws MetadataIOException;


    /**
     * Removes the attribute from the file
     * @param file
     * @param attrKey
     */
    public abstract void removeAttribute(File file, String attrKey) throws MetadataIOException;

    /**
     * Lists all attributes from the given file
     * @param file
     * @return
     * @throws MetadataIOException
     */
    public List<String> listAttributes(File file) throws MetadataIOException;

}
