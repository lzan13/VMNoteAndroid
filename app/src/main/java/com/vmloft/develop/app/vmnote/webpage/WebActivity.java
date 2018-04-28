package com.vmloft.develop.app.vmnote.webpage;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.vmloft.develop.app.vmnote.R;
import com.vmloft.develop.app.vmnote.app.base.AppActivity;
import com.vmloft.develop.app.vmnote.common.router.NavParams;
import com.vmloft.develop.app.vmnote.common.router.NavRouter;

import butterknife.BindView;

/**
 * Created by lzan13 on 2018/4/27.
 * 项目处理 web 页面界面
 */
public class WebActivity extends AppActivity {

    @BindView(R.id.web_view_container) LinearLayout webViewContainer;
    private Toolbar toolbar;

    private AgentWeb agentWeb;
    private String url;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_web_page;
    }

    @Override
    protected void init() {
        toolbar = getToolbar();
        toolbar.setTitle(R.string.agentweb_loading);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });

        NavParams params = NavRouter.getParcelableExtra(activity);
        url = params.str0;
        initWebView();
    }

    private void initWebView() {
        agentWeb = AgentWeb.with(activity)
                .setAgentWebParent(webViewContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(chromeClient)
                .setWebViewClient(viewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    /**
     * 刷新页面
     */
    private void refresh() {
        agentWeb.getUrlLoader().reload();
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
            toolbar.setTitle(title);
        }
    };


    @Override
    public void onBackPressed() {
        if (!agentWeb.back()) {
            onFinish();
        }
    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
