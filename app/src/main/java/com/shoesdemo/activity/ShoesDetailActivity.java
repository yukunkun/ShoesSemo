package com.shoesdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;
import com.shoesdemo.view.FlipClockView;
import com.shoesdemo.view.MyClockView;

import java.util.ArrayList;
import java.util.List;

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
