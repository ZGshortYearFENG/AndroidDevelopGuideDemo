package fengzj.sample.androiddevelopguidedemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        startActivity(view.getContext(), url);
        return true;
    }

    private static void startActivity(Context context, String url) {
        try {
            // 一般的浏览器webview的shouldOverrideUrlLoading都会对intent添加android.intent.action.VIEW，android.intent.category.DEFAULT，android.intent.category.BROWSABLE
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
