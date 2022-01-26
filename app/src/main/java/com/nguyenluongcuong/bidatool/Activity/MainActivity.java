package com.nguyenluongcuong.bidatool.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.nguyenluongcuong.bidatool.GameFrame;
import com.nguyenluongcuong.bidatool.ManagerAbleTouch;
import com.nguyenluongcuong.bidatool.R;

public class MainActivity extends AppCompatActivity {

    private Button btnStart,btnStop, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !Settings.canDrawOverlays(MainActivity.this)){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:" + getPackageName()));
                    mStartForResult.launch(intent);
                }else{
                    Log.d("aaa","Start");
                    showChatHead();
                }
            }
        });

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, GameFrame.class));
                stopService(new Intent(MainActivity.this, ManagerAbleTouch.class));
            }
        });

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("aaa","Ok " + result.getResultCode());
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Log.d("aaa","Ok");
                        showChatHead();
                    }
                }
            }
    );

    private void showChatHead(){
        startService(new Intent(this,GameFrame.class));
        startService(new Intent(this,ManagerAbleTouch.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}