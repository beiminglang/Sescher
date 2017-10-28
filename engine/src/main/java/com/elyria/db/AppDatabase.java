package com.elyria.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by baimi on 2017/10/28.
 */

@Database(entities = {BasicInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BasicInfoDao basicInfoDao();
}
