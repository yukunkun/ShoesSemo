package com.shoesdemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;
import com.shoesdemo.data.Shoes;
import com.shoesdemo.dialog.ColorDialog;
import com.shoesdemo.dialog.SizeDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AddGoodsActivity extends BaseActivity implements View.OnClickListener {

    public static final int TYPE_ADD = 0;//
    public static final int TYPE_EDIE = 1;//
    private EditText mEtNum;
    private EditText mEtRemark;
    private TextView mTvColor;
    private TextView mTvSize;
    private Shoes mShoes;

    @IntDef({TYPE_ADD, TYPE_EDIE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AddType { }

    private int type;

    public static void start(Context context,@AddType int type){
        Intent intent=new Intent(context,AddGoodsActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    public static void start(Context context,@AddType int type,Shoes shoes){
        Intent intent=new Intent(context,AddGoodsActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("shoes",shoes);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getIntExtra("type",TYPE_ADD);
        mShoes = (Shoes) getIntent().getSerializableExtra("shoes");
        if(type==TYPE_ADD){
            setTitle("添加");
        }else {
            setTitle("编辑");
            mTvSize.setText(mShoes.getSize());
            mTvColor.setText(mShoes.getColor());
            mEtNum.setText(mShoes.getGoodsNo());
            if(!TextUtils.isEmpty(mShoes.getRemark())){
                mEtRemark.setText(mShoes.getRemark());
            }
        }
        setRightIcon(R.mipmap.icon_sava);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_add_goods;
    }

    @Override
    protected void initData() {
        mEtNum = findViewById(R.id.et_number);
        mEtRemark = findViewById(R.id.et_remark);
        mTvColor = findViewById(R.id.tv_color);
        mTvSize = findViewById(R.id.tv_size);
        mTvColor.setOnClickListener(this);
        mTvSize.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_color:
                ColorDialog instance = ColorDialog.getInstance();
                instance.show(getSupportFragmentManager(),"");
                break;
            case R.id.tv_size:
                SizeDialog sizeDialog = SizeDialog.getInstance();
                sizeDialog.show(getSupportFragmentManager(),"");
                break;
        }
    }

    public void getColor(String color){
        mTvColor.setText(color);
    }

    public void getSize(String size){
        mTvSize.setText(size);
    }

    @Override
    public void setRightClick(View view) {
        if(TextUtils.isEmpty(mEtNum.getText().toString().trim())){
            showToast("请输入货号");
            return;
        }
        Shoes shoes=new Shoes();
        shoes.setColor(mTvColor.getText().toString());
        shoes.setSize(mTvSize.getText().toString());
        shoes.setGoodsNo(mEtNum.getText().toString().trim());
        if(!TextUtils.isEmpty(mEtRemark.getText().toString().trim())){
            shoes.setRemark(mEtRemark.getText().toString().trim());
        }
        if(type==TYPE_ADD){
            shoes.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        }else {
            shoes.setTimeStamp(mShoes.getTimeStamp());
            mShoes.delete();
        }
        shoes.save();
        showToast("保存成功");
    }
}
