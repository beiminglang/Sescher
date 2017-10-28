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

import com.elyria.sescher.func.SearchActivity;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn ;
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
        try {
            List<ExcelInfo> excelInfos = ExcelOperate.importExcel(getAssets().open("data.xls"));
            Log.d("AAAA", "excel = " + excelInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
