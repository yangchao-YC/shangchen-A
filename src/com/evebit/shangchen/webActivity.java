package com.evebit.shangchen;

/**
 * 网页浏览页面
 */
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;

import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class webActivity extends Activity implements android.view.View.OnClickListener{

	private float OldX1,OldY1,OldX2,OldY2,NewX1,NewY1,NewX2,NewY2;
	
	
	private TextView tabBar;

	private Button backButton;

	private Button nextButton;

	private WebView webView;

	private Button back;

	private String tabString = null;

	private String urlString = null;

	@SuppressLint("SetJavaScriptEnabled")

	@SuppressWarnings("deprecation")

	@Override

	protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);

	setContentView(R.layout.web);
	PushAgent.getInstance(this).onAppStart();	
	/**

	* 接收数据

	*/

	urlString = getIntent().getExtras().getString("url");

	tabString = getIntent().getExtras().getString("tabBar");

	/**

	* 绑定控件

	*/

	tabBar = (TextView)findViewById(R.id.web_TextView_tabBar);

	backButton = (Button)findViewById(R.id.web_Button_bottom_Back);

	nextButton = (Button)findViewById(R.id.web_Button_bottom_Next);

	webView = (WebView)findViewById(R.id.web_WebView);

	back = (Button)findViewById(R.id.web_Button_back);



	webView.getSettings().setJavaScriptEnabled(true);

	webView.getSettings().setPluginsEnabled(true);
	
	webView.getSettings().setUseWideViewPort(true);
	webView.getSettings().setBuiltInZoomControls(true);
	webView.getSettings().setSupportZoom(true);
	webView.getSettings().setDisplayZoomControls(false);

	backButton.setOnClickListener(this);

	nextButton.setOnClickListener(this);

	back.setOnClickListener(this);

	tabBar.setText(tabString);


	/**

	* 设置内容只在本地webView内显示，不跳转至系统浏览器

	*/

	webView.setWebViewClient(new WebViewClient(){


		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
				view.loadUrl(url);
				Log.v("--tianmao--", url);
		
				return true;
		}
	
		


	});

	Normal normal = new Normal(this);

	if (normal.note_Intent()) {

	webView.loadUrl(urlString);
	//Log.v("--", webView.getUrl());

	}

	else {

	Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();

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
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}



	
/*//实现手势缩放 ，层加便利性
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_POINTER_DOWN:
				if(event.getPointerCount() == 2){
					for(int i = 0;i<event.getPointerCount();i++){
						if(i==0){
							OldX1 = event.getX();
							OldY1 = event.getY();
						}
					}
				}
		}
		return super.onTouchEvent(event);
	}
*/




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

	case R.id.web_Button_bottom_Back:

	webView.goBack();

	break;

	case R.id.web_Button_bottom_Next:

	webView.goForward();

	break;

	case R.id.web_Button_back:

	onBackPressed();

	break;

	default:

	break;

	}

	}
	
	
}