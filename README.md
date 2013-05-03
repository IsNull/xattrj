xattrj
=========

Native xattr wrapper (JNI) which enables Java to access extended file attributes (xattr) over the native OS API.


Scope
-----
Currently, only OS X is supported. This is a maven JNI project, the gcc is invoked by a separate makefile.
To compile, you must run OS X 1.8, and invoke the maven "package" goal.

On Windows and Linux you can access the xattr by using Javas UserDefinedFileAttributeView which seems to work nativly too. (UserDefinedFileAttributeView is not supported on HFS+ yet.)


Hints
-----
This is a private maven project and not on maven central. 
If you have dependencies to this project, install it in your local maven repository.

eof