package ch.securityvision.metadata;

/**
 * Thrown when meta-data support could not be given
 * 
 * @author IsNull
 *
 */
@SuppressWarnings("serial")
public class MetaDataNotSupportedException extends Exception {
	public MetaDataNotSupportedException(String message, Throwable reason){
		super(message, reason);
	}
}
