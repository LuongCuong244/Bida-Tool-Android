package com.nguyenluongcuong.bidatool;

import android.graphics.Color;
import android.graphics.Paint;

public class Properties {

    public static int widthFrame = 650;
    public static int heightFrame = 350;

    public static boolean listHole[] = {true,false,false,false,false,false};
    public static int option = 1;
    public static boolean reverseDirection = true;

    public static Paint paintSelectedBall;
    public static Paint paintBall;
    public static Paint paintLine;
    public static Paint paintBorder;
    public static Paint paintCueBall;
    public static Paint paintTarget;

    public static int strokeTarget = 5;
    public static int strokeBorder = 5;
    public static int strokeLine = 2;

    public static int eyeRadius = 90;
    public static int iconsRadius = 40;
    public static int iconsSpace = 10;
    public static int sizeButton = 110;
    public static int ball_Radius = 30;
    public static int radius_pSelected = 80;

    public static void setPaint(){

        paintSelectedBall = new Paint();
        paintCueBall = new Paint();
        paintLine = new Paint();
        paintBorder = new Paint();
        paintBall = new Paint();
        paintTarget = new Paint();

        paintTarget.setStrokeWidth(strokeTarget);
        paintTarget.setStyle(Paint.Style.STROKE);
        paintTarget.setColor(Color.parseColor("#70ff0000"));

        paintCueBall.setColor(Color.parseColor("#50ffffff"));

        paintSelectedBall.setColor(Color.parseColor("#50000000"));

        paintBall.setColor(Color.parseColor("#30ffffff"));

        paintBorder.setColor(Color.YELLOW);
        paintBorder.setStrokeWidth(strokeBorder);

        paintLine.setColor(Color.GREEN);
        paintLine.setStrokeWidth(strokeLine);
    }
}
