package com.tifone.opengl.demo.hockey.air2d.util;

import android.opengl.GLES20;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tlogd;
import static com.tifone.opengl.demo.common.util.LoggerConfig.tlogi;
import static com.tifone.opengl.demo.common.util.LoggerConfig.tlogw;

/**
 * Create by Tifone on 2019/4/6.
 */
public class ShaderHelper {
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }
    public static int linkProgram(int vertexShader, int fragmentShader) {
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            tlogw("could not create new program");
            return 0;
        }
        GLES20.glAttachShader(programObjectId, vertexShader);
        GLES20.glAttachShader(programObjectId, fragmentShader);
        GLES20.glLinkProgram(programObjectId);
        final int[] linkProgramStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkProgramStatus, 0);
        tlogi("result of linking program: \n" + GLES20.glGetProgramInfoLog(programObjectId));
        if (linkProgramStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            tlogw("Lining program failed.");
        }
        return programObjectId;
    }
    public static boolean validateProgram(int programId) {
        GLES20.glValidateProgram(programId);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        tlogi("Result of validating program: \n" + GLES20.glGetProgramInfoLog(programId));
        return validateStatus[0] != 0;
    }
    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            tlogw("Could not create new shader");
            return 0;
        }
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        GLES20.glCompileShader(shaderObjectId);

        // read compile status
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        tlogd("Result of compiling source: " + "\n" + shaderCode + "\n:" + GLES20.glGetShaderInfoLog(shaderObjectId));

        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId);
            tlogw("Compilation of shader failed");
            return 0;
        }
        return shaderObjectId;
    }
}
