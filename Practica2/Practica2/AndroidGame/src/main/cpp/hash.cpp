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

#include <jni.h>
#include <string>
#include "picosha2.h"

using namespace std;
using namespace picosha2;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_practica1_androidgame_NDKManager_createHash(JNIEnv *env, jobject thiz, jstring h) {

    jboolean isCopy;
    // Conversion de la cadena a un array de chars
    const char *convertedValue = env->GetStringUTFChars(h, &isCopy);

    // Creación de un vector para almacenar el hash resultante.
    vector<unsigned char> hash(8);
    // Cálculo del hash SHA-256 a partir de la cadena generada.
    hash256(convertedValue, convertedValue+strlen(convertedValue), hash.begin(), hash.end());

    // Conversión del hash a una cadena hexadecimal.
    string hex_str = bytes_to_hex_string(hash.begin(), hash.end());
    // Liberacion de memoria
    env->ReleaseStringUTFChars(h,convertedValue);

    // Devolvemos la cadena
    return env->NewStringUTF(hex_str.c_str());
}