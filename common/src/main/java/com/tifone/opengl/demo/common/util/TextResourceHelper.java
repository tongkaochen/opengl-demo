package com.tifone.opengl.demo.common.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.tifone.opengl.demo.common.util.LoggerConfig.tloge;

public class TextResourceHelper {
    private static String readText(InputStream is) {
        if (is == null) {
            return "";
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder result = new StringBuilder();
        String nextLine;
        try {
            // read text line by line
            while ((nextLine = br.readLine()) != null) {
                result.append(nextLine);
                // must be append '\n' to the end
                result.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // close stream
                isr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
    public static String getAssetText(Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        String result = "";
        try {
            is = am.open(fileName);
            result = readText(is);
        } catch (IOException e) {
            e.printStackTrace();
            tloge("open asset error", e);
        } finally {
            am.close();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static String getRawText(Context context, int resId) {
        InputStream is = null;
        String result = "";
        try {
            is = context.getResources().openRawResource(resId);
            result = readText(is);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
