package com.elyria.sescher.func;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.elyria.sescher.Constants;
import com.elyria.sescher.func.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class SearchModel implements Contract.Model {
    
    @Override
    public void doSearch(final String key, final Contract.onResultListener listener) {
        test(key,listener);
    }
    
    
    private Contract.onResultListener tmpListener;
    private void  test(final String key, final Contract.onResultListener listener){
        tmpListener = listener;
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<Bean> names = new ArrayList<>();
                Bean item;
                for (String name: Constants.NAMES){
                    item = new Bean(name);
                    names.add(item);
                }

                List<Bean> result = new ArrayList<>();
                for( int i =0,size = names.size();i<size;i++){
                    item = names.get(i);
                    if (item.getResult().equals(key)) {
                        result.add(item);
                    }
                }
                Message msg = handler.obtainMessage();
                msg.obj = result;
                handler.sendMessage(msg);
                
            }
        }.start();
    }
    
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            List<Bean> result = (List<Bean>) msg.obj;
            if(result.size()>0){
                tmpListener.onSearchSuccess(result);
            }else {
                tmpListener.onSearchError("查找失败");
            }
        }
    };
}
