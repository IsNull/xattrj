xattrj
=========

Native xattr wrapper (JNI) which enables Java to access extended file attributes (xattr) over the native OS API.


Scope
-----
Currently, only OS X is guaranteed to be supported (Unix/Linux may work depending on header <sys/xattr.h>). This is a Eclipse maven JNI project, the g++ is invoked by a separate makefile.
To compile, just invoke the maven "package" goal.

On Windows and Linux you can access the xattr by using Javas UserDefinedFileAttributeView which seems to work nativly too. (UserDefinedFileAttributeView is not supported on HFS+ with Java 7 or anything before.)


Maven Dependency
----------------
To use xattrj, you need to compile and install this maven project into your local maven repo:

`mvn clean package install`

Then you can add a dependency to your maven project:

```
  		<dependency>
	        <groupId>org.securityvision</groupId>
	        <artifactId>xattrj</artifactId>
	        <version>1.0</version>
	    </dependency>
``

Hints
-----
This is a private maven project and not on maven central. 
If you have dependencies to this project, install it in your local maven repository.

eof