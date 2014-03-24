package com.evebit.shangchen;
/**
 * 企业信息
 */
import java.util.ArrayList;

import com.evebit.models.GuidePageAdapter;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EnterpriseActivity extends Activity{

	/**
	 * 绑定控件
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews; 
	private ViewGroup viewGroup;
	private View pageView1,pageView2,pageView3;
	
	private TextView textView1;//头部3个选项
	private TextView textView2;
	private TextView textView3;

	
	
	public static ArrayList<String> list = new ArrayList<String>(); 
	private WebView webView1,webView2,webView3;
	private TextView tabBar;
	private Button back;
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 设定滑动列表
		 */
				
		LayoutInflater inflater = getLayoutInflater();  
		pageViews = new ArrayList<View>();  
		                
		pageView1 = inflater.inflate(R.layout.enterprise_web, null);
		pageView2 = inflater.inflate(R.layout.enterprise_web2, null);
		pageView3 = inflater.inflate(R.layout.enterprise_web3, null);

		pageViews.add(pageView1);
		pageViews.add(pageView2);
		pageViews.add(pageView3);  
		        
		viewGroup = (ViewGroup)inflater.inflate(R.layout.enterprise, null);  
		viewPager = (ViewPager)viewGroup.findViewById(R.id.Enterprise_guidePages);  
		GuidePageAdapter adapter = new GuidePageAdapter(pageViews);//声明自定义adapter，放入内容
		viewPager.setAdapter(adapter);  
		viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
				
		setContentView(viewGroup);
		/**
		* 绑定控件设置值,以下为选项卡之外控件
		*/
		
		 textView1 = (TextView) findViewById(R.id.Enterprise_text1);  
		 textView2 = (TextView) findViewById(R.id.Enterprise_text2);  
		 textView3 = (TextView) findViewById(R.id.Enterprise_text3);  

		        
		 textView1.setOnClickListener(new MyOnClickListener(0));  
		 textView2.setOnClickListener(new MyOnClickListener(1));  
		 textView3.setOnClickListener(new MyOnClickListener(2));    
       
		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Enterprise_TextView_tabBar);
		back = (Button)findViewById(R.id.Enterprise_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tabBar.setText(tabString);//设定导航条名称
		
		
		webView1 = (WebView)pageView1.findViewById(R.id.Enterprise_Web_WebView1);
		webView2 = (WebView)pageView2.findViewById(R.id.Enterprise_Web_WebView2);
		webView3 = (WebView)pageView3.findViewById(R.id.Enterprise_Web_WebView3);
		
		
		
		webJava(webView1);
		webJava(webView2);
		webJava(webView3);
		
		initWeb(webView1,list.get(0));
		initWeb(webView2,list.get(1));
		initWeb(webView3,list.get(2));
		
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void webJava(WebView webView)
	{
		
		webView.getSettings().setPluginsEnabled(true);
		/**
		 * 本地html文件内设置支持Javascript
		 */
		WebSettings webSettings = webView.getSettings();       
        webSettings.setJavaScriptEnabled(true);  
        webSettings.setDisplayZoomControls(false);//隐藏放大缩小按钮
	}
	
	/**
	 * 加载本地Html
	 */
	
	private void initWeb(WebView webView,String string) {
		
		
		//byte b[] = android.util.Base64.decode(in, Base64.DEFAULT);
		//in = new String(b);
		
		Normal normal = new Normal(this);
		String summary = normal.getFromAssets("template.html");
		summary = summary.replace("introtextString", string);
		webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
		//mWebView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadDataWithBaseURL("file:///android_asset/",summary, "text/html", "UTF-8", "about:blank");
	
	}
	
	
	/**
	 * 头部3个选项点击事件
	 * @author yangchao
	 *
	 */
	private class MyOnClickListener implements OnClickListener{  
        private int index=0;  
        public MyOnClickListener(int i){  
            index=i;  
        }  
        public void onClick(View v) {  
        	imageX(index);
            viewPager.setCurrentItem(index);              
        }  
          
    }
	/**
	 * 头部3个图片更换
	 * @author yangchao
	 *
	 */
	private void imageX(int index)
	{
		switch (index) {
		case 0:
			textView1.setTextColor(Color.parseColor("#FFFFFF"));
			textView2.setTextColor(Color.parseColor("#005ca1"));
			textView3.setTextColor(Color.parseColor("#005ca1"));
			textView1.setBackgroundColor(Color.parseColor("#005ca1"));
			textView2.setBackgroundColor(Color.parseColor("#FFFFFF"));
			textView3.setBackgroundColor(Color.parseColor("#FFFFFF"));

			break;
		case 1:
			textView1.setTextColor(Color.parseColor("#005ca1"));
			textView2.setTextColor(Color.parseColor("#FFFFFF"));
			textView3.setTextColor(Color.parseColor("#005ca1"));
			textView1.setBackgroundColor(Color.parseColor("#FFFFFF"));
			textView2.setBackgroundColor(Color.parseColor("#005ca1"));
			textView3.setBackgroundColor(Color.parseColor("#FFFFFF"));
			break;
		case 2:
			textView1.setTextColor(Color.parseColor("#005ca1"));
			textView2.setTextColor(Color.parseColor("#005ca1"));
			textView3.setTextColor(Color.parseColor("#FFFFFF"));
			textView1.setBackgroundColor(Color.parseColor("#FFFFFF"));
			textView2.setBackgroundColor(Color.parseColor("#FFFFFF"));
			textView3.setBackgroundColor(Color.parseColor("#005ca1"));
			break;
		default:
			break;
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
	
	//滑动事件
	class GuidePageChangeListener implements OnPageChangeListener {  
  	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
        	//页面判断
        	imageX(arg0);
        	Log.v("我是滑动诶", String.valueOf(arg0));
        	switch (arg0) {
			case 0:
	
				break;
			case 1:

				break;
			case 2:

				break;
			default:
				break;
			}
        	
        }  
    }
	
	
}
