package com.shoesdemo.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;
import com.shoesdemo.adapter.RVAdapter;
import com.shoesdemo.data.Shoes;
import com.shoesdemo.data.ShoesModule;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private TextView mTvTitle;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private DrawerLayout mDrawerLayout;
    private TextView mTvAddGoods;
    private List<ShoesModule> mShoesModules=new ArrayList<>();
    private RVAdapter mRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
        getShoesData();
    }

    private void getShoesData() {
        mShoesModules.clear();
        List<Shoes> shoesList = LitePal.findAll(Shoes.class);
        for (int i = 0; i < shoesList.size(); i++) {
            ShoesModule shoesModule=new ShoesModule("",shoesList.get(i));
            mShoesModules.add(shoesModule);
        }
        mLayout.setRefreshing(false);
        mRvAdapter.notifyDataSetChanged();

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
        mRvAdapter = new RVAdapter(mShoesModules, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRvAdapter);
        mIvLeft.setImageResource(R.mipmap.icon_main);
        mIvRight.setImageResource(R.mipmap.icon_add);
        mLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        getShoesData();
    }
}
