package com.tifone.opengl.demo.hockey.gles20;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.tifone.opengl.demo.common.util.TextResourceHelper;
import com.tifone.opengl.demo.hockey.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tloge;
import static com.tifone.opengl.demo.hockey.gles20.GLES20Factory.*;

public class TriangleShader extends HockeyShader {

    // x, y
    private static final int POSITION_COMPONENT_COUNT = 2;
    // r,g,b
    private static final int COLOR_COMPONENT_COUNT = 3;

    // the length of x,y,r,g,b
    private static final int STRIM_SIZE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PRE_FLOAT;
    private static final String A_POSITION_VERTEX = "a_Position";
    private static final String M_PROJECTION_VERTEX = "m_Projection";
    private static final String A_COLOR_VERTEX = "a_Color";
    private static final String U_COLOR = "u_Color";
    private final float[] mTableCoord = new float[] {
            0, 0.5f, 0f, 1f, 0f,
            -0.5f, 0, 1f, 0f, 0f,
            0.5f, 0, 0f, 0f, 1f
    };
    private float[] mProjection = new float[16];
    private int a_PositionLocation;
    private int u_ColorLocation;
    private FloatBuffer mData;
    private int a_ColorVertexLocation;
    private int m_ProjectionVertexLocation;

    public TriangleShader(Context context) {
        super(context);
        // use FloatBuffer to save the coordinate data.
        mData = ByteBuffer.allocateDirect(mTableCoord.length * BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mData.put(mTableCoord);
    }

    @Override
    public void build() {
        int vertexShader = compile(TriangleShader.TYPE_VERTEX);
        int fragmentShader = compile(TriangleShader.TYPE_FRAGMENT);

        // link program
        mProgram = createAndLinkProgram(vertexShader, fragmentShader);
        useProgram(mProgram);
    }

    @Override
    public void bindData() {
        if (mProgram == 0) {
            tloge("please set valid program first, call build method");
            return;
        }
        mData.position(0); // seek to first
        a_PositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION_VERTEX);
        a_ColorVertexLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR_VERTEX);
//        u_ColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);

        // projection
        m_ProjectionVertexLocation = GLES20.glGetUniformLocation(mProgram, M_PROJECTION_VERTEX);

        // set data to a_Position
        GLES20.glVertexAttribPointer(a_PositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, STRIM_SIZE, mData);
        GLES20.glEnableVertexAttribArray(a_PositionLocation);
        mData.position(POSITION_COMPONENT_COUNT); // offset
        // bind color data
        GLES20.glVertexAttribPointer(a_ColorVertexLocation, COLOR_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, STRIM_SIZE, mData);
        GLES20.glEnableVertexAttribArray(a_ColorVertexLocation);

    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {
        float aspectRatio = width > height ? (float)width / height : (float)height / width;
        if (width > height) {
            // landscape
            Matrix.orthoM(mProjection,0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            Matrix.orthoM(mProjection,0, -1/aspectRatio, 1/aspectRatio, -1f, 1f,  -1f, 1f);
        }
    }

    @Override
    public void draw() {
        // draw the content
        clearColorBufferBit();
//        GLES20.glUniform4fv(u_ColorLocation, 1, new float[]{0f, 0f, 255f, 255f}, 0);
        GLES20.glUniformMatrix4fv(m_ProjectionVertexLocation, 1, false, mProjection, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    @Override
    public String getShaderCode(int type) {
        // get the shader source code from raw
        switch (type) {
            case TYPE_VERTEX:
                return TextResourceHelper.getRawText(mContext, R.raw.hockey_vertex);
            case TYPE_FRAGMENT:
                return TextResourceHelper.getRawText(mContext, R.raw.hockey_fragment);
        }
        return "";
    }
}
