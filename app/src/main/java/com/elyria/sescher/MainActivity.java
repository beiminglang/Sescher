package com.elyria.sescher;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.elyria.db.BasicInfo;
import com.elyria.engine.SearchEngine;
import com.elyria.sescher.adapter.ResultAdapter;
import com.elyria.sescher.func.Contract;
import com.elyria.sescher.func.SearchPresenter;
import com.elyria.sescher.tools.ToastUtils;
import com.elyria.sescher.widget.ClearEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.elyria.sescher.SescherApplication.BASIC_DATA_NAME;
import static com.elyria.sescher.SescherApplication.getFileMD5;

public class MainActivity extends AppCompatActivity implements Contract.View {

    Context mContext;
    private static final int REQUEST_CODE = 1001;

    private ClearEditText editText;
    private RecyclerView recyclerView;
    private SearchPresenter mPresenter;
    private ResultAdapter mAdapter;
    private View progress;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("info_sp", MODE_PRIVATE);
        progress = findViewById(R.id.progress);
        mContext = this;
        editText = findViewById(R.id.input);
        editText.setTextChangedCallBack(new ClearEditText.onTextChangedCallBack() {
            @Override
            public void onChanged(CharSequence s) {
                String key = s.toString().trim();
                if (!TextUtils.isEmpty(key)) {
                    mPresenter.search(key);
                } else {
                    mAdapter.setData(null);
                }
            }
        });
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ResultAdapter(this, null);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                return false;
            }
        });


        mPresenter = new SearchPresenter(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionRead = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionRead == PackageManager.PERMISSION_GRANTED) {
                request();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
            request();
        }
    }

    private void request() {
        if (!checkDataAvaliable()) {
            ToastUtils.showToast(this, "数据异常");
            sp.edit().clear().apply();
            progress.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults == null || grantResults.length == 0) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                request();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("我们需要您的允许获取网络状态和读取存储")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                request();
                            }
                        }).create();
                dialog.show();
            }
        }
    }

    private void initDatabase(InputStream file, final String md5) {
        final SearchEngine instance = SearchEngine.getInstance();
        try {
            Observable.just(file)
                    .map(new Function<InputStream, List<BasicInfo>>() {
                        @Override
                        public List<BasicInfo> apply(InputStream inputStream) throws Exception {
                            List<BasicInfo> basicInfos = instance.importExcel(inputStream);
                            instance.clear();
                            instance.insertAll(basicInfos);
                            return basicInfos;
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<BasicInfo>>() {
                        @Override
                        public void accept(List<BasicInfo> infos) throws Exception {
                            if (infos != null && infos.size() > 0) {
                                sp.edit().putString("basic_info_md5", md5).apply();
                                progress.setVisibility(View.GONE);
                            } else {
                                sp.edit().clear().apply();
                                ToastUtils.showToast(mContext, "数据异常");
                                progress.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mPresenter.search(editable.toString().trim());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void showData(List<BasicInfo> result) {
        mAdapter.setData(result);
    }

    @Override
    public void showError() {
        mAdapter.setData(null);
        ToastUtils.showToast(this, "error");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private boolean checkDataAvaliable() {
        /*
        * 读取excel文件
        * 文件存在则获取md5值
        * 与本地dp里的文件md5做判断，不同则更新数据
        * */
        String basicInfoMd5 = sp.getString("basic_info_md5", null);
        // sd
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BASIC_DATA_NAME);

        // assets
        try {
            String sdFileMd5 = null;
            if (file.exists()) {
                if (basicInfoMd5 != null) {
                    sdFileMd5 = getFileMD5(new FileInputStream(file));
                    if (basicInfoMd5.equals(sdFileMd5)) {
                        progress.setVisibility(View.GONE);
                        return true;
                    }
                }
                sdFileMd5 = getFileMD5(new FileInputStream(file));
                if (sdFileMd5 == null) {
                    return false;
                }
                initDatabase(new FileInputStream(file), sdFileMd5);
                return true;
            } else {
                if (basicInfoMd5 == null) {
                    sdFileMd5 = getFileMD5(getAssets().open("basic_data.xls"));
                    if (sdFileMd5 == null) {
                        return false;
                    }
                    initDatabase(getAssets().open("basic_data.xls"), sdFileMd5);
                    return true;
                } else {
                    progress.setVisibility(View.GONE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public interface Callback {
        public void onLoadFinsih(boolean success);
    }
}
