package ch.securityvision.xattrj;

import java.io.File;
import java.io.IOException;

/**
 * Xattrj
 * Provides native extended attribute access for Java!
 *
 * @author IsNull
 *
 */
public class Xattrj {

    private boolean libLoaded = false;

    public Xattrj() throws UnsatisfiedLinkError, IOException{
        if(!libLoaded){
            loadNativeLibrary();
        }
    }

    private void loadNativeLibrary() throws UnsatisfiedLinkError, IOException{
        System.out.println("loading xattrj...");
        LibraryLoader.loadLibrary("xattrj");
        libLoaded = true;
        System.out.println("loaded!");
    }

    /**
     * Write the extended attribute to the given file
     * @param file
     * @param attrKey
     * @param attrValue
     */
    public boolean writeAttribute(File file, String attrKey, String attrValue){
        return writeAttribute(file.getAbsolutePath(), attrKey, attrValue);
    }

    /**
     * Read the extended attribute from the given file
     * @param file
     * @param attrKey
     * @return
     */
    public String readAttribute(File file, String attrKey){
        return readAttribute(file.getAbsolutePath(), attrKey);
    }

    /**
     * Remove the given meta attribute from the given file
     * @param file
     * @param attrKey
     * @return
     */
    public boolean removeAttribute(File file, String attrKey){
        return removeAttribute(file.getAbsolutePath(), attrKey);
    }

    /**
     * List all attributes from the given file
     * @param file
     * @return
     */
    public String[] listAttributes(File file){
        return listAttributes(file.getAbsolutePath());
    }



    //
    // NATIVE METHOD ENTRY POINTS
    //

    /**
     * JNI Test Method
     */
    public native void hello();

    /**
     * Write the extended attribute to the given file
     * @param file
     * @param attrKey
     * @param attrValue
     */
    private native boolean writeAttribute(String file, String attrKey, String attrValue);

    /**
     * Read the extended attribute from the given file
     * @param file
     * @param attrKey
     * @return
     */
    private native String readAttribute(String file, String attrKey);

    /**
     * Removes the extended attribute from the given file
     * @param file
     * @param attrKey
     * @return Returns true upon success, false otherwise
     */
    private native boolean removeAttribute(String file, String attrKey);

    /**
     * List all available (accessible) Attributes of the given file
     * @param file
     * @return Returns an array of Attribute Keys which are present in the given file
     */
    private native String[] listAttributes(String file);

}