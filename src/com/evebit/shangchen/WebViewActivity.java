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
import android.widget.TableLayout;
import android.widget.TextView;
import android.util.Base64;
import android.util.Log;


public class WebViewActivity extends Activity implements android.view.View.OnClickListener {
	private WebView webView;//主体
	private Button backButton;//返回按钮
	private Button webBackButton;//布局页面下方向左箭头
	private Button webNextButton;//布局页面下方向右箭头
	private TableLayout tableLayout;
	private TextView tabBar;

	
	private String tabString = null;//导航条名
	private String showString = null;
	
	public static ArrayList<HashMap<String, Object>> data ;//获取数据，由上级页面传递
	public static int mark;//获取当前点击的是第几项，然后给web页面赋值
	
	
	@SuppressWarnings("deprecation")
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		tabString = getIntent().getExtras().getString("tabBar");
		showString = getIntent().getExtras().getString("show");//值为YES显示下面箭头，值为NO不显示

		webView = (WebView)findViewById(R.id.webView_WebView);
		backButton = (Button)findViewById(R.id.webView_Button_back);
		webBackButton = (Button)findViewById(R.id.webView_Button_bottom_Back);
		webNextButton = (Button)findViewById(R.id.webView_Button_bottom_Next);
		tableLayout = (TableLayout)findViewById(R.id.webView_bottom_linearLayout);
		if (showString.equals("no")) {
			tableLayout.setVisibility(View.GONE);
		}
		tabBar = (TextView)findViewById(R.id.webView_TextView_tabBar);
		tabBar.setText(tabString);//设定导航条名称
		

		//绑定监听事件
		backButton.setOnClickListener(this);
		webBackButton.setOnClickListener(this);
		webNextButton.setOnClickListener(this);

		//显示内容
		initWeb();
		

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
	private void initWeb() {
		
		//String in = "PHA+Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7546J6b6Z5rip5rOJ5qyi5LmQ6LC355qE5bu66K6+56qB56C05LqG5Lul5b6A55qE5byA5Y+R5oCd6Lev77yM5Yqq5Yqb5aGR6YCg56ys5LqU5Luj5rip5rOJ5paw5b2i6LGh77yMPC9wPjxwPiZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwO+WNs+WcqOiejeWQiOa4qeazieiHqueEtuOAgemXsumAguOAgeWKqOaEn+OAgeWBpeW6t+etieS6p+WTgeeJuei0qOeahOWQjOaXtu+8jOedgOaEj+eqgeWHuua4qeazieS6p+WTgTwvcD48cD4mbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDvnmoTmlofljJbmgKflkozotqPlkbPmgKfjgILmma/ljLrlsIbmnKznnYDigJznp4nmib/kuJPkuJrmnI3liqHjgIHmiZPpgKDmuKnms4nnsr7lk4HigJ3nmoTnsr7npZ7vvIw8L3A+PHA+Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A75Lul4oCc55Sf5oCB44CB5paH5YyW44CB5aW9546p44CB5YW755Sf4oCd5Li655CG5b+177yM5Z2a5oyB5Lul5Lq65Li65pys77yM5o+Q6auY5ZGY5bel55qE5pyN5Yqh57Sg6LSo77yMPC9wPjxwPiZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwO+WKquWKm+WwhueOiem+mea4qeazieasouS5kOiwt+aJk+mAoOaIkOS4uuKAnOWNjuS4reesrOS4gOOAgeS4reWbveS4gOa1geOAgeS4lueVjOefpeWQjeKAneeahOa4qeazieaXhea4uOW6puWBh+aZr+WMuuOAgjwvcD48cD4mbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDs8cCBhbGlnbj0iY2VudGVyIj48aW1nIHNyYz0iaHR0cDovLzU4LjI0OS40OC4xMC9teWZpbGVzL2NvbXBhbnkvMjAxMjA1MDYwOTI3NDhkMS5qcGciIHdpZHRoPSIzMTAiIC8+PC9wPjwvcD4=";
		
	//	byte b[] = android.util.Base64.decode(in, Base64.DEFAULT);
	//	in = new String(b);
		
		
		
		Normal normal = new Normal(this);
		String summary = normal.getFromAssets("template.html");
	//	summary = summary.replace("titleString", "我是标题");
		summary = summary.replace("introtextString", getResources().getString(R.string.kaifa));
	//	summary = summary.replace("modified_timeString", "2013年11月22日");
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
				initWeb();
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
				initWeb();
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
			webView.goBack();
			break;
		case R.id.webView_Button_bottom_Next:
			webView.goForward();
			break;
		default:
			break;
		}
	}
	

}
