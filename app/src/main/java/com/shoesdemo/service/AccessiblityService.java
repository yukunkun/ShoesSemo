package com.shoesdemo.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Random;

public class AccessiblityService extends AccessibilityService {

    private String TAG=getClass().getName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG,"onAccessibilityEvent");
        if(event==null ||event.getPackageName()==null){
            return;
        }
        if(event.getPackageName().equals("com.ss.android.ugc.aweme.lite")){
            AccessibilityNodeInfo source = event.getSource();
            if(source!=null&&source.getClass().equals("androidx.recyclerview.widget.RecyclerView")){
                Log.i(TAG,source.toString());
                startThread(source);
            }

        }
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG,"onInterrupt");
    }

    private void startThread(final AccessibilityNodeInfo nodeInfo){
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    while (true){
                        Random random=new Random();
                        int i = random.nextInt(2000);
                        Thread.sleep(1000,i);
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
