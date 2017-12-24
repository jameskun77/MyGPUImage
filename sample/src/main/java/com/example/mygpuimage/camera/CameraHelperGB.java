package com.example.mygpuimage.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;

/**
 * Created by 123 on 2017/12/24.
 */

@TargetApi(9)
public class CameraHelperGB implements CameraHelper.CameraHelperImpl {

    @Override
    public int getNumberOfCameras(){
        return Camera.getNumberOfCameras();
    }

    @Override
    public Camera openCamera(int id){
        return Camera.open(id);
    }

    @Override
    public Camera openDefaultCamera(){
        return Camera.open(0);
    }

    @Override
    public boolean hasCamera(int facing){
       return getCameraId(facing) != -1;
    }

    @Override
    public Camera openCameraFacing(int facing){
        return Camera.open(getCameraId(facing));
    }

    @Override
    public void getCameraInfo(int cameraId, CameraHelper.CameraInfo2 cameraInfo){
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,info);
        cameraInfo.facing = info.facing;
        cameraInfo.orientation = info.orientation;
    }

    private int getCameraId(final int facing){
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();

        for (int id = 0; id < numberOfCameras;id++){
            Camera.getCameraInfo(id,info);
            if (info.facing == facing){
                return id;
            }
        }
        return -1;
    }
}
