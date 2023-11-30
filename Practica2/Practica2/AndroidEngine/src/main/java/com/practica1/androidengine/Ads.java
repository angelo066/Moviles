package com.practica1.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;


public class Ads {
    private AppCompatActivity context;
    private AdRequest adRequest;
    private AdView adView;
    public Ads(AppCompatActivity context, AdView adView){
        this.context = context;

        MobileAds.initialize(context, null);

        adRequest = new AdRequest.Builder().build();

        this.adView = adView;
    }

    public void loadBanner(){
        adView.loadAd(adRequest);
    }

    public void loadRewarded(){
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",adRequest,null);
    }

    public void destroy(){
        adView.destroy();
    }
}
