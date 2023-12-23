package com.practica1.androidengine;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import kotlin.jvm.internal.Lambda;


public class AdManager {
    private Context context;
    private AdView adView;
    private RewardedAd rewardedAd;

    public AdManager(Context context, AdView adView) {
        this.context = context;
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadBannerAd(adView);
                loadRewardedAd();
            }
        });
    }

    // Método para cargar y mostrar un banner
    public void loadBannerAd(AdView adView) {
        this.adView = adView;

        if (this.adView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            this.adView.loadAd(adRequest);
        }
    }


    // Método para cargar un anuncio recompensado
    public void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
            }
        });
    }

    // Método para mostrar un anuncio recompensado
    public void showRewardedAd(AdCallback adCallback) {
        if (rewardedAd != null) {
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rewardedAd.show((AppCompatActivity)context, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            adCallback.execute();
                            loadRewardedAd();
                        }
                    });
                }
            });
        }
    }

    public void destroy() {
        adView.destroy();
    }
}
