package com.hybrid.sample;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends Activity {

    private final static int closeMill = 2000;
    private InterstitialAd interstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, closeMill);


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-2847913535577808/5265566831");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        // start Ads
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });
    }
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
            //전면광고후 처리 코드 삽입
        }
    }



}