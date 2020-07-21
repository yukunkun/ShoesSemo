package com.shoesdemo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public ActionBar actionBar;
    public Context mContext;
    private TextView mTvTitle;
    private ImageView mIvLeft;
    private ImageView mIvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        mContext = this;
        initActionbar();
        initData();
    }

    private void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        View view = actionBar.getCustomView();
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvLeft = view.findViewById(R.id.iv_left);
        mIvRight = view.findViewById(R.id.iv_right);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLeftClick(view);
            }
        });
        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRightClick(view);
            }
        });
    }

    public abstract int getLayoutView();

    protected abstract void initData();

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }
    }

    public void setLeftIcon(@DrawableRes int icon){
        mIvLeft.setImageResource(icon);
    }

    public void setRightIcon(@DrawableRes int icon){
        mIvRight.setImageResource(icon);
    }

    public void setLeftClick(View view){
        finish();
    }

    public void setRightClick(View view){

    }

    public void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
