package com.tifone.opengl.demo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.tifone.opengl.demo.hockey.AirHockeyRender;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Create by Tifone on 2019/3/30.
 */
class FirstOpenGLRender implements GLSurfaceView.Renderer {


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // clear the rendering surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}