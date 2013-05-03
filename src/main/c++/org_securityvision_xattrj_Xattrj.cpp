#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "org_securityvision_xattrj_Xattrj.h"
#include <sys/xattr.h>

	JNIEXPORT void JNICALL Java_org_securityvision_xattrj_Xattrj_hello(JNIEnv *, jobject) {
		printf("Hello World From C++\n");

		const char *filePath = "/Users/IsNull/Documents/hello.txt";
		const char *attrName = "test";
		const char *val = "valua";

		int res = setxattr(filePath,
					attrName,
		            (void *)val,
		            strlen(val),
		            0,
		            0); //XATTR_NOFOLLOW != 0
		if(res){
		  /* an error occurred, see errno */
			printf("error on write...");
			perror("");
		}else{
			printf("xattr written :)");
		}

		// read argument
		printf("xattr read...");
		// get size of needed buffer
		int bufferLength = getxattr(filePath, attrName, NULL, 0, 0, 0);

		// make a buffer of sufficient length
		char *buffer = (char *)malloc(bufferLength);

		// now actually get the attribute string
		getxattr(filePath, attrName, buffer, 255, 0, 0);

		printf("buffer: ");
		printf("%s\n", buffer);
		// release buffer
		free(buffer);

	#ifdef __cplusplus
		printf("__cplusplus is defined\n");
	#else
		printf("__cplusplus is NOT defined\n");
	#endif
		return;
	}

