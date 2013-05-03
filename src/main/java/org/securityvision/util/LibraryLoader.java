package org.securityvision.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Contains helper methods for loading native libraries, particularly JNI.
 *
 * @author gkubisa
 */
public class LibraryLoader {

	/**
	 * Loads a native shared library. It tries the standard System.loadLibrary
	 * method first and if it fails, it looks for the library in the current
	 * class path. It will handle libraries packed within jar files, too.
	 *
	 * @param name name of the library to load
	 * @throws IOException if the library cannot be extracted from a jar file
	 * into a temporary file
	 */
	public static void loadLibrary(String name) throws IOException {
		try {
			System.out.println("System.loadLibrary " + name);

			System.loadLibrary(name);
		} catch (UnsatisfiedLinkError e) {
			System.err.println("UnsatisfiedLinkError: " + e.getMessage());
			String filename = System.mapLibraryName(name);
			InputStream in = LibraryLoader.class.getClassLoader().getResourceAsStream(filename);

			if(in != null)
			{
				int pos = filename.lastIndexOf('.');
				File file = File.createTempFile(filename.substring(0, pos), filename.substring(pos));
				file.deleteOnExit();
				OutputStream out = null;
				try {
					byte[] buf = new byte[4096];
					out = new FileOutputStream(file);

					while (in.available() > 0) {
						int len = in.read(buf);
						if (len >= 0) {
							out.write(buf, 0, len);
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				} finally {
					if(out != null) out.close();
					if(in != null) in.close();
				}
				System.load(file.getAbsolutePath());
				System.out.println("Loaded " + file.getAbsolutePath());
			}else{
				System.err.println("LibraryLoader.class.getClassLoader().getResourceAsStream: RES Not found: " + filename);
			}
		}
	}
}