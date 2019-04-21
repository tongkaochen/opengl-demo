package com.tifone.opengl.demo.hockey;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Create by Tifone on 2019/4/6.
 */
public class AirHockeyActivity extends Activity {
    private GLSurfaceView mGLSurfaceView;
    private boolean isGLSurfaceViewSetup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isOpenGLES20Supported()) {
            mGLSurfaceView = new GLSurfaceView(this);
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setRenderer(new AirHockeyRender(this));
            setContentView(mGLSurfaceView);
            isGLSurfaceViewSetup = true;
        } else {
            Toast.makeText(this, "OpenGL ES 2.0 is not supported", Toast.LENGTH_LONG).show();
            isGLSurfaceViewSetup = false;
            finish();
        }
    }
    private boolean isOpenGLES20Supported() {
        if (Build.MODEL.contains("Android SDK built for x86")) {
            return true;
        }
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isGLSurfaceViewSetup) {
            mGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGLSurfaceViewSetup) {
            mGLSurfaceView.onResume();
        }
    }
}
