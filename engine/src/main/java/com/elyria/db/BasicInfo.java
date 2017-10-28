package com.elyria.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by baimi on 2017/10/28.
 */

@Entity(tableName = "basic_info")
public class BasicInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "initial")
    private String initial;

    @ColumnInfo(name = "name")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BasicInfo{" +
                "id=" + id +
                ", initial='" + initial + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
