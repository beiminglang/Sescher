package com.elyria.sescher.func;

import com.elyria.sescher.func.bean.Bean;

import java.util.List;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class Contract {
    public interface Presenter {
        void search(String item);
         void subscribe();

         void unsubscribe();
    }
    public interface View{
        void showData(List<Bean> result);
        void showError();
    }
    public interface Model{
        void doSearch(String key,onResultListener listener);
    }
    public interface onResultListener {
        void onSearchSuccess(List<Bean> result);

        void onSearchError(String msg);

    }
}
