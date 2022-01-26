package com.nguyenluongcuong.bidatool;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.nguyenluongcuong.bidatool.Data.Data;

public class ManagerAbleTouch extends Service {

    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private View viewAbleTouch;
    public static ImageButton img_able_touch;

    private boolean ableTouch = true;
    private boolean shouldClick = true;

    private int initialX;
    private int initialY;
    private int initialTouchX;
    private int initialTouchY;

    private SharedPreferences sharedPreferences;
    public static boolean readDataSuccessfully = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        readDataSuccessfully = Data.getData(sharedPreferences);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        viewAbleTouch = LayoutInflater.from(this).inflate(R.layout.manager_able_touch, null);

        img_able_touch = viewAbleTouch.findViewById(R.id.img_able_touch);
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
        Log.d("aaa","" + Properties.eyeRadius*2);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(Properties.eyeRadius*2,Properties.eyeRadius*2);
        img_able_touch.setLayoutParams(parms);
    }

    private void onTouchManager_Able_Touch(){
        img_able_touch.setOnTouchListener(new View.OnTouchListener() {
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
                        if(shouldClick){
                            if(ableTouch){
                                img_able_touch.setImageResource(R.drawable.eye_hide);
                                GameFrame.params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
                                ableTouch = false;
                                GameFrame.windowManager.updateViewLayout(GameFrame.viewFrame,GameFrame.params);
                            }else{
                                img_able_touch.setImageResource(R.drawable.eye_appear);
                                GameFrame.params.flags = GameFrame.params.flags & WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE & WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL & WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
                                ableTouch = true;
                                GameFrame.windowManager.updateViewLayout(GameFrame.viewFrame,GameFrame.params);
                            }
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
}
