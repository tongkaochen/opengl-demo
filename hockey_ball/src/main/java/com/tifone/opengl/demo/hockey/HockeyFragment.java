package com.tifone.opengl.demo.hockey;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifone.opengl.demo.common.Constants;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tloge;

public class HockeyFragment extends Fragment {

    private GLSurfaceView mGLSurfaceView;

    public static Fragment create(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FRAGMENT_TITLE, title);
        Fragment fragment = new HockeyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mGLSurfaceView = createGLSurfaceView(getContext(), 2/*eglVersion*/);
        if (mGLSurfaceView == null) {
            tloge("GLSurfaceView is null");
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            return mGLSurfaceView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseSurfaceView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeSurfaceView();
    }
    private void pauseSurfaceView() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
    }
    private void resumeSurfaceView() {
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }
    }
    private GLSurfaceView createGLSurfaceView(Context context, int eglVersion) {
        if (context == null || !isVersionSupported(context, eglVersion)) {
            return null;
        }
        GLSurfaceView surfaceView = new GLSurfaceView(context);
        surfaceView.setEGLContextClientVersion(eglVersion);
        // specify the renderer
        surfaceView.setRenderer(new HockeyBallRenderer(context));
        return surfaceView;
    }
    private boolean isVersionSupported(@NonNull Context context, int eglVersion) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        if (eglVersion == 2) {
            return info.reqGlEsVersion >= 0x2000;
        }
        return false;
    }
}
