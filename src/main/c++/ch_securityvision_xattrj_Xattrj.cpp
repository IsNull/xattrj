#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include "ch_securityvision_xattrj_Xattrj.h"
#include <sys/xattr.h>

using namespace std;

	// Private member declarations (since the header is auto generated)

	jstring memJstr(JNIEnv *env, char* buffer, int size);


	// IMPLEMENTATION

	/**
	 * writeAttribute
	 * writes the extended attribute
	 *
	 */
	JNIEXPORT jboolean JNICALL Java_ch_securityvision_xattrj_Xattrj_writeAttribute
		(JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName, jstring jattrValue){

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);
		const char *attrValue=env->GetStringUTFChars(jattrValue,0);

		int res = setxattr(filePath,
					attrName,
		            (void *)attrValue,
		            strlen(attrValue),
		            0,
		            XATTR_NOFOLLOW);

		env->ReleaseStringUTFChars(jfilePath, filePath);
		env->ReleaseStringUTFChars(jattrName, attrName);
		env->ReleaseStringUTFChars(jattrValue, attrValue);

        return (res == 0) ? JNI_TRUE : JNI_FALSE;
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
	JNIEXPORT jstring JNICALL Java_ch_securityvision_xattrj_Xattrj_readAttribute
		(JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName){

		jstring jvalue = NULL;

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);

		// get size of needed buffer
		int bufferLength = getxattr(filePath, attrName, NULL, 0, 0, XATTR_NOFOLLOW);

		if(bufferLength > 0){
			// make a buffer of sufficient length
			char *buffer = (char*)malloc(bufferLength);

			// now actually get the attribute string
			int s = getxattr(filePath, attrName, buffer, bufferLength, 0, XATTR_NOFOLLOW);

			if(s > 0){
				// convert the buffer to java string
				jvalue = memJstr(env, buffer, s);
			}
			free(buffer);
		}

		env->ReleaseStringUTFChars(jfilePath, filePath);
		env->ReleaseStringUTFChars(jattrName, attrName);

		return jvalue;
	}

	/**
	 * Creates a java string from the given buffer part
	 */
	jstring memJstr(JNIEnv *env, char* buffer, int size){

		// convert the buffer to c-string
		char* value = (char*)malloc(size+1);
		*(char*)value = 0; // Null term
		strncat(value, buffer, size);

		// convert c-string to java string
		jstring jvalue = env->NewStringUTF(value);
		free(value);

		return jvalue;
	}


	/*
	 * Remove the given attribute
	 */
	JNIEXPORT jboolean JNICALL Java_ch_securityvision_xattrj_Xattrj_removeAttribute
	  (JNIEnv *env, jobject jobj, jstring jfilePath, jstring jattrName){

		const char *filePath= env->GetStringUTFChars(jfilePath, 0);
		const char *attrName= env->GetStringUTFChars(jattrName, 0);

		//  int removexattr(const char *path, const char *name, int options);

		int ret = removexattr(filePath, attrName, XATTR_NOFOLLOW);

		env->ReleaseStringUTFChars(jfilePath, filePath);
		env->ReleaseStringUTFChars(jattrName, attrName);


		return (ret == 0) ? JNI_TRUE : JNI_FALSE;
	}

	/*
	 * List all attributes
	 */
	JNIEXPORT jobjectArray JNICALL Java_ch_securityvision_xattrj_Xattrj_listAttributes
	  (JNIEnv *env, jobject jobj, jstring jfilePath){

		vector<int> attributeNames;
		char *buffer = NULL;


		const char *filePath= env->GetStringUTFChars(jfilePath, 0);

		// listxattr(const char *path, char *namebuf, size_t size, int options);

		// get size of needed buffer for names
		int bufferLength = listxattr(filePath, NULL, 0, XATTR_NOFOLLOW);

		if(bufferLength > 0){
			// make a buffer of sufficient length to hold all names
			buffer = (char*)malloc(bufferLength);

			// now actually get the attribute names
			int s = listxattr(filePath, buffer, bufferLength,  XATTR_NOFOLLOW);
			if(s >= 0){

				// Buffer contains now all names as utf-8 strings
				// each terminated by a null byte

				char* p = buffer;
				char* lp = p;

				for(int i=0; i<s; i++){
					if(*p == 0){
						// string end found
						attributeNames.push_back(p-lp); // save length of string
						lp = p+sizeof(char); // save pointer to last string end
					}
					p++;
				}
			}
		}

		// create the string array and fill it
		jclass stringClass = env->FindClass( "java/lang/String" );
		jobjectArray stringArray = env->NewObjectArray( attributeNames.size(), stringClass, 0 );

		char* bp = buffer;
		for(int i=0; i<attributeNames.size();i++){
					jstring javaString = memJstr(env, bp, attributeNames[i]);
					env->SetObjectArrayElement(stringArray, i, javaString);

					// we have to increase the string start pntr
					bp = bp + attributeNames[i]+sizeof(char); // +1 skip Null byte
		}

		env->ReleaseStringUTFChars(jfilePath, filePath);
		free(buffer);

		return stringArray;
	}





	/**
	 * JNI Test Method
	 */
	JNIEXPORT void JNICALL Java_ch_securityvision_xattrj_Xattrj_hello(JNIEnv *, jobject) {
		printf("Hello World From JNI-C++\n");

	#ifdef __cplusplus
		printf("__cplusplus is defined\n");
	#else
		printf("__cplusplus is NOT defined\n");
	#endif
		return;
	}

