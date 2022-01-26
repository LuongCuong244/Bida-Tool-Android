package com.nguyenluongcuong.bidatool;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameFrame extends Service {


    public static WindowManager windowManager;
    public static WindowManager.LayoutParams params;
    public static View viewFrame;
    private LinearLayout gameFrame;

    public static DrawFrame toolFrame;
    public static LinearLayout liLViewFrame, liLBarOne, liLBarTwo;
    public static ImageButton hole_one,hole_two,hole_three,hole_four,hole_five,hole_six;
    public static ImageButton option_one, option_two, option_three, option_four, option_five;
    public static int widthScreen = 1500;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        viewFrame = LayoutInflater.from(this).inflate(R.layout.game_frame, null);

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        }else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
        }

        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        try {
            windowManager.addView(viewFrame, params);
        } catch (Exception e){
            Toast.makeText(this.getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        };

        initView();
        setRadiusOfIcons();

        onClickHole_One();
        onClickHole_Two();
        onClickHole_Three();
        onClickHole_Four();
        onClickHole_Five();
        onClickHole_Six();
        onClickOption_One();
        onClickOption_Two();
        onClickOption_Three();
        onClickOption_Four();
        onClickOption_Five();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gameFrame.setVisibility(View.GONE);
            windowManager.updateViewLayout(viewFrame, params);
        }
    }

    private void initView(){
        toolFrame = viewFrame.findViewById(R.id.draw_frame);
        gameFrame = viewFrame.findViewById(R.id.game_frame);
        hole_one = viewFrame.findViewById(R.id.hole_one);
        hole_two = viewFrame.findViewById(R.id.hole_two);
        hole_three = viewFrame.findViewById(R.id.hole_three);
        hole_four = viewFrame.findViewById(R.id.hole_four);
        hole_five = viewFrame.findViewById(R.id.hole_five);
        hole_six = viewFrame.findViewById(R.id.hole_six);
        option_one = viewFrame.findViewById(R.id.one_line);
        option_two = viewFrame.findViewById(R.id.two_line);
        option_three = viewFrame.findViewById(R.id.option_three);
        option_four = viewFrame.findViewById(R.id.option_four);
        option_five = viewFrame.findViewById(R.id.option_five);
        liLViewFrame = viewFrame.findViewById(R.id.view_frame);
        liLBarOne = viewFrame.findViewById(R.id.liLBarOne);
        liLBarTwo = viewFrame.findViewById(R.id.liLBarTwo);
    }

    public static void setRadiusOfIcons(){
        Log.d("aaa",Properties.iconsRadius + " radus");

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(Properties.iconsRadius*2,Properties.iconsRadius*2);
        parms.leftMargin = Properties.iconsSpace;
        parms.rightMargin = Properties.iconsSpace;
        hole_one.setLayoutParams(parms);
        hole_two.setLayoutParams(parms);
        hole_three.setLayoutParams(parms);
        hole_four.setLayoutParams(parms);
        hole_five.setLayoutParams(parms);
        hole_six.setLayoutParams(parms);
        option_one.setLayoutParams(parms);
        option_two.setLayoutParams(parms);
        option_three.setLayoutParams(parms);
        option_four.setLayoutParams(parms);
        option_five.setLayoutParams(parms);
        liLBarOne.setLayoutParams(parms);
        liLBarTwo.setLayoutParams(parms);
        parms = new LinearLayout.LayoutParams(widthScreen,Properties.iconsRadius*2 + 10);
        liLViewFrame.setLayoutParams(parms);
    }

    private void onClickHole_One(){
        hole_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[0]){
                    hole_one.setImageResource(R.drawable.hole_one_off);
                    Properties.listHole[0] = false;
                }else {
                    hole_one.setImageResource(R.drawable.hole_one_on);
                    Properties.listHole[0] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickHole_Two(){
        hole_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[1]){
                    hole_two.setImageResource(R.drawable.hole_two_off);
                    Properties.listHole[1] = false;
                }else {
                    hole_two.setImageResource(R.drawable.hole_two_on);
                    Properties.listHole[1] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickHole_Three(){
        hole_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[2]){
                    hole_three.setImageResource(R.drawable.hole_three_off);
                    Properties.listHole[2] = false;
                }else {
                    hole_three.setImageResource(R.drawable.hole_three_on);
                    Properties.listHole[2] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickHole_Four(){
        hole_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[3]){
                    hole_four.setImageResource(R.drawable.hole_four_off);
                    Properties.listHole[3] = false;
                }else {
                    hole_four.setImageResource(R.drawable.hole_four_on);
                    Properties.listHole[3] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickHole_Five(){
        hole_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[4]){
                    hole_five.setImageResource(R.drawable.hole_five_off);
                    Properties.listHole[4] = false;
                }else {
                    hole_five.setImageResource(R.drawable.hole_five_on);
                    Properties.listHole[4] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickHole_Six(){
        hole_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.listHole[5]){
                    hole_six.setImageResource(R.drawable.hole_six_off);
                    Properties.listHole[5] = false;
                }else {
                    hole_six.setImageResource(R.drawable.hole_six_on);
                    Properties.listHole[5] = true;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickOption_One(){
        option_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.option != 1){
                    switch (Properties.option){
                        case 2: {
                            option_two.setImageResource(R.drawable.option_two_off);
                            break;
                        }
                        case 3: {
                            option_three.setImageResource(R.drawable.option_three_off);
                            DrawFeature.listPointTwoShootLine.clear();
                            break;
                        }
                        case 4: {
                            option_four.setImageResource(R.drawable.option_four_off);
                            break;
                        }
                        case 5: {
                            option_five.setImageResource(R.drawable.option_five_off);
                            break;
                        }
                    }
                    option_one.setImageResource(R.drawable.option_one_on);
                    Properties.option = 1;
                    toolFrame.invalidate();
                }

            }
        });
    }

    private void onClickOption_Two(){
        option_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Properties.reverseDirection = !Properties.reverseDirection;
                if(Properties.option != 2){

                    switch (Properties.option){
                        case 1: {
                            option_one.setImageResource(R.drawable.option_one_off);
                            break;
                        }
                        case 3: {
                            option_three.setImageResource(R.drawable.option_three_off);
                            DrawFeature.listPointTwoShootLine.clear();
                            break;
                        }
                        case 4: {
                            option_four.setImageResource(R.drawable.option_four_off);
                            break;
                        }
                        case 5: {
                            option_five.setImageResource(R.drawable.option_five_off);
                            break;
                        }
                    }
                    option_two.setImageResource(R.drawable.option_two_on);
                    Properties.option = 2;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickOption_Three(){
        option_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.option != 3){
                    switch (Properties.option){
                        case 1: {
                            option_one.setImageResource(R.drawable.option_one_off);
                            break;
                        }
                        case 2: {
                            option_two.setImageResource(R.drawable.option_two_off);
                            break;
                        }
                        case 4: {
                            option_four.setImageResource(R.drawable.option_four_off);
                            break;
                        }
                        case 5: {
                            option_five.setImageResource(R.drawable.option_five_off);
                            break;
                        }
                    }
                    option_three.setImageResource(R.drawable.option_three_on);
                    Properties.option = 3;
                }
                DrawFeature.direction++;
                if(DrawFeature.direction >= DrawFeature.listPointTwoShootLine.size()){
                    DrawFeature.direction = 0;
                }
                toolFrame.invalidate();
            }
        });
    }

    private void onClickOption_Four(){
        option_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.option != 4){
                    switch (Properties.option){
                        case 1: {
                            option_one.setImageResource(R.drawable.option_one_off);
                            break;
                        }
                        case 2: {
                            option_two.setImageResource(R.drawable.option_two_off);
                            break;
                        }
                        case 3: {
                            option_three.setImageResource(R.drawable.option_three_off);
                            DrawFeature.listPointTwoShootLine.clear();
                            break;
                        }
                        case 5: {
                            option_five.setImageResource(R.drawable.option_five_off);
                            break;
                        }
                    }
                    option_four.setImageResource(R.drawable.option_four_on);
                    Properties.option = 4;
                    toolFrame.invalidate();
                }
            }
        });
    }

    private void onClickOption_Five(){
        option_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Properties.option != 5){
                    switch (Properties.option){
                        case 1: {
                            option_one.setImageResource(R.drawable.option_one_off);
                            break;
                        }
                        case 2: {
                            option_two.setImageResource(R.drawable.option_two_off);
                            break;
                        }
                        case 3: {
                            option_three.setImageResource(R.drawable.option_three_off);
                            DrawFeature.listPointTwoShootLine.clear();
                            break;
                        }
                        case 4: {
                            option_four.setImageResource(R.drawable.option_four_off);
                            break;
                        }
                    }
                    option_five.setImageResource(R.drawable.option_five_on);
                    Properties.option = 5;
                    toolFrame.invalidate();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gameFrame.setVisibility(View.VISIBLE);
            windowManager.updateViewLayout(viewFrame, params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gameFrame.setVisibility(View.GONE);
            windowManager.updateViewLayout(viewFrame, params);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("aaa","Dừng");
        try{
            if (viewFrame != null) windowManager.removeView(viewFrame);
        }catch (Exception e){
            Log.d("aaa",e.getMessage());
        }
    }

}

