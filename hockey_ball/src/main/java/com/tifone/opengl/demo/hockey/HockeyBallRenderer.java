package com.tifone.opengl.demo.hockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.tifone.opengl.demo.hockey.gles20.HockeyShader;
import com.tifone.opengl.demo.hockey.gles20.HockeyShader2D;
import com.tifone.opengl.demo.hockey.gles20.TriangleShader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tlogd;
import static com.tifone.opengl.demo.hockey.gles20.GLES20Factory.*;

// opengl renderer
// use FloatBuffer to add data to GL
public class HockeyBallRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private final HockeyShader mShader;

    public HockeyBallRenderer(Context context) {
        mContext = context;
        mShader = new HockeyShader2D(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        tlogd("onSurfaceCreated");
        drawColor(0f, 1f, 1f, 1f);
        // create shade and compile shader
        mShader.build();
        mShader.bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        tlogd("onSurfaceChanged, width: " + width + " height: " + height);
        // set display area
        setViewport(0, 0, width, height);
        mShader.onSurfaceSizeChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mShader.draw();
    }
}
