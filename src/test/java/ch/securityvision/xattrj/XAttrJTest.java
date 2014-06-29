package ch.securityvision.xattrj;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ch.securityvision.metadata.FileMetaDataSupportFactory;
import ch.securityvision.metadata.IFileMetaDataSupport;
import ch.securityvision.metadata.MetaDataNotSupportedException;

/**
 * Unit test for simple xAttrJ
 */
public class XAttrJTest extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public XAttrJTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( XAttrJTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testStringAttribute()
	{
		String attNameString = "junit.test";
		String value1 = "abcdefghijklmnopqrstuvwxyz";

        IFileMetaDataSupport xattrj = getMetaDataSupport();
		assertNotNull(xattrj);
		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

			// Test case 1
			xattrj.writeAttribute(file, attNameString, value1);
			String readed = xattrj.readAttribute(file, attNameString);
			System.out.println("readed: '" + readed + "'");
			assertTrue("readed: '" + readed + "' but expteded: '" + value1 + "'", value1.equals(readed));

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}

	public void testRemoveAttribute(){

		String attNameString = "junit.test.remove";
		String value1 = "qiojwefpo12341234ijaoispjf";

        IFileMetaDataSupport xattrj = getMetaDataSupport();
		assertNotNull(xattrj);
		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

			// write attribute
			xattrj.writeAttribute(file, attNameString, value1);
			String readed = xattrj.readAttribute(file, attNameString);
			System.out.println("readed: '" + readed + "'");
			assertTrue("readed: '" + readed + "' but expteded: '" + value1 + "'", value1.equals(readed));

			System.out.println("now deleting attribute: " + attNameString);

            xattrj.removeAttribute(file, attNameString);
			assertTrue("attribute could not be deleted (removeAttribute returned false!)", true);

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}

	}


	public void testListAttributes()
	{
		String[] _attNameStrings = { "junit.test", "blub", "blabbb2", "hello", "world" };
		Set<String> attNameStrings = new HashSet<String>(Arrays.asList(_attNameStrings));

		String value1 = "abcdefghijklmnopqrstuvwxyz";

        IFileMetaDataSupport xattrj = getMetaDataSupport();
		assertNotNull(xattrj);
		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

			// Test case 1
			for (String attr : attNameStrings) {
				xattrj.writeAttribute(file, attr, "1243");
			}

			// we expect now that we get a list of all added attributes

			List<String> foundAttributes = xattrj.listAttributes(file);

			if(foundAttributes.size() != attNameStrings.size())
				System.err.println(
						String.format("returned attr list %d is not same size as expected: %d ",
								foundAttributes.size(), attNameStrings.size()));


			assertTrue(
					String.format("foundAttributes.length %d and attNameStrings.length %d are not Equal!", 
							foundAttributes.size(), attNameStrings.size()),
							foundAttributes.size() == attNameStrings.size());


			System.out.println("\n\nAll found attributes:");
			for (int i = 0; i < foundAttributes.size(); i++) {
				System.out.println("'" + foundAttributes.get(i)+"'");
			}
			System.out.println();


			for (String attrKey : foundAttributes) {
				assertTrue(
						String.format("missing attribute %s in set!", attrKey),
						attNameStrings.contains(attrKey));
			}

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}

	public void testLargeStringAttribute()
	{
		String attNameString = "junit.test";
		String value2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        IFileMetaDataSupport xattrj = getMetaDataSupport();
		assertNotNull(xattrj);
		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

			xattrj.writeAttribute(file, attNameString, value2);
			String readed = xattrj.readAttribute(file, attNameString);
			System.out.println("readed: '" + readed + "'");
			assertTrue("readed: '" + readed + "' but expteded: '" + value2 + "'", value2.equals(readed));

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}

	public void testMissingAttribute()
	{

		String attNameString = "junit.IdoNotExist";

		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

            IFileMetaDataSupport xattrj = getMetaDataSupport();
			assertNotNull(xattrj);

			String readed = xattrj.readAttribute(file, attNameString);
			System.out.println("readed: '" + readed + "'");
			assertTrue("readed: '" + readed + "' but expteded: '" + null + "'", readed == null);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}


	private IFileMetaDataSupport getMetaDataSupport(){
		try {
			return FileMetaDataSupportFactory.buildFileMetaSupport();
		} catch (MetaDataNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
	}
}
