package com.vmloft.develop.app.vmnote.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vmloft.develop.app.vmnote.common.router.ARouter;
import com.vmloft.develop.library.tools.router.VMParams;
import com.vmloft.develop.library.tools.utils.VMStr;

/**
 * Markdown View
 * The type Markdown preview view.
 */
public class MDPreviewView extends NestedScrollView {
    private Context context;
    private WebView webView;

    public MDPreviewView(Context context) {
        this(context, null);
    }

    public MDPreviewView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MDPreviewView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    private void init() {
        webView = new WebView(context);
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(viewClient);
        webView.setWebChromeClient(chromeClient);

        addView(webView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        webView.loadUrl("file:///android_asset/markdown/index.html");
    }

    /**
     * 通过调动 javascript 方法利用 marked 库将 Markdown 内容转为 html 预览，
     * 因为参数传递到 javascript 层转义字符会直接转为实际效果，所以要进行下替换
     * 还有一点比较坑的地方就是，这里只能调用 loadUrl 去调用 javascript 方法
     *
     * @param str markdown 内容
     */
    public void parseMarkdown(String str) {
        str = str.replace("\n", "\\n");
        str = str.replace("\"", "\\\"");
        str = str.replace("'", "\\'");
        String callJs = "javascript:mdToHtml(\"" + str + "\")";
        webView.loadUrl(callJs);
    }

    /**
     * WebView 内容相关回调
     */
    private WebViewClient viewClient = new WebViewClient() {
        public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (!VMStr.isEmpty(url)) {
                VMParams params = new VMParams();
                params.str0 = url;
                ARouter.goWebPage(context, params);
            }
            return true;
        }
    };


    /**
     * WebView 浏览器事件相关回调
     */
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };

    /**
     * 截屏
     */
    public Bitmap getScreen() {
        Bitmap bmp = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        webView.draw(canvas);
        return bmp;
    }

}
