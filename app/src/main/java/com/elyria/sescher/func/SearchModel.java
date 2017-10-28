package com.elyria.sescher.func;

import android.util.Log;

import com.elyria.db.BasicInfo;
import com.elyria.engine.SearchEngine;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class SearchModel implements Contract.Model {

    @Override
    public void doSearch(final String key, final Contract.onResultListener listener) {
//        test(key,listener);
        String searchKey = "";
        if (key != null) {
            searchKey = key.toLowerCase();
        }
        Observable.just(searchKey).map(new Function<String, List<BasicInfo>>() {
            @Override
            public List<BasicInfo> apply(String key) throws Exception {
                String s = Thread.currentThread().toString();
                Log.d("AAA", "s3 = " + s);
                return SearchEngine.getInstance().search(key);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BasicInfo>>() {
                    @Override
                    public void accept(List<BasicInfo> infos) throws Exception {
                        listener.onSearchSuccess(infos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onSearchError(throwable.getMessage());
                    }
                });
    }
}
