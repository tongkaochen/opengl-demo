package com.tifone.opengl.demo.hockey.air2d.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Create by Tifone on 2019/4/6.
 */
public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                builder.append(nextLine);
                builder.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return builder.toString();
    }
}
