package com.elyria.sescher;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.elyria.db.BasicInfo;
import com.elyria.engine.SearchEngine;
import com.elyria.sescher.func.SearchActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        final SearchEngine instance = SearchEngine.getInstance();
        try {
            Observable.just(instance.importExcel(getAssets().open("data.xls"))).flatMap(new Function<List<BasicInfo>, ObservableSource<List<BasicInfo>>>() {
                @Override
                public ObservableSource<List<BasicInfo>> apply(List<BasicInfo> infos) throws Exception {
                    instance.initDatabase(getApplicationContext());
                    instance.clear();
                    instance.insertAll(infos);
                    List<BasicInfo> a = instance.search("A");
                    Log.d("AAA", "aa" + a);
                    return Observable.just(infos);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<BasicInfo>>() {
                        @Override
                        public void accept(List<BasicInfo> infos) throws Exception {
                            if (infos != null && infos.size() > 0) {
                                Log.d("AAA", "aa = " + infos.size());
                            }


                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
