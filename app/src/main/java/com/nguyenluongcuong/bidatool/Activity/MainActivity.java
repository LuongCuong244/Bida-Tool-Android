package com.nguyenluongcuong.bidatool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.nguyenluongcuong.bidatool.Data.Data;
import com.nguyenluongcuong.bidatool.GameFrame;
import com.nguyenluongcuong.bidatool.ManagerAbleTouch;
import com.nguyenluongcuong.bidatool.R;

public class MainActivity extends AppCompatActivity {

    private Button btnStart,btnStop, btnSetting, btnGetPCVersion;
    private SharedPreferences sharedPreferences;
    public static boolean readDataSuccessfully = false;
    private static boolean isReadData = false;
    private RewardedAd mRewardedAd;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static boolean completedAd = false;
    private static boolean isLoadingAdRewardedAd = false;
    private static boolean isLoadingInterstitialAd = false;
    private static int unavailable = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if(isReadData == false){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            readDataSuccessfully = Data.getData(sharedPreferences);
            isReadData = true;
        }

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
                    showService();
                }
            }
        });

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideService();
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

        btnGetPCVersion = findViewById(R.id.btnGetPCVersion);
        btnGetPCVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetVersionActivity.class);
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
                        showService();
                    }
                }
            }
    );

    private void showService(){

        if(isLoadingAdRewardedAd){
            Toast.makeText(this,"Đang load quảng cáo, đợi tý nhé!",Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quảng cáo sẽ xuất hiện");
        builder.setMessage("Bạn cần phải hoàn thành một quảng cáo để bật công cụ này.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("aaa","Ok");
                isLoadingAdRewardedAd = true;
                loadRewardedAd();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Vui lòng xem quảng cáo để bật công cụ",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void hideService(){
        stopService(new Intent(MainActivity.this, GameFrame.class));
        stopService(new Intent(MainActivity.this, ManagerAbleTouch.class));
        if(isLoadingInterstitialAd){
            return;
        }
        isLoadingInterstitialAd = true;
        loadInterstitialAd();
    }

    private void loadRewardedAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Thông báo lỗi");
                        builder.setMessage("Đã có lỗi khi load quảng cáo.\n * Hãy thử:\n- Kiểm tra lại kết nối internet.\n- Sang xin pass wifi nhà hàng xóm.\n- Đăng ký 3G/4G tốc độ cao.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                        isLoadingAdRewardedAd = false;
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("aaa", "Ad was loaded.");
                        isLoadingAdRewardedAd = false;
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("aaa", "Ad was shown.");
                                unavailable = 0;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d("aaa", "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("aaa", "Ad was dismissed.");
                                if(completedAd){
                                    startService(new Intent(MainActivity.this,GameFrame.class));
                                    startService(new Intent(MainActivity.this,ManagerAbleTouch.class));
                                    completedAd = false;
                                } else {
                                    Toast.makeText(MainActivity.this,"Chưa xem hết quảng cáo nên không được tính!",Toast.LENGTH_LONG).show();
                                }
                                mRewardedAd = null;
                            }
                        });
                        if (mRewardedAd != null) {
                            Activity activityContext = MainActivity.this;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    Log.d("aaa", "The user earned the reward.");
                                    completedAd = true;
                                }
                            });
                        } else {
                            Log.d("aaa", "The rewarded ad wasn't ready yet.");
                            unavailable++;
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Thông báo lỗi");
                            builder.setMessage("Quảng cáo chưa có sẵn từ máy chủ.\n- Cứ nhấn bắt đầu lại xem được không, nếu 5 lần liên tiếp hiện thông báo này thì bật tool cho :))\n( Còn " + ( 5 - unavailable) + " lần )");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(unavailable == 5){
                                        startService(new Intent(MainActivity.this,GameFrame.class));
                                        startService(new Intent(MainActivity.this,ManagerAbleTouch.class));
                                        completedAd = false;
                                    }
                                }
                            });
                            builder.show();
                        }
                    }
                });
    }

    private void loadInterstitialAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        isLoadingInterstitialAd = false;
                        mInterstitialAd = interstitialAd;
                        Log.d("aaa", "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(MainActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("aaa", loadAdError.getMessage());
                        isLoadingInterstitialAd = false;
                        mInterstitialAd = null;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}