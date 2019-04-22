package com.tifone.opengl.demo.hockey.air2d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tifone.opengl.demo.R;
import com.tifone.opengl.demo.hockey.air2d.util.ShaderHelper;
import com.tifone.opengl.demo.hockey.air2d.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.tifone.opengl.demo.util.LoggerConfig.tlogd;

/**
 * Create by Tifone on 2019/4/5.
 */
public class AirHockeyRender implements GLSurfaceView.Renderer {

    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private static final String A_COLOR = "a_Color";
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final String U_MATRIX = "u_Matrix";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int BYTES_PRE_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PRE_FLOAT;
    private final FloatBuffer vertexData;
    private int program;
    private Context mContext;
    private int uColorPosition;
    private int aPositionLocation;
    private int aColorLocation;
    private float[] projectionMatrix = new float[16];

    public float tableVertices[] = {
            0f, 0f,
            0f, 14f,
            9f, 14f,
            9f, 0f
    };
    public float tableVerticesWithTriangles[] = {
            // order of coordinates: x, y, R, G, B
            // tringle 1
            -0.55f, -0.85f, 1.0f, 1.0f, 1.0f,
            0.55f, 0.85f, 1.0f, 1.0f, 1.0f,
            -0.55f, 0.85f, 1.0f, 1.0f, 1.0f,
            // tringle 2
            -0.55f, -0.85f, 1.0f, 1.0f, 1.0f,
            0.55f, -0.85f, 1.0f, 1.0f, 1.0f,
            0.55f, 0.85f, 1.0f, 1.0f, 1.0f,

//            // tringle 3
//            -0.5f, -0.5f,
//            0.5f, 0.5f,
//            -0.5f, 0.5f,
//            // tringle 4
//            -0.5f, -0.5f,
//            0.5f, -0.5f,
//            0.5f, 0.5f,

            // triangle fan
            0,0, 1.0f, 1.0f, 0.0f,
            //
            -0.5f, -0.749f, 0.7f, 0.7f, 0.7f,
            -0.495f, -0.7495f , 0.7f, 0.7f, 0.7f,
            -0.49f, -0.75f, 0.7f, 0.7f, 0.7f,
            //
            //-0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.495f, -0.75f, 0.7f, 0.7f, 0.7f,
            0.495f, -0.7495f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.7495f, 0.7f, 0.7f, 0.7f,

            // 0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.7495f, 0.7f, 0.7f, 0.7f,
            0.495f, 0.7495f, 0.7f, 0.7f, 0.7f,
            0.495f, 0.75f, 0.7f, 0.7f, 0.7f,

            //-0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.495f, 0.75f, 0.7f, 0.7f, 0.7f,
            -0.495f, 0.7495f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.7495f, 0.7f, 0.7f, 0.7f,

            -0.5f, -0.7495f, 0.7f, 0.7f, 0.7f,
            //-0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

            // line1
            -0.5f, 0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0f, 1.0f, 0.0f, 0.0f,

            // mallets
            0f, -0.45f, 1.0f, 0.0f, 0.0f,
            0f, 0.45f, 0.0f, 0.0f, 1.0f
    };
    private int uMatrixPosition;

    public AirHockeyRender(Context context) {
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PRE_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);

        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_fragment_shader);

        tlogd(vertexShaderSource);
        tlogd(fragmentShaderSource);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        tlogd("vertex shader = " + vertexShader);
        tlogd("fragment shader = " + fragmentShader);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glUseProgram(program);
//        uColorPosition = GLES20.glGetUniformLocation(program, U_COLOR);
        uMatrixPosition = GLES20.glGetUniformLocation(program, U_MATRIX);
        GLES20.glUniformMatrix4fv(uMatrixPosition, 1, false, projectionMatrix, 0);

        vertexData.position(0);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        vertexData.position(POSITION_COMPONENT_COUNT);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        final float aspectRatio = width > height ? (float) width / height : (float) height / width;
        if (width > height) {
            // landscape
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio , aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            // Portrait or square
            Matrix.orthoM(projectionMatrix, 0, -1/aspectRatio, 1/aspectRatio,
                    -1f, 1f, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(uMatrixPosition, 1, false, projectionMatrix, 0);

        // draw table
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 6, 6);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 6, 14);

        // draw split line
        GLES20.glLineWidth(8.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 20, 2);

        // draw point
        GLES20.glDrawArrays(GLES20.GL_POINTS, 22, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 23, 1);
    }
}
