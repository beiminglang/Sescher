package com.elyria.engine;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.elyria.db.AppDatabase;
import com.elyria.db.BasicInfo;

import java.util.List;

/**
 * Created by jungletian on 2017/10/28.
 */

public class SearchEngine {

    private AppDatabase db;

    private static class Holder {
        private static final SearchEngine INSTANCE = new SearchEngine();

    }

    private SearchEngine() {
    }

    public static final SearchEngine getInstance() {
        return Holder.INSTANCE;
    }

    public void transData(Context context) {


    }

    public void initDatabase(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }

    public void insert() {
        db.basicInfoDao().insertAll();
    }

    public void delete(BasicInfo info) {
        db.basicInfoDao().delete(info);
    }

    public List<BasicInfo> search(String word) {
        return db.basicInfoDao().findByInitial(word);
    }

    public void clear() {
        db.basicInfoDao().clearAll();
    }


}
