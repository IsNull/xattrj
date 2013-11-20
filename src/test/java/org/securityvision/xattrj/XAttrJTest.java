package org.securityvision.xattrj;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple xAttrJ
 */
public class XAttrJTest 
extends TestCase
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
		if(skipTests()){
			Assert.assertTrue(true);
			return;
		}


		String attNameString = "junit.test";
		String value1 = "abcdefghijklmnopqrstuvwxyz";

		Xattrj xattrj = getXattrj();
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
		if(skipTests()){
			Assert.assertTrue(true);
			return;
		}


		String attNameString = "junit.test.remove";
		String value1 = "qiojwefpo12341234ijaoispjf";

		Xattrj xattrj = getXattrj();
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

			assertTrue("attribute could not be deleted (removeAttribute returned false!)", xattrj.removeAttribute(file, attNameString));

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}

	}


	public void testListAttributes()
	{
		if(skipTests()){
			Assert.assertTrue(true);
			return;
		}


		String[] _attNameStrings = { "junit.test", "blub", "blabbb2", "hello", "world" };
		Set<String> attNameStrings = new HashSet<String>(Arrays.asList(_attNameStrings));

		String value1 = "abcdefghijklmnopqrstuvwxyz";

		Xattrj xattrj = getXattrj();
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

			String[] foundAttributes = xattrj.listAttributes(file);

			if(foundAttributes.length != attNameStrings.size()) 
				System.err.println(
						String.format("returned attr list %d is not same size as expected: %d ",
								foundAttributes.length, attNameStrings.size()));


			assertTrue(
					String.format("foundAttributes.length %d and attNameStrings.length %d are not Equal!", 
							foundAttributes.length, attNameStrings.size()),
							foundAttributes.length == attNameStrings.size());


			System.out.println("\n\nAll found attributes:");
			for (int i = 0; i < foundAttributes.length; i++) {
				System.out.println("'" + foundAttributes[i]+"'");
			}
			System.out.println();


			for (int i = 0; i < foundAttributes.length; i++) {
				assertTrue(
						String.format("missing attribute %s in set!", foundAttributes[i]),
						attNameStrings.contains(foundAttributes[i]));
			}

		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}

	public void testLargeStringAttribute()
	{
		if(skipTests()){
			Assert.assertTrue(true);
			return;
		}

		String attNameString = "junit.test";
		String value2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

		Xattrj xattrj = getXattrj();
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
		if(skipTests()){
			Assert.assertTrue(true);
			return;
		}

		String attNameString = "junit.IdoNotExist";

		File file;
		try {
			file = File.createTempFile("xattrTest", "junit");
			file.deleteOnExit();

			Xattrj xattrj = getXattrj();
			assertNotNull(xattrj);

			String readed = xattrj.readAttribute(file, attNameString);
			System.out.println("readed: '" + readed + "'");
			assertTrue("readed: '" + readed + "' but expteded: '" + null + "'", readed == null);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
			e.printStackTrace();
		}
	}


	private Xattrj getXattrj(){
		try {
			return new Xattrj();
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Shall the tests be skipped (not supported on the current platform)
	 * 
	 * @return
	 */
	private static boolean skipTests() {
		// Is the current OS a windows? If so, skipp tests.

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
	}
}
