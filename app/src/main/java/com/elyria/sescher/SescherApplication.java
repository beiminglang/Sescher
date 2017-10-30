package com.elyria.sescher;

import android.app.Application;

import com.elyria.engine.SearchEngine;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by baimi on 2017/10/28.
 */

public class SescherApplication extends Application {

    public static final String BASIC_DATA_NAME = "basic_data.xls";

    public static String getFileMD5(InputStream in) {
        if (in == null)
            return null;
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
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

    @Override
    public void onCreate() {
        super.onCreate();
        SearchEngine.getInstance().initDatabase(this);
    }
}
