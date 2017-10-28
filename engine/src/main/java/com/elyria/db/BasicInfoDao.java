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

    @Query("SELECT * FROM basic_info WHERE initial = :word")
    List<BasicInfo> findByInitial(String word);

    @Insert
    void insertAll(BasicInfo... infos);

    @Delete
    void delete(BasicInfo info);

    @Query("DELETE FROM basic_info")
    void clearAll();
}
