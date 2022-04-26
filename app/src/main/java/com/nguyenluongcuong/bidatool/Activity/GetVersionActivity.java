package com.nguyenluongcuong.bidatool.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.nguyenluongcuong.bidatool.GameFrame;
import com.nguyenluongcuong.bidatool.ManagerAbleTouch;
import com.nguyenluongcuong.bidatool.R;

public class GetVersionActivity extends AppCompatActivity {

    private TextView numberOfAd;
    private Button btnDisplayAd;
    private SharedPreferences sharedPreferences;

    private RewardedAd mRewardedAd;
    private static boolean completedAd = false;
    private static boolean isLoadingAdRewardedAd = false;
    private static int unavailable = 0;
    private static int numberOfAdsViewed = 0;
    private LinearLayout liLGetLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_version);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        sharedPreferences = getPreferences(MODE_PRIVATE);

        liLGetLink = (LinearLayout) findViewById(R.id.get_link_container);
        numberOfAd = (TextView) findViewById(R.id.txtNumberOfAd);
        numberOfAd.setText("Số quảng cáo đã xem: " + sharedPreferences.getInt("numberOfAdsViewed",0));
        numberOfAdsViewed = sharedPreferences.getInt("numberOfAdsViewed",0);
        if(numberOfAdsViewed < 10){
            liLGetLink.setVisibility(View.GONE);
        }
        btnDisplayAd = (Button) findViewById(R.id.btnDisplayAd);
        btnDisplayAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isLoadingAdRewardedAd){
                    Toast.makeText(GetVersionActivity.this,"Đang load quảng cáo, đợi tý nhé!",Toast.LENGTH_SHORT).show();
                    return;
                }
                isLoadingAdRewardedAd = true;
                loadRewardedAd();
            }
        });
    }

    private void loadRewardedAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        AlertDialog.Builder builder = new AlertDialog.Builder(GetVersionActivity.this);
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
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    numberOfAdsViewed++;
                                    editor.putInt("numberOfAdsViewed",numberOfAdsViewed);
                                    numberOfAd.setText("Số quảng cáo đã xem: " + numberOfAdsViewed);
                                    editor.commit();
                                    completedAd = false;
                                    if(numberOfAdsViewed >= 10){
                                        liLGetLink.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    Toast.makeText(GetVersionActivity.this,"Chưa xem hết quảng cáo nên không được tính!",Toast.LENGTH_LONG).show();
                                }
                                mRewardedAd = null;
                            }
                        });
                        if (mRewardedAd != null) {
                            Activity activityContext = GetVersionActivity.this;
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(GetVersionActivity.this);
                            builder.setTitle("Thông báo lỗi");
                            builder.setMessage("Quảng cáo chưa có sẵn từ máy chủ.\n- Tý nữa nhấn tiếp xem sao, miễn là đủ 10 lần, bao nhiêu lâu không quan trọng.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(unavailable == 5){
                                        startService(new Intent(GetVersionActivity.this,GameFrame.class));
                                        startService(new Intent(GetVersionActivity.this,ManagerAbleTouch.class));
                                        completedAd = false;
                                    }
                                }
                            });
                            builder.show();
                        }
                    }
                });
    }
}