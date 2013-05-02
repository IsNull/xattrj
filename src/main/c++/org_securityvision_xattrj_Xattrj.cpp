#include <jni.h>
#include <stdio.h>
#include "org_securityvision_xattrj_Xattrj.h"


	JNIEXPORT void JNICALL Java_org_securityvision_xattrj_Xattrj_hello(JNIEnv *, jobject) {
		printf("Hello World From C++\n");
	#ifdef __cplusplus
		printf("__cplusplus is defined\n");
	#else
		printf("__cplusplus is NOT defined\n");
	#endif
		return;
	}

