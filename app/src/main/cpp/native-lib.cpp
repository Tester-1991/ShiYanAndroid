#include <jni.h>
#include <string>
#include <android/log.h>

#ifndef LOG_TAG
#define LOG_TAG "jni"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__) // 定义LOGF类型
#endif

extern "C"
JNIEXPORT jstring JNICALL
Java_com_shiyan_android_ndk_NdkUtil_string_1jni(JNIEnv *env, jobject instance) {

    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_shiyan_android_ndk_NdkUtil_string_1java(JNIEnv *env, jobject instance, jstring message_) {

    const char *message = env->GetStringUTFChars(message_, NULL);

    LOGE("%s",message);

    env->ReleaseStringUTFChars(message_, message);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_shiyan_android_ndk_NdkUtil_array_1java(JNIEnv *env, jobject instance, jintArray array_) {

    jint buf[10];

    jint i = 0;

    jint sum = 0;

    jint number = env->GetArrayLength(array_);

    env->GetIntArrayRegion(array_,0,number,buf);

    for(i = 0; i < number; i++){

        LOGE("%d",buf[i]);

        sum += buf[i];
    }

    LOGE("sum:%d",sum);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_shiyan_android_ndk_NdkUtil_array2_1java(JNIEnv *env, jobject instance, jintArray array_) {

    jint *array = env->GetIntArrayElements(array_, NULL);

    jint sum = 0;

    jint i = 0;

    for(i =0;i < 10;i++){

        sum += array[i];
    }

    LOGE("%d",sum);

    env->ReleaseIntArrayElements(array_, array, 0);
}