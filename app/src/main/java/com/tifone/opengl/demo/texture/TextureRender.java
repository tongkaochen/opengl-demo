package com.tifone.opengl.demo.texture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tifone.opengl.demo.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class TextureRender implements GLSurfaceView.Renderer {

    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private static final String A_MATRIX = "a_Matrix";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private int mProgram;
    private FloatBuffer mBuffer;
    private Context mContext;
    private final int BYTES_PRE_FLOAT = 4;
    private final float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            1f, 1f,

            -1f, -1f,
            1f, 1f,
            -1f, 1f
    };
    private int aPositionLocation;
    private int uColorPositionLocation;
    private float[] projectionMatrix = new float[16];
    private int aMatrixLocation;

    public TextureRender(Context context) {
        mContext = context;

        mBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mBuffer.put(vertexData);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        String vertexShaderSource = TextSourceHelper.getShaderSource(
                mContext, R.raw.texture_vertex_shader);
        int vertexShader = TextureShaderHelper.compileVertexShader(vertexShaderSource);
        String fragmentShaderSource = TextSourceHelper.getShaderSource(
                mContext, R.raw.texture_fragment_shader);
        int fragmentShader = TextureShaderHelper.compileFragmentShader(fragmentShaderSource);
        mProgram = TextureShaderHelper.linkToProgram(vertexShader, fragmentShader);
        if (mProgram == 0) {
            return;
        }
        GLES20.glUseProgram(mProgram);

        aMatrixLocation = GLES20.glGetUniformLocation(mProgram, A_MATRIX);
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, projectionMatrix, 0);

        mBuffer.position(0);
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, 0, mBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        uColorPositionLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float aspectRatio = width > height ? (float) width / height : (float) height / width;
        if (width > height) {
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, projectionMatrix, 0);
        GLES20.glUniform4f(uColorPositionLocation, 1.0f, 0f, 0f, 1f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

    }
}
