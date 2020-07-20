package com.shoesdemo;


import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends BaseActivity {

    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mLayout = findViewById(R.id.swipe_layout);
        mRecyclerView = findViewById(R.id.recycler);
        setLeftIcon(R.mipmap.back);
    }

}
