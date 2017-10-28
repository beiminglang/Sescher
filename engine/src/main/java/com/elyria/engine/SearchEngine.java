package com.elyria.engine;

import android.content.Context;

/**
 * Created by jungletian on 2017/10/28.
 */

public class SearchEngine {

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
}
