package com.tifone.opengl.demo.hockey.gles20;

import android.content.Context;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tloge;

public abstract class HockeyShader {
    public static final int TYPE_VERTEX = 1;
    public static final int TYPE_FRAGMENT = 2;
    public static final int BYTES_PRE_FLOAT = 4;
    protected final Context mContext;
    protected int mProgram;

    public HockeyShader(Context context) {
        mContext = context;
    }

    public abstract String getShaderCode(int type);
    public abstract void build();
    public abstract void bindData();
    public abstract void draw();

    protected int compile(int type) {
        String code = getShaderCode(type);
        switch (type) {
            case TYPE_VERTEX:
                return GLES20Factory.compileVertexShader(code);
            case TYPE_FRAGMENT:
                return GLES20Factory.compileFragmentShader(code);
        }
        return 0;
    }

    public void onSurfaceSizeChanged(int width, int height) {}
}
