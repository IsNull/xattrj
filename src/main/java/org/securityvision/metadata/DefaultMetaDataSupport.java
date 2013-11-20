package org.securityvision.metadata;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.Map;


/**
 * MetaDataSupport for most platforms using javas UserDefinedFileAttributeView
 * for user attributes (extended file attributes)
 * @author IsNull
 *
 */
public class DefaultMetaDataSupport implements IFileMetaDataSupport {


	@Override
	public void writeAttribute(File file, String attrKey, String attrValue) {
		UserDefinedFileAttributeView view = Files
				.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
		try {
			view.write(attrKey,
					Charset.defaultCharset().encode(attrValue));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readAttribute(File file, String attrKey) {
		UserDefinedFileAttributeView view = Files
				.getFileAttributeView(file.toPath(),UserDefinedFileAttributeView.class);
		ByteBuffer buf;
		try {
			buf = ByteBuffer.allocate(view.size(attrKey));
			view.read(attrKey, buf);
			buf.flip();
			return Charset.defaultCharset().decode(buf).toString();
		} catch (NoSuchFileException e) {
			// the requested attribute could not be found
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private final boolean isWindows = OSValidator.isWindows();
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

	private final Map<String, Boolean> driveMetaDataSupport = new HashMap<String, Boolean>();


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
	private boolean isMetaDataSupportedWindows(File file){
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
