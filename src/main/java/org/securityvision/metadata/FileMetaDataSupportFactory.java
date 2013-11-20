package org.securityvision.metadata;


public class FileMetaDataSupportFactory {

	private FileMetaDataSupportFactory(){}

	private static IFileMetaDataSupport fileMetaDataSupport = null;


	/**
	 * Builds a platform specific IFileMetaDataSupport if possible
	 * @return
	 * @throws MetaDataNotSupportedException 
	 */
	public static IFileMetaDataSupport buildFileMetaSupport() throws MetaDataNotSupportedException{
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
