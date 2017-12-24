package com.example.mygpuimage;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Camera;

import com.example.mygpuimage.camera.CameraHelper;

/**
 * Created by 123 on 2017/12/24.
 */

public class ActivityCamera extends Activity {

    private CameraHelper mCameraHelper;

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private class CameraLoader{

        private int mCurrentCameraId = 0;
        private Camera mCameraInstance;

        public void onResume(){

        }

        private void setUpCamera(int id){

        }

        private Camera getCameraInstance(int id){
            Camera c = null;
            try {
                c = mCameraHelper.openCamera(id);
            }catch (Exception e){
                e.printStackTrace();
            }
            return c;
        }

        private void releaseCamera(){
            mCameraInstance.setPreviewCallback(null);
            mCameraInstance.release();
            mCameraInstance = null;
        }
    }
}
