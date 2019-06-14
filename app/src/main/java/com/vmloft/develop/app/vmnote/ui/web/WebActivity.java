package com.vmloft.develop.app.vmnote.ui.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.base.AppActivity;
import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.library.tools.router.VMParams;

import butterknife.BindView;

/**
 * Created by lzan13 on 2018/4/27.
 *
 * 项目处理 web 页面界面
 */
public class WebActivity extends AppActivity {

    @BindView(R.id.web_view_container)
    RelativeLayout mWebContainer;

    private AgentWeb mAgentWeb;
    private String mUrl;

    @Override
    protected int layoutId() {
        return R.layout.activity_web_page;
    }

    @Override
    protected void initUI() {
        super.initUI();
        setTopTitle(R.string.agentweb_loading);

    }

    @Override
    protected void initData() {
        VMParams params = ARouter.getParams(mActivity);
        mUrl = params.str0;
        initWebView();
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(mWebContainer, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(chromeClient)
                .setWebViewClient(viewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    /**
     * 刷新页面
     */
    private void refresh() {
        mAgentWeb.getUrlLoader().reload();
    }

    private WebViewClient viewClient = new WebViewClient() {

    };

    /**
     * WebView 浏览器相关回调
     */
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTopTitle(title);
        }
    };

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            onFinish();
        }
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
