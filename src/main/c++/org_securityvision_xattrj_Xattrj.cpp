#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "org_securityvision_xattrj_Xattrj.h"
#include <sys/xattr.h>


	/**
	 * writeAttribute
	 * writes the extended attribute
	 */
	JNIEXPORT void JNICALL Java_org_securityvision_xattrj_Xattrj_writeAttribute
		(JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName, jstring jattrValue){

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);
		const char *attrValue=env->GetStringUTFChars(jattrValue,0);

		int res = setxattr(filePath,
					attrName,
		            (void *)attrValue,
		            strlen(attrValue), 0,  0); //XATTR_NOFOLLOW != 0
		if(res){
		  // an error occurred, see errno
			printf("native:writeAttribute: error on write...");
			perror("");
		}
	}


	/**
	 * readAttribute
	 * Reads the extended attribute
	 */
	JNIEXPORT jstring JNICALL Java_org_securityvision_xattrj_Xattrj_readAttribute
		(JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName){

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);

		// get size of needed buffer
		int bufferLength = getxattr(filePath, attrName, NULL, 0, 0, 0);

		// make a buffer of sufficient length
		char *buffer = (char *)malloc(bufferLength);

		// now actually get the attribute string
		getxattr(filePath, attrName, buffer, 255, 0, 0);

		//printf("%s\n", buffer);

		jstring attrValue = env->NewStringUTF(buffer);
		// release buffer
		free(buffer);

		return attrValue;
	}


	/**
	 * JNI Test Method
	 */
	JNIEXPORT void JNICALL Java_org_securityvision_xattrj_Xattrj_hello(JNIEnv *, jobject) {
		printf("Hello World From JNI-C++\n");

	#ifdef __cplusplus
		printf("__cplusplus is defined\n");
	#else
		printf("__cplusplus is NOT defined\n");
	#endif
		return;
	}

