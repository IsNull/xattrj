package org.securityvision.xattrj;

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
	}
}
