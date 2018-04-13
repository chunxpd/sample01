package com.hybrid.sample;

/**
 * Created by 맞춤팀 on 2018-03-22.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class Fragment2 extends Fragment {
    private WebView mWebView;
    private String mCurrentUrl;
    private final static String MAIN_URL = "http://chunxpd1.cafe24.com/think/bbs/board.php?bo_table=2001&pim=1/";
    WebView webView;
    public Fragment2()
    {
        // required


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_fragment1,container, false);
        View v = inflater.inflate( R.layout.fragment_fragment1, container, false );
        mWebView = (WebView)v.findViewById(R.id.web1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setInitialScale(100);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClientClass());
        mWebView.loadUrl(MAIN_URL);
        mWebView.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        return v;
    }
    private class WebViewClientClass extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String overrideUrl) {
            view.loadUrl(overrideUrl);
            return true;
        }
    }
}



