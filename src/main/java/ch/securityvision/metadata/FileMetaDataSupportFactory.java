package ch.securityvision.metadata;

/**
 * Factory which creates a IFileMetaDataSupport instance.
 * The instance is valid for the current running platform and will internally be cached.
 *
 */
public class FileMetaDataSupportFactory {

	private FileMetaDataSupportFactory(){}
	private static IFileMetaDataSupport fileMetaDataSupport = null;


	/**
	 * Builds a platform specific IFileMetaDataSupport if possible
	 * @return
	 * @throws MetaDataNotSupportedException 
	 */
	public synchronized static IFileMetaDataSupport buildFileMetaSupport() throws MetaDataNotSupportedException{
		if(fileMetaDataSupport == null)
		{
			if(OSValidator.isOSX())
			{
				fileMetaDataSupport = XAttrMetaDataSupport.getInstance();
			}else {
				fileMetaDataSupport = new DefaultMetaDataSupport();
			}
		}
		return fileMetaDataSupport;
	}

}
