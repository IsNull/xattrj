package org.securityvision.xattrj;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		System.out.println("Java: Hello World!");
		Xattrj xattrj = new Xattrj();

		System.out.println("calling jni: xattrj.hello()");
		xattrj.hello();

		File file = new File("/Users/IsNull/Documents/hello.txt");
		xattrj.writeAttribute(file, "native.test", "12345");


		String value = xattrj.readAttribute(file, "native.test");
		System.out.println("val: "+value);

	}
}
