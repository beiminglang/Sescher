package com.elyria.sescher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            List<ExcelInfo> excelInfos = ExcelOperate.importExcel(getAssets().open("data.xls"));
            Log.d("AAAA", "excel = " + excelInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
