package com.nguyenluongcuong.bidatool.Data;

import android.content.SharedPreferences;
import android.util.Log;

import com.nguyenluongcuong.bidatool.ListPoint;
import com.nguyenluongcuong.bidatool.Properties;

public class Data {

    public static void saveData(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("widthFrame", Properties.widthFrame);
        editor.putInt("heightFrame", Properties.heightFrame);
        editor.putInt("sizeButton", Properties.sizeButton);
        editor.putInt("ball_Radius", Properties.ball_Radius);
        editor.putInt("iconsRadius", Properties.iconsRadius);
        editor.putInt("iconsSpace", Properties.iconsSpace);
        editor.putInt("radius_pSelected", Properties.radius_pSelected);
        editor.putInt("strokeTarget", Properties.strokeTarget);
        editor.putInt("strokeLine", Properties.strokeLine);
        editor.putInt("strokeBorder", Properties.strokeBorder);
        editor.putInt("eyeRadius", Properties.eyeRadius);

        editor.putInt("pBorderStart_X", ListPoint.pBorderStart.x);
        editor.putInt("pBorderStart_Y", ListPoint.pBorderStart.y);
        editor.putInt("pCueBall_X", ListPoint.pCueBall.x);
        editor.putInt("pCueBall_Y", ListPoint.pCueBall.y);
        editor.putInt("pSelected_X", ListPoint.pSelected.x);
        editor.putInt("pSelected_Y", ListPoint.pSelected.y);
        editor.putInt("pBorderEnd_X", ListPoint.pBorderEnd.x);
        editor.putInt("pBorderEnd_Y", ListPoint.pBorderEnd.y);
        editor.putInt("pScaleButton_X", ListPoint.pScaleButton.x);
        editor.putInt("pScaleButton_Y", ListPoint.pScaleButton.y);
        editor.putInt("pMoveButton_X", ListPoint.pMoveButton.x);
        editor.putInt("pMoveButton_Y", ListPoint.pMoveButton.y);

        editor.commit();
    }

    public static boolean getData(SharedPreferences sharedPreferences){

        Properties.widthFrame = sharedPreferences.getInt("widthFrame",0);
        if(Properties.widthFrame == 0){ // chưa được lưu bao giờ
            return false;
        }

        Properties.heightFrame = sharedPreferences.getInt("heightFrame",350);
        Properties.sizeButton = sharedPreferences.getInt("sizeButton",80);
        Properties.eyeRadius = sharedPreferences.getInt("eyeRadius",200);
        Properties.ball_Radius = sharedPreferences.getInt("ball_Radius",30);
        Properties.iconsRadius = sharedPreferences.getInt("iconsRadius",13);
        Properties.iconsSpace = sharedPreferences.getInt("iconsSpace",10);
        Properties.radius_pSelected = sharedPreferences.getInt("radius_pSelected",80);
        Properties.strokeTarget = sharedPreferences.getInt("strokeTarget",5);
        Properties.strokeLine = sharedPreferences.getInt("strokeLine",2);
        Properties.strokeBorder = sharedPreferences.getInt("strokeBorder",5);

        ListPoint.pBorderStart.set(
                sharedPreferences.getInt("pBorderStart_X",0),
                sharedPreferences.getInt("pBorderStart_X",0)
        );
        ListPoint.pCueBall.set(
                sharedPreferences.getInt("pCueBall_X",0),
                sharedPreferences.getInt("pCueBall_Y",0)
        );
        ListPoint.pSelected.set(
                sharedPreferences.getInt("pSelected_X",0),
                sharedPreferences.getInt("pSelected_Y",0)
        );
        ListPoint.pBorderEnd.set(
                sharedPreferences.getInt("pBorderEnd_X",0),
                sharedPreferences.getInt("pBorderEnd_Y",0)
        );
        ListPoint.pScaleButton.set(
                sharedPreferences.getInt("pScaleButton_X",0),
                sharedPreferences.getInt("pScaleButton_Y",0)
        );
        ListPoint.pMoveButton.set(
                sharedPreferences.getInt("pMoveButton_X",0),
                sharedPreferences.getInt("pMoveButton_Y",0)
        );
        return true;
    }
}
