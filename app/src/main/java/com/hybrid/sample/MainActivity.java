package com.hybrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mCurrentUrl;
  //  private final static String MAIN_URL = "http://chunxpd1.cafe24.com/think/bbs/board.php?bo_table=1001/";
    ViewPager pager;
    // BACK 2번 클릭 시 종료 핸들러. 플래그
    private Handler mHandler = new Handler();
    private boolean mFlag = false;
    private boolean bool_isBackbuttonTap;

    ViewPager vp;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //스플래시
        startActivity(new Intent(this, SplashActivity.class));

        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequests = new AdRequest.Builder().build();
        mAdView.loadAd(adRequests);

        vp = (ViewPager)findViewById(R.id.vp);
        ll = (LinearLayout)findViewById(R.id.ll);

        TextView tab_first = (TextView)findViewById(R.id.tab_first);
        TextView tab_second = (TextView)findViewById(R.id.tab_second);
        TextView tab_third = (TextView)findViewById(R.id.tab_third);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        tab_first.setOnClickListener(movePageListener);
        tab_first.setTag(0);
        tab_second.setOnClickListener(movePageListener);
        tab_second.setTag(1);
        tab_third.setOnClickListener(movePageListener);
        tab_third.setTag(2);

        tab_first.setSelected(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<3)
                {
                    if(position==i)
                    {
                        ll.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });



        /*pager = (ViewPager)findViewById(R.id.pager);
        Button btn_first = (Button)findViewById(R.id.btn_first);
        Button btn_second = (Button)findViewById(R.id.btn_second);
        Button btn_third = (Button)findViewById(R.id.btn_third);

        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();

                pager.setCurrentItem(tag);
            }
        };

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
*/
        //웹뷰 설정
       /* mWebView = (WebView) findViewById(R.id.web);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setInitialScale(100);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClientClass());
        mWebView.loadUrl(MAIN_URL);*/

        //추가한 라인
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();



    }
    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();

            int i = 0;
            while(i<3)
            {
                if(tag==i)
                {
                    ll.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    ll.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }


   /* private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(FragmentManager fm )
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // total page count
            return 3;
        }
    }*/
    private class WebViewClientClass extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String overrideUrl) {
            view.loadUrl(overrideUrl);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!mFlag) {
                    Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();    // 종료안내 toast 를 출력
                    mFlag = true;
                    mHandler.sendEmptyMessageDelayed(0, 2000);    // 2000ms 만큼 딜레이
                    return false;
                }else {
                    // 앱 종료 code
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(Process.myPid());
                }
            } else {
                // 뒤로 가기 실행
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }

/*
            if (mWebView.getOriginalUrl().equalsIgnoreCase(MAIN_URL)) {
                if (!mFlag) {
                    Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();    // 종료안내 toast 를 출력
                    mFlag = true;
                    mHandler.sendEmptyMessageDelayed(0, 2000);    // 2000ms 만큼 딜레이
                    return false;
                } else {
                    // 앱 종료 code
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(Process.myPid());
                }
            } else {
                // 뒤로 가기 실행
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }*/

        }
        return true;
    }





}
