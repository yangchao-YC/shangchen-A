package com.evebit.shangchen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.util.Base64;
import android.util.Log;


public class DoingWebViewActivity extends Activity implements android.view.View.OnClickListener {

	private WebView webView;//主体
	private Button backButton;//返回按钮
	private Button webBackButton;//布局页面下方向左箭头
	private Button webNextButton;//布局页面下方向右箭头
	private TableLayout tableLayout;
	private TextView tabBar;
	private LinearLayout linearLayout;
	private TextView titleTextView;
	private TextView timeTextView;

	
	public static ArrayList<Hashtable<String, String>> data  = new ArrayList<Hashtable<String,String>>();//获取数据，由上级页面传递
	public static int mark;//获取当前点击的是第几项，然后给web页面赋值
	
	
	@SuppressWarnings("deprecation")
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webView = (WebView)findViewById(R.id.webView_WebView);
		backButton = (Button)findViewById(R.id.webView_Button_back);
		webBackButton = (Button)findViewById(R.id.webView_Button_bottom_Back);
		webNextButton = (Button)findViewById(R.id.webView_Button_bottom_Next);
		tableLayout = (TableLayout)findViewById(R.id.webView_bottom_linearLayout);
		linearLayout = (LinearLayout)findViewById(R.id.webView_Layout_title);
		
		titleTextView = (TextView)findViewById(R.id.webView_Layout_title_text1);
		timeTextView = (TextView)findViewById(R.id.webView_Layout_title_text2);
		
		linearLayout.setVisibility(View.VISIBLE);
		tabBar = (TextView)findViewById(R.id.webView_TextView_tabBar);
		tabBar.setText(data.get(mark).get(HomeActivity.KEY_TITLE));//设定导航条名称
		titleTextView.setText(data.get(mark).get(HomeActivity.KEY_TITLE));
		timeTextView.setText(data.get(mark).get(HomeActivity.KEY_CREATED));
		

		//绑定监听事件
		backButton.setOnClickListener(this);
		webBackButton.setOnClickListener(this);
		webNextButton.setOnClickListener(this);

		//显示内容
		initWeb(data.get(mark).get(HomeActivity.KEY_INTROTEXT).toString());
		

		webView.getSettings().setPluginsEnabled(true);
		/**
		 * 本地html文件内设置支持Javascript
		 */
		WebSettings webSettings = webView.getSettings();       
        webSettings.setJavaScriptEnabled(true);       


	}


	/**
	 * 
	 * @param title	标题
	 * @param introtext	内容
	 * @param time	时间
	 * 根据数据调用本地html进行网页显示
	 * String title,String introtext,String time
	 */
	private void initWeb(String content) {
		
		String in = content;
		if (in.equals("")) {
			
		}
		else {
			byte b[] = android.util.Base64.decode(in, Base64.DEFAULT);
			in = new String(b);
		}
		
		
		Normal normal = new Normal(this);
		String summary = normal.getFromAssets("template.html");
		summary = summary.replace("titleString", "我是标题");
		summary = summary.replace("introtextString", in);
		summary = summary.replace("modified_timeString", "2013年11月22日");
		webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
		//mWebView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadDataWithBaseURL("file:///android_asset/",summary, "text/html", "UTF-8", "about:blank");
	
	}

	
	/**
	 * 向左图片调用方法，点击图片后进行判断此页面是否为第一页，不是则进行mark赋值，根据mark的值获取新页面需显示的数据，然后调用initWeb方法进行页面显示
	 */
	private void backWeb()
	{
		if (mark!=0) {
			if ((mark+1)>1) {
				mark--;
				Log.e("---1-", ""+mark);
				initWeb(data.get(mark).get(HomeActivity.KEY_INTROTEXT));
				tabBar.setText(data.get(mark).get(HomeActivity.KEY_TITLE));
				titleTextView.setText(data.get(mark).get(HomeActivity.KEY_TITLE));
				timeTextView.setText(data.get(mark).get(HomeActivity.KEY_CREATED));
			}
		}	
	}
	/**
	 * 向右图片调用方法，点击图片后进行判断此页面是否为最后，不是则进行mark赋值，根据mark的值获取新页面需显示的数据，然后调用initWeb方法进行页面显示
	 */
	private void nextWeb()
	{
		if (data.size() ==(mark +1)) {
			
		}
		else {
			if (data.size()>mark) {
				mark++;
				Log.e("---2-", ""+mark);
				initWeb(data.get(mark).get(HomeActivity.KEY_INTROTEXT));
				tabBar.setText(data.get(mark).get(HomeActivity.KEY_TITLE));
				titleTextView.setText(data.get(mark).get(HomeActivity.KEY_TITLE));
				timeTextView.setText(data.get(mark).get(HomeActivity.KEY_CREATED));
			}
		}
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.webView_Button_back:
			onBackPressed();//返回上一级
			break;
		case R.id.webView_Button_bottom_Back:
			backWeb();
			break;
		case R.id.webView_Button_bottom_Next:
			nextWeb();
			break;
		default:
			break;
		}
	}
	

}
