package com.elyria.sescher.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class ToastUtils {
    private static long showTime;
    private static long firstTime;
    private static String message;
    private static Toast toast;
    
    
    
    public static void showToast(Context context,@NonNull String msg){
        if(toast== null){
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show() ;
            firstTime = System.currentTimeMillis() ;
            message = msg;
        }else{
            showTime  = System.currentTimeMillis();
            if(msg.equals(message)&&(showTime - firstTime <= Toast.LENGTH_SHORT)){
                
            }else{
                message = msg;
                toast.setText(msg);
                toast.show();
            }
        }
    }
}
