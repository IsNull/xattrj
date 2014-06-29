package ch.securityvision;

import ch.securityvision.xattrj.Xattrj;

import java.io.File;
import java.io.IOException;


/**
 * Xattrj test!
 *
 */
public class TestApp {

	public static void main(String[] args) {
		new TestApp().test();
	}

	public void test(){
		System.out.println("Java: Hello World!");
		Xattrj xattrj;
		try {
			xattrj = new Xattrj();

			System.out.println("calling jni: xattrj.hello()");
			xattrj.hello();

			File file = new File("/Users/IsNull/Documents/hello.txt");
			xattrj.writeAttribute(file, "native.test", "12345");


			String value = xattrj.readAttribute(file, "native.test");
			System.out.println("val: "+value);

		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
