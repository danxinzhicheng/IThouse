package com.danmo.ithouse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.danmo.ithouse.R;

/**
 * 普通详情页，webview类型页面
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    public static final String INTENT_DETAIL_CONTENT_LINK = "link";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.onBackPressed();
            }
        });

        webView = (WebView) findViewById(R.id.wv_detail_content);
        WebSettings webSettings = webView.getSettings();//获取webview设置属性
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setBackgroundColor(0x00000000); // 设置背景色
        //设置Web视图
        webView.setWebViewClient(new webViewClient());
    }

    //设置不用系统浏览器打开,直接显示在当前Webview
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

    public static void start(Context context, String link) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_DETAIL_CONTENT_LINK, link);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String link = getIntent().getStringExtra(INTENT_DETAIL_CONTENT_LINK);
        if (!TextUtils.isEmpty(link)) {
            webView.loadUrl(link);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
