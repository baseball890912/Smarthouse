package com.example.baseb.facecamera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.hardware.Camera.Face;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;


public class FaceView extends View implements Runnable ,SensorEventListener {

    Resources res = this.getContext().getResources();
    Bitmap kawamuso = BitmapFactory.decodeResource(res, R.drawable.kawauso);


    /** デバッグ用 */
    private static final boolean DEBUG = true;
    private static final String TAG = "OrientationListener";
    /** 行列数 */
    private static final int MATRIX_SIZE = 16;
    /** 三次元(XYZ) */
    private static final int DIMENSION = 3;
    /** センサー管理クラス */
    private SensorManager mManager;
    /** 地磁気行列 */
    private float[] mMagneticValues;
    /** 加速度行列 */
    private float[] mAccelerometerValues;
    /** X軸の回転角度 */
    private int mPitchX;
    /** Y軸の回転角度 */
    private int mRollY;
    /** Z軸の回転角度(方位角) */
    private int mAzimuthZ;

    public synchronized int getAzimuth() {
        return mAzimuthZ;
    }


    /** 塗りつぶし設定の {@link Paint} です。 */
    private Paint mPaint;

    /** 検出した顔情報の配列です。 */
    private Face[] mFaces;

    public FaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FaceView(Context context) {
        super(context);
        initialize();
    }

    /** 初期化します。 */
    private void initialize() {
        // 塗りつぶしの設定
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setAlpha(128);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 検出した顔情報の配列をセットします。
     * @param faces 検出した顔情報の配列
     */
    public void setFaces(Face[] faces) {
        mFaces = faces;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFaces == null) {
            return;
        }
        for (Face face : mFaces) {
            if (face == null) {
                continue;
            }
            // 短形で塗りつぶす
            Matrix matrix = new Matrix();
            // スケールの算出
            matrix.postScale(getWidth() / 2000f, getHeight() / 2000f);
            // 座標の移動
            matrix.postTranslate(getWidth() / 2f, getHeight() / 2f);
            // Canvas の保存
            int saveCount = canvas.save();
            //matrix.postRotate(90.0f);
            // Matrix を適用
            matrix.setRotate(mAzimuthZ, kawamuso.getWidth()/2, kawamuso.getHeight()/2);
            canvas.concat(matrix);
            // 描画
            //canvas.drawRect(face.rect, mPaint);

            Paint  paint  = new Paint();
            Rect prect = new Rect(0, 0, kawamuso.getWidth(), kawamuso.getHeight());
            //canvas.drawBitmap(kawamuso,face.rect.centerX()*2,face.rect.centerY()*2,paint);
            canvas.drawBitmap(kawamuso,prect,face.rect,paint);
            // リストア
            canvas.restoreToCount(saveCount);


        }

    }

    @Override
    public void run() {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // センサーイベント
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                // 地磁気センサー
                mMagneticValues = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                // 加速度センサー
                mAccelerometerValues = event.values.clone();
                break;
            default:
                // それ以外は無視
                return;
        }
        if (mMagneticValues != null && mAccelerometerValues != null) {
            float[] rotationMatrix = new float[MATRIX_SIZE];
            float[] inclinationMatrix = new float[MATRIX_SIZE];
            float[] remapedMatrix = new float[MATRIX_SIZE];
            float[] orientationValues = new float[DIMENSION];
            // 加速度センサーと地磁気センサーから回転行列を取得
            SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, mAccelerometerValues, mMagneticValues);
            SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remapedMatrix);
            SensorManager.getOrientation(remapedMatrix, orientationValues);
            // ラジアン値を変換し、それぞれの回転角度を取得する
            mAzimuthZ = radianToDegrees(orientationValues[0]);
            mPitchX = radianToDegrees(orientationValues[1]);
            mRollY = radianToDegrees(orientationValues[2]);
            if (DEBUG) {
                Log.d(TAG, "X=" + mPitchX + "Y=" + mRollY + "Z=" + mAzimuthZ);
            }
        }
    }
    private int radianToDegrees(float angrad) {
        return (int) Math.floor(angrad >= 0 ? Math.toDegrees(angrad) : 360 + Math.toDegrees(angrad));
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
