#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "org_securityvision_xattrj_Xattrj.h"
#include <sys/xattr.h>


	/**
	 * writeAttribute
	 * writes the extended attribute
	 *
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
	 * Reads the extended attribute as string
	 *
	 * If the attribute does not exist (or any other error occurs)
	 * a null string is returned.
	 *
	 *
	 */
	JNIEXPORT jstring JNICALL Java_org_securityvision_xattrj_Xattrj_readAttribute
		(JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName){

		jstring jvalue = NULL;

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);

		// get size of needed buffer
		int bufferLength = getxattr(filePath, attrName, NULL, 0, 0, 0);

		if(bufferLength > 0){
			// make a buffer of sufficient length
			char *buffer = (char*)malloc(bufferLength);

			// now actually get the attribute string
			int s = getxattr(filePath, attrName, buffer, bufferLength, 0, 0);

			if(s > 0){
				// convert the buffer to a null terminated string
				char *value = (char*)malloc(s+1);
				*(char*)value = 0;
				strncat(value, buffer, s);
				free(buffer);

				// convert the c-String to a java string
				jvalue = env->NewStringUTF(value);
			}
		}
		return jvalue;
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

