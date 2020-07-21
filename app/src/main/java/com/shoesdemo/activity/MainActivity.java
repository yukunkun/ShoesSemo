package com.shoesdemo.activity;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private TextView mTvTitle;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private DrawerLayout mDrawerLayout;
    private TextView mTvAddGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mDrawerLayout = findViewById(R.id.drawer);
        mTvTitle = findViewById(R.id.tv_title);
        mIvLeft = findViewById(R.id.iv_left);
        mIvRight = findViewById(R.id.iv_right);
        mLayout = findViewById(R.id.swipe_layout);
        mRecyclerView = findViewById(R.id.recycler);
        mTvAddGoods = findViewById(R.id.tv_add_goods);

        mIvLeft.setImageResource(R.mipmap.icon_main);
        mIvRight.setImageResource(R.mipmap.icon_add);
        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mTvAddGoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                if(!mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.iv_right:
            case R.id.tv_add_goods:
                AddGoodsActivity.start(this,AddGoodsActivity.TYPE_ADD);
                break;

        }
    }
}
