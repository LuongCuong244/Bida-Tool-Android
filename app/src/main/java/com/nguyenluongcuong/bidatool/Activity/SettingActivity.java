package com.nguyenluongcuong.bidatool.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nguyenluongcuong.bidatool.Properties;
import com.nguyenluongcuong.bidatool.R;

public class SettingActivity extends AppCompatActivity {

    private EditText edt_eye_size, edt_icons_size,edt_icons_space, edt_circle_touch_radius, edt_circle_ball_radius,edt_border_ball, edt_move_scale_radius, edt_border_width, edt_line_width;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ImageView img = (ImageView) findViewById(R.id.img);
        int width = displayMetrics.widthPixels;
        int height = width*1080/1920;
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
        img.setLayoutParams(parms);

        initView();

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
}