package com.example.mygpuimage.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by 123 on 2017/12/24.
 */


public class CameraHelperBase implements CameraHelper.CameraHelperImpl {

    private final Context mContext;

    public CameraHelperBase(final Context context){
        mContext = context;
    }

    @Override
    public int getNumberOfCameras(){
        return hasCameraSupport() ? 1 : 0;
    }

    @Override
    public Camera openCamera(int id){
        return Camera.open();
    }

    @Override
    public Camera openDefaultCamera(){
        return Camera.open();
    }

    @Override
    public Camera openCameraFacing(int facing){
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK){
            return Camera.open();
        }
        return null;
    }

    @Override
    public boolean hasCamera(int facing){
        if(facing == Camera.CameraInfo.CAMERA_FACING_BACK){
            return hasCameraSupport();
        }
        return  false;
    }

    @Override
    public void getCameraInfo(int cameraId, CameraHelper.CameraInfo2 cameraInfo){
        cameraInfo.facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        cameraInfo.orientation = 90;
    }

    private boolean hasCameraSupport(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}

