package com.elyria.sescher;

import android.app.Application;

/**
 * Created by baimi on 2017/10/28.
 */

public class SescherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if (!checkDataUpdate())
//        {
//
//        }
    }

    private boolean checkDataUpdate() {
        /*
        * 读取excel文件
        * 文件存在则获取md5值
        * 与本地dp里的文件md5做判断，不同则更新数据
        * */
        return  false;

    }

    private String getFileMd5(String fileName)
    {
        return null;
    }


}
