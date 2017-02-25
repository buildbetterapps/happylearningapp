package com.gols.happylearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.gols.happylearning.utils.AppUtil;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String content = getIntent().getStringExtra(AppUtil.KEY_POST_CONTENT);
        webView.loadDataWithBaseURL("", content, "text/html", "UTF-8", "");
    }
}
