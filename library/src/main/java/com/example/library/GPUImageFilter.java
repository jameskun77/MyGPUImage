package com.example.library;

/**
 * Created by 123 on 2017/12/24.
 */

public class GPUImageFilter {
    public static final String NO_FILTER_VERTEX_SHADER = "" +
            "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            " \n" +
            "varying vec2 textureCoordinate;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "   gl_Position = position;\n" +
            "   textureCoordinate = inputTextureCoordinate.xy;\n" +
            "}";
    public static final String NO_FILTER_FRAGMENT_SHADER = "" +
            "precision mediump float;\n" +
            "varying vec2 textureCoordinate;\n" +
            " \n" +
            "uniform sampler2D inputImageTexture;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "   gl_FragColor = texture2D(inputImageTexture,textureCoordinate);\n" +
            "}";

}
