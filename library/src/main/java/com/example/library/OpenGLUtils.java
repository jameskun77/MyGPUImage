package com.example.library;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.IntBuffer;

/**
 * Created by 123 on 2017/12/25.
 */

public class OpenGLUtils {

    private static final String TAG = "OpenGLUtils_TAG";

    public static final int NO_TEXTURE = -1;

    public static int loadTexture(final Bitmap img, final int usedTexId) {
        return loadTexture(img, usedTexId, true);
    }

    public static int loadTexture(final Bitmap img, final int usedTexId, final boolean recycle) {
        int textures[] = new int[1];
        if (usedTexId == NO_TEXTURE) {
            GLES20.glGenTextures(1, textures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, img, 0);
        } else {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, usedTexId);
            GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, img);
            textures[0] = usedTexId;
        }
        if (recycle) {
            img.recycle();
        }
        return textures[0];
    }

    public static int loadTexture(final IntBuffer data, final Camera.Size size, final int usedTexId) {
        int textures[] = new int[1];
        if (usedTexId == NO_TEXTURE) {
            GLES20.glGenTextures(1, textures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, size.width, size.height,
                    0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, data);
        } else {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, usedTexId);
            GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, size.width,
                    size.height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, data);
            textures[0] = usedTexId;
        }
        return textures[0];
    }

    public static int loadTextureAsBitmap(final IntBuffer data, final Camera.Size size, final int usedTexId) {
        Bitmap bitmap = Bitmap
                .createBitmap(data.array(), size.width, size.height, Bitmap.Config.ARGB_8888);
        return loadTexture(bitmap, usedTexId);
    }

    public static int loadShader(String strSource,int iType){
        int[] compiled = new int[1];
        int iShader = GLES20.glCreateShader(iType);
        GLES20.glShaderSource(iShader,strSource);
        GLES20.glCompileShader(iShader);
        GLES20.glGetShaderiv(iShader,GLES20.GL_COMPILE_STATUS,compiled,0);

        if (compiled[0] == 0){
            Log.e(TAG,"Compilation\n" + GLES20.glGetShaderInfoLog(iShader));
            return 0;
        }

        return iShader;
    }

    public static int loadProgram(String strVSource,String strFSource){
        int iVShader,iFShader,iProgId;
        int[] link = new int[1];

        iVShader = loadShader(strVSource,GLES20.GL_VERTEX_SHADER);
        if (iVShader == 0){
            Log.e(TAG,"load Vertex Shader Failed");
        }
        iFShader = loadShader(strFSource,GLES20.GL_FRAGMENT_SHADER);
        if (iFShader == 0){
            Log.e(TAG,"load Fragment Shader Failed");
        }
        iProgId = GLES20.glCreateProgram();

        GLES20.glAttachShader(iProgId,iVShader);
        GLES20.glAttachShader(iProgId,iFShader);
        GLES20.glLinkProgram(iProgId);

        GLES20.glGetProgramiv(iProgId,GLES20.GL_LINK_STATUS,link,0);
        if (link[0] == 0){
            Log.e(TAG,"Linking Failed");
        }

        GLES20.glDeleteShader(iVShader);
        GLES20.glDeleteShader(iFShader);

        return iProgId;
    }
}
