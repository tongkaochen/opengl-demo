package com.tifone.opengl.demo.hockey.gles20;

import android.opengl.GLES20;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tloge;

public class GLES20Factory {
    private static final int VERTEX_SHADER = 1;
    private static final int FRAGMENT_SHADER = 2;

    private static boolean compileShaderCode(int shader) {
        GLES20.glCompileShader(shader);
        int[] compileStatus = new int[1];
        // check error
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            // compile fail
            tloge("shader : " + shader + " compile fail");
            tloge("log: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            return false;
        }
        return true;
    }
    private static int compileShader(int shaderType, String code) {
        if (code == null || "".equals(code)) {
            tloge("invalid shader code");
            return 0;
        }
        int type;
        if (shaderType == VERTEX_SHADER) {
            type = GLES20.GL_VERTEX_SHADER;
        } else {
            type = GLES20.GL_FRAGMENT_SHADER;
        }
        // 1. create shader
        int shader = GLES20.glCreateShader(type);
        if (shader == 0) {
            tloge("shader create fail");
            return 0;
        }
        // 2. link shader code
        GLES20.glShaderSource(shader, code);
        // 3. compiler shader
        boolean success = compileShaderCode(shader);
        if (!success) {
            return 0;
        }
        return shader;
    }
    public static int compileVertexShader(String code) {
        return compileShader(VERTEX_SHADER, code);
    }
    public static int compileFragmentShader(String code) {
        return compileShader(FRAGMENT_SHADER, code);
    }
    public static int createAndLinkProgram(int vertexShader, int fragmentShader) {
        if (vertexShader <= 0 || fragmentShader <=0) {
            tloge("params error: vertexShader:" + vertexShader +
                    " fragmentShader: " + fragmentShader);
            return 0;
        }
        // 1. create opengl program
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            tloge("program create fail");
            return 0;
        }
        // 2. link shader to program
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        // 3. link program to opengl
        GLES20.glLinkProgram(program);
        // 4. check link status
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            tloge("link program error: \n" + GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            return 0;
        }
        return program;
    }

    public static void useProgram(int program) {
        if (program <= 0) {
            tloge("could use the invalid program: " + program);
            return;
        }
        GLES20.glUseProgram(program);
    }
    public static void clearColorBufferBit() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
    public static void drawColor(float r, float g, float b, float a) {
        GLES20.glClearColor(r, g, b, a);
    }
    public static void setViewport(int left, int top, int right, int bottom) {
        GLES20.glViewport(left, top, right, bottom);
    }
}
