package com.elyria.sescher;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import android.util.Log;

import com.elyria.db.BasicInfo;
import com.elyria.engine.SearchEngine;

import java.io.IOException;
import java.util.List;

/**
 * Created by baimi on 2017/10/28.
 */

public class SescherApplication extends Application {

    public static final String BASIC_DATA_NAME = "basic_data.excel";
    //private

    @Override
    public void onCreate() {
        super.onCreate();
        if (!checkDataAvaliable())
        {

        }
    }

    private boolean checkDataAvaliable() {
        /*
        * 读取excel文件
        * 文件存在则获取md5值
        * 与本地dp里的文件md5做判断，不同则更新数据
        * */
        SharedPreferences sp = getApplicationContext().getSharedPreferences("info_sp", MODE_PRIVATE);
        String basicInfoMd5 = sp.getString("basic_info_md5", null);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + BASIC_DATA_NAME);

        File assertFile = null;
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(BASIC_DATA_NAME);
            writeBytesToFile(inputStream, assertFile);
        }
        catch (IOException e) {
            return false;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileMd5 = null;
        if (file.exists()) {
            if (basicInfoMd5 != null) {
                fileMd5 = getFileMD5(file);
                if (fileMd5 == basicInfoMd5) {
                    return true;
                }
            }
            fileMd5 = getFileMD5(file);
            // 写入md5
            // 写入数据
        } else {
            if (basicInfoMd5 == null) {
                if (assertFile == null) {
                    return  false;
                }
                fileMd5 = getFileMD5(assertFile);
                // 写入md5
                // 写入数据
            }
        }

        return  true;
    }

    public static void writeBytesToFile(InputStream is, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while((nbread = is.read(data)) > -1) {
                fos.write(data,0, nbread);
            }
        }
        catch (Exception e) {
            // logger.error("Exception",ex);
            e.printStackTrace();
        }
        finally{
            if (fos!=null){
                fos.close();
            }
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
