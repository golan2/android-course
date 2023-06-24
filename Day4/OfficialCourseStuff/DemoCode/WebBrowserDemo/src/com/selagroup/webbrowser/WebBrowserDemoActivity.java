package com.selagroup.webbrowser;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowserDemoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WebView wb = (WebView)findViewById(R.id.wb);
        
        //Loading browser with URL
        wb.loadUrl("http://www.google.com");
        
        //Loading browser with HTML string        
        //String html = getString(R.string.html);
        //wb.loadData(html, "text/html", "utf-8");
        
        //Loading browser with a dynamically generated page and overriding navigation
        //wb.setWebViewClient(new BrowserCallbackHandler());
        //getCurrentDateTime(wb);
    }
    
    void getCurrentDateTime(WebView view){
    	String html = "<html><body><a href=\"getTime\">" + new Date().toLocaleString() + "</a></body></html>";
    	view.loadDataWithBaseURL("x-data://base", html, "text/html", "utf-8", null);
    }
    
    @SuppressWarnings("unused")
	private class BrowserCallbackHandler extends WebViewClient{
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url){
    		getCurrentDateTime(view);
    		return true;
    	}
    }
}