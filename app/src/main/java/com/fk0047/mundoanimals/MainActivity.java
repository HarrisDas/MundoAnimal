package com.fk0047.mundoanimals;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Bundle savedInstanceState;
    WebView myWeb;
//LinearLayout backLayout;
    SwipeRefreshLayout swipeLayout;
Button retry_button;
    private boolean mbErrorOccured = false;
    ProgressDialog mDialog;

    TextView error_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        myWeb = (WebView)findViewById(R.id.myWebView);
        myWeb.loadUrl("http://mundoanimal.club/");
        mDialog=new ProgressDialog(this);
        error_message=(TextView)findViewById(R.id.error_message);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.backlayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                myWeb.loadUrl("http://mundoanimal.club/");
                refresh();
            }
        });
        refresh();




    }

    private void refresh() {
        error_message.setVisibility(View.GONE);
        mDialog.setMessage("Loading ");
      //  mDialog.show();
        myWeb.loadUrl(myWeb.getUrl());
        WebSettings webSettings = myWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWeb.getSettings().setUseWideViewPort(true);
        myWeb.getSettings().setLoadWithOverviewMode(true);
        myWeb.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
              //  mDialog.dismiss();
                swipeLayout.setRefreshing(false);

            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mbErrorOccured = true;
                //showErrorLayout();
                Toast.makeText(MainActivity.this, "Error on loading web ", Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, errorCode, description, failingUrl);
                myWeb.clearView();
                error_message.setVisibility(View.VISIBLE);
               myWeb.loadUrl("about:blank");
              // backLayout.setVisibility(View.INVISIBLE);
                //retry_button.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (myWeb.canGoBack()) {
            myWeb.goBack();
            return;
        }
        else {
            finish();
        }

        super.onBackPressed();
    }


}
