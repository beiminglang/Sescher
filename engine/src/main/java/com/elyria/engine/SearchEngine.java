package com.elyria.engine;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.elyria.db.AppDatabase;

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




}
