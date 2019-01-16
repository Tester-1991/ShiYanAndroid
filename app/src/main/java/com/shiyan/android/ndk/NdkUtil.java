package com.shiyan.android.ndk;

public class NdkUtil {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public native String string_jni();

    public native void string_java(String message);

    public native void array_java(int[] array);

    public native void array2_java(int[] array);
}
