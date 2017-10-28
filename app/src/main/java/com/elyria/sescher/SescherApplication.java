package com.elyria.sescher;

import android.app.Application;

/**
 * Created by baimi on 2017/10/28.
 */

public class SescherApplication extends Application {

    public static final String BASIC_DATA_NAME = "basic_data.excel";

    @Override
    public void onCreate() {
        super.onCreate();
        if (!checkDataUpdate())
        {

        }
    }

    private boolean checkDataUpdate() {
        /*
        * 读取excel文件
        * 文件存在则获取md5值
        * 与本地dp里的文件md5做判断，不同则更新数据
        * */
        


        return  false;

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
