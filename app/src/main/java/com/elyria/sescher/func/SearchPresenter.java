package com.elyria.sescher.func;

import com.elyria.db.BasicInfo;

import java.util.List;

/**
 * Created by wang.lichen on 2017/10/28.
 */

public class SearchPresenter implements Contract.Presenter,Contract.onResultListener {
    private Contract.View mView;
    private Contract.Model mModel;
    
    public SearchPresenter(Contract.View view) {
        mView =view;
        mModel  =new SearchModel();
    }
    
    @Override
    public void search(String key) {
        mModel.doSearch(key,this);
    }

    @Override
    public void subscribe() {
        
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void onSearchSuccess(List<BasicInfo> result) {
        mView.showData(result);
    }

    @Override
    public void onSearchError(String msg) {
        mView.showError();
    }
}
