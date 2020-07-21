package com.shoesdemo.impl;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface AddType {
    public static final int PAY_TYPE_BANCK_PAY = 0;//银行卡交易
    public static final int PAY_TYPE_QRCODE_PAY = 1;//扫码正交易
    public static final int PAY_TYPE_QRCODE_PAY_REVERSE =2;//扫码反交易

}
