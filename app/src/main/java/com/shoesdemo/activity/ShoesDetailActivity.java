package com.shoesdemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;
import com.shoesdemo.view.FlipClockView;
import com.shoesdemo.view.MyClockView;

import java.util.Calendar;
import java.util.Date;

public class ShoesDetailActivity extends BaseActivity implements View.OnClickListener, MyClockView.DownCountTimerListener {

    Button mBtn;
    private MyClockView myClockView;
    private FlipClockView mFlipClockView;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ShoesDetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_shoes_detail;
    }

    @Override
    protected void initData() {
        mBtn = (Button) findViewById(R.id.button1);
        mBtn.setOnClickListener(this);
        myClockView = (MyClockView) findViewById(R.id.clockView);
        mFlipClockView = findViewById(R.id.flip);
        myClockView.setDownCountTimerListener(this);
//        mFlipClockView.init(this);
        mFlipClockView.setClockBackground(getDrawable(R.drawable.time_bg));
        mFlipClockView.setClockTextColor(getResources().getColor(R.color.color_white));
        mFlipClockView.setClockTextSize(40);
        mFlipClockView.setFlipDirection(true);
        CountDownTimer countDownTimer=new CountDownTimer(50000,1000) {
            @Override
            public void onTick(long l) {
                mFlipClockView.setClockTime(l/1000+"");
                mFlipClockView.smoothFlip();
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(ShoesDetailActivity.this,date.toString(),Toast.LENGTH_SHORT).show();
//                tvTime.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true}).build();
         //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                myClockView.setDownCountTime(1000L * 60L + 1000L * 12L);
                myClockView.startDownCountTimer();
                break;

            default:
                break;
        }
    }

    public void stopDownCountTimer() {
        Toast.makeText(this,"结束了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
