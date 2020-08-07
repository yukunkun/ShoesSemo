package com.shoesdemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;
import com.shoesdemo.adapter.RVAdapter;
import com.shoesdemo.data.Shoes;
import com.shoesdemo.data.ShoesModule;
import com.shoesdemo.service.AccessiblityService;
import com.shoesdemo.utils.Cn2Spell;
import com.shoesdemo.utils.PinyinComparator;
import com.shoesdemo.view.ISideBarSelectCallBack;
import com.shoesdemo.view.SideBar;
import com.shoesdemo.view.SwipeItemLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mLayout;
    private RecyclerView mRecyclerView;
    private TextView mTvHeader;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private DrawerLayout mDrawerLayout;
    private TextView mTvAddGoods;
    private List<ShoesModule> mShoesModules = new ArrayList<>();
    private RVAdapter mRvAdapter;
    private List<Shoes> mShoesList;
    private int mCurrentPosition = 0;
    private int mSuspensionHeight = 0;
    private LinearLayoutManager mLayoutManager;
    private SideBar mSideBar;
    private TextView mTvLetter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
        getShoesData();
        initListener();
    }

    private void initListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mLayoutManager.findViewByPosition(mCurrentPosition + 1);
                mSuspensionHeight = mTvHeader.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = mLayoutManager.findViewByPosition(mCurrentPosition + 1);
                //下一个item距离top位置小于header
                if (view != null) {
                    //同一个字母开头，不update
                    if (!mShoesModules.get(mCurrentPosition + 1).getLetter().equals(mTvHeader.getText())) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mTvHeader.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mTvHeader.setY(0);
                        }
                    }
                }


                if (mCurrentPosition != mLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = mLayoutManager.findFirstVisibleItemPosition();
                    updateSuspensionBar();
                    mTvHeader.setY(0);
                }
            }
        });
        updateSuspensionBar();

        mSideBar.setOnStrSelectCallBack(new ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                mTvLetter.setText(selectStr);
                mRecyclerView.scrollToPosition(index);
            }

            @Override
            public void onSelectEnd() {
                mTvLetter.setVisibility(View.GONE);
            }

            @Override
            public void onSelectStart() {
                mTvLetter.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateSuspensionBar() {
        mTvHeader.setText(mShoesModules.get(mCurrentPosition).getLetter());
    }

    private void getShoesData() {
        mShoesModules.clear();
        mShoesList = LitePal.findAll(Shoes.class);
        mShoesModules.addAll(getSortModule());
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
        mTvHeader = findViewById(R.id.tv_header);
        mIvLeft = findViewById(R.id.iv_left);
        mIvRight = findViewById(R.id.iv_right);
        mLayout = findViewById(R.id.swipe_layout);
        mRecyclerView = findViewById(R.id.recycler);
        mTvAddGoods = findViewById(R.id.tv_add_goods);
        mSideBar = findViewById(R.id.sidebar);
        mTvLetter = findViewById(R.id.tv_letter);
        mRvAdapter = new RVAdapter(mShoesModules, this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRvAdapter);
        mIvLeft.setImageResource(R.mipmap.icon_main);
        mIvRight.setImageResource(R.mipmap.icon_add);
        mLayout.setOnRefreshListener(this);
        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mTvAddGoods.setOnClickListener(this);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mSideBar.setStyle(SideBar.STYLE_NORMAL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.iv_right:
                AddGoodsActivity.start(this, AddGoodsActivity.TYPE_ADD);
                break;
            case R.id.tv_add_goods:
//                AddGoodsActivity.start(this, AddGoodsActivity.TYPE_ADD);
//                ShoesDetailActivity.start(this);

                boolean isAuto=isAccessibilitySettingsOn(this);
                if(isAuto){
                    startService(new Intent(MainActivity.this, AccessiblityService.class));
                }else{
                    Intent intent =new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
                break;
        }
    }

    private List<ShoesModule> getSortModule() {
        List<ShoesModule> filterDateList = new ArrayList<ShoesModule>();
        for (int i = 0; i < mShoesList.size(); i++) {
            String pinYinFirstLetter = Cn2Spell.getPinYinFirstLetter(mShoesList.get(i).getGoodsNo());
            ShoesModule sortModel = new ShoesModule(pinYinFirstLetter.toUpperCase().charAt(0) + "", mShoesList.get(i));
            filterDateList.add(sortModel);
        }
        Collections.sort(filterDateList, new PinyinComparator());
        return filterDateList;
    }

    @Override
    public void onRefresh() {
        getShoesData();
    }

    public boolean isAccessibilitySettingsOn(Context mContext) {

        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + AccessiblityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)){
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {

        }
        return false;
    }



}
