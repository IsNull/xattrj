package ch.securityvision.metadata;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * MetaDataSupport for most platforms using javas UserDefinedFileAttributeView
 * for user attributes (extended file attributes)
 * @author IsNull
 *
 */
class DefaultMetaDataSupport implements IFileMetaDataSupport {

    /***************************************************************************
     *                                                                         *
     * Private Fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final boolean isWindows = OSValidator.isWindows();
    private final Map<String, Boolean> driveMetaDataSupport = new HashMap<String, Boolean>();


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**{@inheritDoc}*/
	@Override
	public void writeAttribute(File file, String attrKey, String attrValue) throws MetadataIOException{
		UserDefinedFileAttributeView view = Files
				.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
		try {
			view.write(attrKey,
					Charset.defaultCharset().encode(attrValue));
		} catch (IOException e) {
			throw new MetadataIOException(
                    String.format("Writing attribute '%s' with value '%s' to file '%s' failed.", attrKey, attrValue, file),
                    e);
		}
	}

    /**{@inheritDoc}*/
	@Override
	public String readAttribute(File file, String attrKey) throws MetadataIOException{
		UserDefinedFileAttributeView view = Files
				.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
		ByteBuffer buf;
		try {
			buf = ByteBuffer.allocate(view.size(attrKey));
			view.read(attrKey, buf);
			buf.flip();
			return Charset.defaultCharset().decode(buf).toString();
		} catch (NoSuchFileException e) {
			// the requested attribute could not be found
		} catch (IOException e) {
            throw new MetadataIOException(
                    String.format("Reading attribute '%s' from file '%s' failed.", attrKey, file),
                    e);
		}
		return null;
	}


    /**{@inheritDoc}*/
    @Override
    public void removeAttribute(File file, String attrKey) throws MetadataIOException {
        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);

        try {
            view.delete(attrKey);
        } catch (IOException e) {
            throw new MetadataIOException(
                    String.format("Deleting attribute '%s' from file '%s' failed.", attrKey, file),
                    e);
        }
    }

    /**{@inheritDoc}*/
    @Override
    public List<String> listAttributes(File file) throws MetadataIOException {
        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);

        try {
            List<String> attributes = view.list();
            return attributes;
        } catch (IOException e) {
            throw new MetadataIOException(
                    String.format("Listing attributes for file '%s' failed.", file),
                    e);
        }
    }


    /**
	 * Is meta-data supported for the given file?
	 */
	@Override
	public boolean isMetaDataSupported(File file) {
		if(isWindows)
		{
			return isMetaDataSupportedWindows(file);
		}
		return isMetaDataSupportedFor(file);
	}


    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/


	/**
	 * Does the given file support metadata?
	 * 
	 * Note:
	 * Since Windows maps different devices with different letters,
	 * we only have to check each device letter once. This greatly improves
	 * speed, so we can basically cache this info for great speed.
	 * 
	 * On Unix like OS on the other hand, a path can be mapped at any point to a different device.
	 * 
	 * @param file
	 * @return
	 */
	private boolean isMetaDataSupportedWindows(File file) {
		String letter = file.toString().substring(0, 2);
		Boolean metaDataSupported = driveMetaDataSupport.get(letter);
		if(metaDataSupported == null){
			metaDataSupported = isMetaDataSupportedFor(file);
			driveMetaDataSupport.put(letter, metaDataSupported);
		}
		return metaDataSupported;
	}

	private boolean isMetaDataSupportedFor(File file) {
		try {
			FileStore store = Files.getFileStore(file.toPath());
			return store.supportsFileAttributeView(UserDefinedFileAttributeView.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
