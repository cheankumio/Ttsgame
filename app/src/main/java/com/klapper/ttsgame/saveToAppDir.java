package com.klapper.ttsgame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by c1103304 on 2017/7/27.
 */


public class saveToAppDir {

    // 更新本地資料
    public static void put(Context cx, String _fileName, String data) {
        try {
            FileOutputStream outputStream = cx.openFileOutput(_fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 取得本地資料
    public static String get(Context cx,String _fileName) {
        StringBuffer sb = new StringBuffer();
        String filePath = cx.getFilesDir().toString()+"/"+_fileName;
        try {
            File file = new File(filePath);
            InputStream inputstream = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
