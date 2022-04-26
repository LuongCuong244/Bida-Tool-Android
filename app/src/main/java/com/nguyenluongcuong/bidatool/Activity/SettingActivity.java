package com.nguyenluongcuong.bidatool.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.nguyenluongcuong.bidatool.DrawFeature;
import com.nguyenluongcuong.bidatool.DrawFrame;
import com.nguyenluongcuong.bidatool.GameFrame;
import com.nguyenluongcuong.bidatool.ManagerAbleTouch;
import com.nguyenluongcuong.bidatool.Properties;
import com.nguyenluongcuong.bidatool.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends AppCompatActivity {

    private EditText edt_eye_size, edt_icons_size,edt_icons_space, edt_circle_touch_radius, edt_circle_ball_radius,edt_border_ball, edt_move_scale_radius, edt_border_width, edt_line_width;
    private Button btnUpdate;
    private  static  final String regEx = "^[0-9]{1,3}+$";
    private static final Pattern pattern = Pattern.compile(regEx,Pattern.UNICODE_CASE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ImageView img = (ImageView) findViewById(R.id.img);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        int width = displayMetrics.widthPixels;
        int height = width*1080/1920;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        img.setLayoutParams(parms);

        initView();
        onChangeText_Eye_Size();
        onChangeText_Icon_Size();
        onChangeText_Icon_Space();
        onChangeText_Circle_Touch_Radius();
        onChangeText_Circle_Ball_Radius();
        onChangeText_Border_Ball();
        onChangeText_Move_Scale_Radius();
        onChangeText_Border_Width();
        onChangeText_Line_Width();
        onClickUpdate();
    }

    private void initView(){
        edt_eye_size = findViewById(R.id.edt_eye_size);
        edt_icons_size = findViewById(R.id.edt_icons_size);
        edt_icons_space = findViewById(R.id.edt_icons_space);
        edt_circle_touch_radius = findViewById(R.id.edt_circle_touch_radius);
        edt_circle_ball_radius = findViewById(R.id.edt_circle_ball_radius);
        edt_border_ball = findViewById(R.id.edt_border_ball);
        edt_move_scale_radius = findViewById(R.id.edt_move_scale_radius);
        edt_border_width = findViewById(R.id.edt_border_width);
        edt_line_width = findViewById(R.id.edt_line_width);
        btnUpdate = findViewById(R.id.btn_Update);

        edt_eye_size.setText(String.valueOf(Properties.eyeRadius));
        edt_icons_size.setText(String.valueOf(Properties.iconsRadius));
        edt_icons_space.setText(String.valueOf(Properties.iconsSpace));
        edt_circle_touch_radius.setText(String.valueOf(Properties.radius_pSelected));
        edt_circle_ball_radius.setText(String.valueOf(Properties.ball_Radius));
        edt_border_ball.setText(String.valueOf(Properties.strokeTarget));
        edt_move_scale_radius.setText(String.valueOf(Properties.sizeButton));
        edt_border_width.setText(String.valueOf(Properties.strokeBorder));
        edt_line_width.setText(String.valueOf(Properties.strokeLine));
    }

    private boolean checkValue(String value, int min, int max){
        Matcher matcher = pattern.matcher(value);
        if(matcher.matches()){
            if(Integer.parseInt(value) >= min && Integer.parseInt(value) <= max){
                return true;
            }else{
                return false;
            }
        }
        else {
            return false;
        }
    }

    private void onChangeText_Eye_Size(){
        edt_eye_size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,10,500)){
                    edt_eye_size.setTextColor(Color.BLACK);
                }else {
                    edt_eye_size.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Icon_Size(){
        edt_icons_size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,5,500)){
                    edt_icons_size.setTextColor(Color.BLACK);
                }else {
                    edt_icons_size.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Icon_Space(){
        edt_icons_space.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,5,150)){
                    edt_icons_space.setTextColor(Color.BLACK);
                }else {
                    edt_icons_space.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Circle_Touch_Radius(){
        edt_circle_touch_radius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,20,500)){
                    edt_circle_touch_radius.setTextColor(Color.BLACK);
                }else {
                    edt_circle_touch_radius.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Circle_Ball_Radius(){
        edt_circle_ball_radius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,3,300)){
                    edt_circle_ball_radius.setTextColor(Color.BLACK);
                }else {
                    edt_circle_ball_radius.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Border_Ball(){
        edt_border_ball.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,0,50)){
                    edt_border_ball.setTextColor(Color.BLACK);
                }else {
                    edt_border_ball.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Move_Scale_Radius(){
        edt_move_scale_radius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,10,400)){
                    edt_move_scale_radius.setTextColor(Color.BLACK);
                }else {
                    edt_move_scale_radius.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Border_Width(){
        edt_border_width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,0,50)){
                    edt_border_width.setTextColor(Color.BLACK);
                }else {
                    edt_border_width.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onChangeText_Line_Width(){
        edt_line_width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if(checkValue(text,0,50)){
                    edt_line_width.setTextColor(Color.BLACK);
                }else {
                    edt_line_width.setTextColor(Color.RED);
                }
            }
        });
    }

    private void onClickUpdate() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean updateSuccessfully = true;

                if(checkValue(edt_eye_size.getText().toString().trim(),10,500)){
                    int value_edt_eye_size = Integer.valueOf(edt_eye_size.getText().toString().trim());
                    if(Properties.eyeRadius != value_edt_eye_size){
                        Properties.eyeRadius = value_edt_eye_size;
                        ManagerAbleTouch.setSizeImage();
                    }
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_icons_space.getText().toString().trim(),5,150) && checkValue(edt_eye_size.getText().toString().trim(),5,500)){
                    int value_edt_icons_size = Integer.valueOf(edt_icons_size.getText().toString().trim());
                    int value_edt_icons_space = Integer.valueOf(edt_icons_space.getText().toString().trim());
                    if(Properties.iconsRadius != value_edt_icons_size || Properties.iconsSpace != value_edt_icons_space){
                        Properties.iconsRadius = value_edt_icons_size;
                        Properties.iconsSpace = value_edt_icons_space;
                        GameFrame.setRadiusOfIcons();
                    }
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_circle_touch_radius.getText().toString().trim(),20,500)){
                    int value_edt_circle_touch_radius = Integer.valueOf(edt_circle_touch_radius.getText().toString().trim());
                    Properties.radius_pSelected = value_edt_circle_touch_radius;
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_circle_ball_radius.getText().toString().trim(),3,300)){
                    int value_edt_circle_ball_radius = Integer.valueOf(edt_circle_ball_radius.getText().toString().trim());
                    Properties.ball_Radius = value_edt_circle_ball_radius;
                    DrawFeature.saiSoCanBang = Properties.ball_Radius;
                    DrawFeature.customSaiSo = Properties.ball_Radius*7;
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_border_ball.getText().toString().trim(),0,50)){
                    int value_edt_border_ball = Integer.valueOf(edt_border_ball.getText().toString().trim());
                    Properties.strokeTarget = value_edt_border_ball;
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_move_scale_radius.getText().toString().trim(),10,400)){
                    int value_edt_move_scale_radius = Integer.valueOf(edt_move_scale_radius.getText().toString().trim());
                    Properties.sizeButton = value_edt_move_scale_radius;
                    DrawFrame.resetBitmap();
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_border_width.getText().toString().trim(),0,50)){
                    int value_edt_border_width = Integer.valueOf(edt_border_width.getText().toString().trim());
                    Properties.strokeBorder = value_edt_border_width;
                }else {
                    updateSuccessfully = false;
                }

                if(checkValue(edt_line_width.getText().toString().trim(),0,50)){
                    int value_edt_line_width = Integer.valueOf(edt_line_width.getText().toString().trim());
                    Properties.strokeLine = value_edt_line_width;
                }else {
                    updateSuccessfully = false;
                }

                if(updateSuccessfully){
                    Toast.makeText(SettingActivity.this, "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SettingActivity.this, "Cập nhật không thực sự thành công. Một số thay đổi vẫn được giữ nguyên!",Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}