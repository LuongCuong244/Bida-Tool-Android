package com.nguyenluongcuong.bidatool;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.nguyenluongcuong.bidatool.Data.Data;

public class ManagerAbleTouch extends Service {

    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private View viewAbleTouch;
    public static ImageButton img_able_touch;

    public static boolean ableTouch = true;
    private static boolean shouldClick = true;

    private int initialX;
    private int initialY;
    private int initialTouchX;
    private int initialTouchY;

    private SharedPreferences sharedPreferences;
    private GestureDetector gestureDetector;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        viewAbleTouch = LayoutInflater.from(this).inflate(R.layout.manager_able_touch, null);

        gestureDetector = new GestureDetector(ManagerAbleTouch.this, new SingleTapConfirm());
        img_able_touch = viewAbleTouch.findViewById(R.id.img_able_touch);
        img_able_touch.setClickable(false);
        setSizeImage();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        }else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        }

        params.x = 0;
        params.y = 0;
        try {
            windowManager.addView(viewAbleTouch, params);
        } catch (Exception e){
            Toast.makeText(this.getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        };

        onTouchManager_Able_Touch();
    }

    public static void setSizeImage(){
        if(img_able_touch == null){
            return;
        }
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(Properties.eyeRadius*2,Properties.eyeRadius*2);
        img_able_touch.setLayoutParams(parms);
    }

    private void onTouchManager_Able_Touch(){
        viewAbleTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        shouldClick = true;
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = (int) event.getRawX();
                        initialTouchY = (int) event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        if(ableTouch){
                            img_able_touch.setImageResource(R.drawable.eye_hide);
                            GameFrame.params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
                            ableTouch = false;
                            GameFrame.liLViewFrame.setVisibility(View.GONE);
                            GameFrame.windowManager.updateViewLayout(GameFrame.viewFrame,GameFrame.params);
                        }else{
                            img_able_touch.setImageResource(R.drawable.eye_appear);
                            GameFrame.params.flags = GameFrame.params.flags & WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE & WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL & WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
                            ableTouch = true;
                            GameFrame.liLViewFrame.setVisibility(View.VISIBLE);
                            GameFrame.windowManager.updateViewLayout(GameFrame.viewFrame,GameFrame.params);
                        }
                    }
                    case MotionEvent.ACTION_MOVE:{
                        shouldClick = false;
                        params.x = (int) (initialX + (event.getRawX() - initialTouchX));
                        params.y = (int) (initialY + (event.getRawY() - initialTouchY));
                        windowManager.updateViewLayout(viewAbleTouch, params);
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("aaa","Dừng");
        Data.saveData(sharedPreferences);
        try{
            if (viewAbleTouch != null) windowManager.removeView(viewAbleTouch);
        }catch (Exception e){
            Log.d("aaa",e.getMessage());
        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
