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


public class FaceView extends View implements Runnable{

    Resources res = this.getContext().getResources();
    Bitmap kawamuso = BitmapFactory.decodeResource(res, R.drawable.kawauso);

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
        mPaint.setAlpha(200);
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
            Matrix matrix = new Matrix();
            matrix.postScale(getWidth() / 2000f, getHeight() / 2000f);
            matrix.postTranslate(getWidth() / 2f, getHeight() / 2f);
            int saveCount = canvas.save();
            canvas.concat(matrix);

            Rect rect = new Rect(0, 0, kawamuso.getWidth(), kawamuso.getHeight());
            canvas.drawBitmap(kawamuso,rect,face.rect,mPaint);

            canvas.restoreToCount(saveCount);


        }

    }

    @Override
    public void run() {

    }
}
