#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("androidgame");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("androidgame")
//      }
//    }


extern "C"
JNIEXPORT jint JNICALL
Java_com_practica1_androidgame_NDKManager_computeFibonacci(JNIEnv *env, jclass clazz, jint n) {

    // TODO: implement computeFibonacci()
    return n;
}