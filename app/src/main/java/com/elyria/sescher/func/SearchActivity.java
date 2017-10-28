package com.elyria.sescher.func;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.elyria.sescher.R;
import com.elyria.sescher.adapter.ResultAdapter;
import com.elyria.sescher.func.bean.Bean;

import java.util.List;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class SearchActivity extends Activity implements Contract.View{
    private EditText editText;
    private RecyclerView recyclerView ;
    private SearchPresenter mPresenter;
    private ResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.input);
        editText.addTextChangedListener(watcher);

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ResultAdapter(this,null);
        recyclerView.setAdapter(mAdapter);
        mPresenter = new SearchPresenter(this);
    }

    TextWatcher watcher  = new TextWatcher() {
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
        if(mPresenter !=null){
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void showData(List<Bean> result) {
        mAdapter.setData(result);
    }

    @Override
    public void showError() {
        mAdapter.setData(null);
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
}