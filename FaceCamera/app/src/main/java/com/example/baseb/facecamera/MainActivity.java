package com.example.baseb.facecamera;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera.FaceDetectionListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Camera.FaceDetectionListener ,View.OnClickListener{
    private Camera mCamera = null;
    private SurfaceView mView = null;
    private FaceView mFaceView;
    private int openCameraType = 0;
    private Button mButton;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        //this.mButton = (Button)this.findViewById(R.id.button);
        this.mButton = (Button) this.findViewById(R.id.button);
        this.mButton.setOnClickListener(this);

        //mView = new SurfaceView(this);
        this.mView = (SurfaceView)this.findViewById(R.id.preview_id);
        //setContentView(this.mView);
        this.holder = this.mView.getHolder();
        holder.addCallback(this);

        mFaceView = new FaceView(this);
        addContentView(mFaceView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //openCameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;
        openCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCamera = Camera.open(openCameraType);
        //Camera#setFaceDetectionListenerメソッドで, リスナーを登録する.
        mCamera.setFaceDetectionListener(this);
        try {
            // プレビューの設定
            mCamera.setPreviewDisplay(this.holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
        //Camera#stopFaceDitectionメソッドで, 顔検出を停止する.
        mCamera.stopFaceDetection();
        mCamera.stopPreview();

        //Camera#getParametersメソッドで, Camera.Parametersのインスタンスを取得する.
        Camera.Parameters params = mCamera.getParameters();
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        Size size = previewSizes.get(0);
        params.setPreviewSize(size.width, size.height);
        mCamera.setParameters(params);
        // プレビュー開始
        mCamera.startPreview();
        //Parameters#getMaxNumDetectedFacesメソッドで, 検出可能な最大の顔数を取得する.
        //System.out.println("############");
        //System.out.println(params.getMaxNumDetectedFaces());
        //System.out.println("############");
        if (params.getMaxNumDetectedFaces() > 0) {
            //顔数が1以上の場合には顔検出に対応しているので, Camera#startFaceDetectionメソッドで顔検出を開始する.
            mCamera.startFaceDetection();
        } else {
            Log.e("FaceDetector", "Face detection is not supported.");
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //Camera#stopFaceDitectionメソッドで, 顔検出を停止する.
        mCamera.stopFaceDetection();
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    //顔を検出した際の処理を記述する.
    public void onFaceDetection(Face[] faces, Camera mCamera) {
        // TODO Auto-generated method stub
        if (faces.length > 0) {
            Log.v("FaceDetection", faces.length + " faces");
            mFaceView.setFaces(faces);
        }
    }

    public void switchCamera() {
        mCamera.stopFaceDetection();
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;

        if(openCameraType == Camera.CameraInfo.CAMERA_FACING_BACK){
            openCameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }else {
            openCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        mCamera = Camera.open(openCameraType);
        mCamera.setFaceDetectionListener(this);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            if(mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
            Log.e(TAG,e.getMessage());
        }

        Camera.Parameters params = mCamera.getParameters();
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        Size size = previewSizes.get(0);
        params.setPreviewSize(size.width, size.height);
        mCamera.setParameters(params);
        mCamera.startPreview();
        if (params.getMaxNumDetectedFaces() > 0) {
            mCamera.startFaceDetection();
        } else {
            Log.e("FaceDetector", "Face detection is not supported.");
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("FaceCamera","onClick()");
        if (v.getId() == R.id.button){
            switchCamera();
        }
    }
}