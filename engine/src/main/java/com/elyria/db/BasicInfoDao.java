package com.elyria.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by baimi on 2017/10/28.
 */

@Dao
public interface BasicInfoDao {
    @Query("SELECT * FROM basic_info")
    List<BasicInfo> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<BasicInfo> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    BasicInfo findByName(String first, String last);

    @Insert
    void insertAll(BasicInfo... users);

    @Delete
    void delete(BasicInfo user);
}
