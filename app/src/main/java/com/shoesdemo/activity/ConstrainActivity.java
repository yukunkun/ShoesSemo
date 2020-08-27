package com.shoesdemo.activity;

import android.content.Context;
import android.content.Intent;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;

public class ConstrainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ConstrainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_constrain;
    }

    @Override
    protected void initData() {

    }
}
