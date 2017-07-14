package com.klapper.ttsgame;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by c1103304 on 2017/7/14.
 */

public class STR {
    public static String head(String pStr, String pSpliter) {
        int spliterPos = pStr.indexOf(pSpliter);
        if (spliterPos < 0) return pStr;
        return pStr.substring(0, spliterPos);
    }

    public static String tail(String pStr, String pSpliter) {
        int spliterPos = pStr.indexOf(pSpliter);
        if (spliterPos < 0) return "";
        return pStr.substring(spliterPos + pSpliter.length());
    }

    public static String replace(String pStr, String fromPat, String toPat) {
        if (fromPat.length() == 0) return pStr;
        if (pStr.indexOf(fromPat) < 0) return pStr;
        StringBuffer rzStr = new StringBuffer();
        int strIdx = 0, nextIdx;
        while ((nextIdx = pStr.indexOf(fromPat, strIdx)) >= 0) {
            rzStr.append(pStr.substring(strIdx, nextIdx));
            rzStr.append(toPat);
            strIdx = nextIdx + fromPat.length();
        }
        rzStr.append(pStr.substring(strIdx));
        return rzStr.toString();
    }

    public static String expand(String pText, String pMacros) {
        String[] macros = pMacros.split("\\|");
        for (int i = 0; i < macros.length; i++) {
            String name = head(macros[i], "=");
            String expand = tail(macros[i], "=");
            pText = replace(pText, name, expand);
        }
        return pText;
    }

    public static String file2text(Context cx,String fileName, String encode) throws Exception {
        InputStream f = cx.getAssets().open(fileName);
        int size = f.available();
        byte[] buffer = new byte[size];
        f.read(buffer);
        f.close();
        //String text = new String(buffer);
        return new String(buffer, encode);
    }
}
