package com.example.library;

/**
 * Created by 123 on 2017/12/25.
 */

public class GPUImageNativeLibrary {

    static {
        System.loadLibrary("gpuimage-library");
    }

    public static native void YUVtoRBGA(byte[] yuv, int width, int height, int[] out);

    public static native void YUVtoARBG(byte[] yuv, int width, int height, int[] out);
}
