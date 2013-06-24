package org.securityvision.xattrj;

import java.io.File;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( AppTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testStringAttribute()
	{
		//assertTrue( true );
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

	public void testLargeStringAttribute()
	{
		//assertTrue( true );
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
}
