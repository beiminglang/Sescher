package com.elyria.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by baimi on 2017/10/28.
 */

@Entity(tableName = "basic_info")
public class BasicInfo {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "initial")
    private String initial;

    @ColumnInfo(name = "english_name")
    private String englishName;

    @ColumnInfo(name = "chinese_name")
    private  String chineseName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
