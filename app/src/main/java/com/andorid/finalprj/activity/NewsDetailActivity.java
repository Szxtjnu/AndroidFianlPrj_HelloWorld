package com.andorid.finalprj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andorid.finalprj.R;

public class NewsDetailActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageButton ibBack;
    private ImageButton ibShare;
    private WebView webView;
    private ProgressBar pbLoading;
    private String url;
    private String title;

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibShare = (ImageButton) findViewById(R.id.ib_share);
        webView = (WebView) findViewById(R.id.webview);
        pbLoading = (ProgressBar) findViewById(R.id.pb_status);
        ibShare.setOnClickListener(this);
        ibBack.setOnClickListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        findViews();
        getData();
        pbLoading.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if (v == ibBack) {
            finish();
        } else if (v == ibShare) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(url);
        tvTitle.setText(title);
    }
}