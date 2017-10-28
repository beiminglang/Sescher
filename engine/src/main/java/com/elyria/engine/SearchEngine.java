package com.elyria.engine;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.elyria.db.AppDatabase;
import com.elyria.db.BasicInfo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public void insertAll(List<BasicInfo> infos) {
        db.basicInfoDao().insertAll(infos);
    }

    public void insertAll(BasicInfo... infos) {
        db.basicInfoDao().insert(infos);
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

    public List<BasicInfo> importExcel(InputStream in) throws IOException {
        List<BasicInfo> result = null;
        try {
            result = new ArrayList<>();
            Workbook wb = new HSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                Cell initial = row.getCell(1);
                Cell name = row.getCell(2);
                if (initial != null && name != null && initial.getCellTypeEnum() != CellType.BLANK && name.getCellTypeEnum() != CellType.BLANK) {
                    BasicInfo info = new BasicInfo();
                    info.setInitial(initial.getStringCellValue());
                    info.setName(name.getStringCellValue());
                    result.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return result;
    }
}
