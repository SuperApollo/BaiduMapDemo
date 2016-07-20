package com.example.baidumapdemo.baidumapdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.baidumapdemo.baidumapdemo.R;
import com.example.baidumapdemo.baidumapdemo.Utils.ToastUtils;
import com.example.baidumapdemo.baidumapdemo.common.AppConfig;

/**
 * Created by ${Apollo} on 2016/5/30 22:18.
 */
public class PlayActivity extends Activity implements View.OnClickListener {

    private WebView webView;
    private Button btn_back;
    private Button btn_forward;
    private Button btn_search;
    private EditText et_address;
    private ProgressBar pb_web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_play);
        initView();
        initWebView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        btn_back = (Button) findViewById(R.id.btn_web_back);
        btn_forward = (Button) findViewById(R.id.btn_web_forward);
        btn_search = (Button) findViewById(R.id.btn_web_search);
        et_address = (EditText) findViewById(R.id.et_web_address);
        pb_web = (ProgressBar) findViewById(R.id.pb_web);

        btn_back.setOnClickListener(this);
        btn_forward.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    private void initWebView() {
//        String url = "http://91.vido.ws/v.php?next=watch";
        String url = AppConfig.CL_ADDRESS;
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setPluginsEnabled(true);//可以使用插件
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_web.setVisibility(View.VISIBLE);
                ToastUtils.show(getApplicationContext(), "开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_web.setVisibility(View.GONE);
                ToastUtils.show(getApplicationContext(), "加载完成");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                pb_web.setVisibility(View.GONE);
                ToastUtils.show(getApplicationContext(), "加载错误");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_web_forward:
                ToastUtils.show(getApplicationContext(), "前进");
                webView.goForward();
                break;
            case R.id.btn_web_back:
                ToastUtils.show(getApplicationContext(), "后退");
                webView.goBack();
                break;
            case R.id.btn_web_search:
                ToastUtils.show(getApplicationContext(), "搜索");
                String s = et_address.getEditableText().toString().trim();
                if(TextUtils.isEmpty(s)){
                    s = AppConfig.CL_ADDRESS;
                }
                String addr = s.contains("http://") ? s : "http://" + s;
                webView.clearCache(true);
                webView.clearHistory();
                webView.loadUrl(addr);
                break;

        }
    }
}
