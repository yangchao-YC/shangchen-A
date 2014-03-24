package com.evebit.shangchen;
/**
 * 产品中心详细页面
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.evebit.models.GuidePageAdapter;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends Activity{
	
	public static ArrayList<Hashtable<String, String>> date = new ArrayList<Hashtable<String,String>>();
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

	private  WebView webView,webView2,webView3;
	
	private ListView listView;
	
	private TextView tabBar;
	private Button back;
	/**
	 * 数据变量
	 */
	
	private String tabString = null;//导航条名
	private String arg2 = null;//点击项标识
	//产品参数列表
	private String [] parameterString ={
			"拍照像素","尺寸体积","总量","电池容量","手机外型","网络选择",
			"网络制式","支持频率/网络频率","屏幕尺寸","屏幕分辨率","屏幕材质",
			"触摸屏","手写","主数码相机","副数码相机","自动对焦","闪光灯",
			"扩展存储","操作系统","音乐播放器","彩信","蓝牙","文档浏览器",
			"连接方式","机身内存","大容量存储器"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		/**
		 * 设定滑动列表
		 */
				LayoutInflater inflater = getLayoutInflater();  
		        pageViews = new ArrayList<View>();  
		                
		        pageView1 = inflater.inflate(R.layout.product_detail_complex1, null);
		        pageView2 = inflater.inflate(R.layout.product_detail_parameter2, null);
		        pageView3 = inflater.inflate(R.layout.product_detail_aftermarket3, null);

		        pageViews.add(pageView1);
		        pageViews.add(pageView2);
		        pageViews.add(pageView3);  
		        
		        viewGroup = (ViewGroup)inflater.inflate(R.layout.product_detail, null);  
		        viewPager = (ViewPager)viewGroup.findViewById(R.id.ProductDetail_guidePages);  
		        GuidePageAdapter adapter = new GuidePageAdapter(pageViews);//声明自定义adapter，放入内容
		        viewPager.setAdapter(adapter);  
		        viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
				
		        setContentView(viewGroup);
		

		        /**
		         * 绑定控件设置值,以下为选项卡之外控件
		         */
		        textView1 = (TextView) findViewById(R.id.ProductDetail_text1);  
		        textView2 = (TextView) findViewById(R.id.ProductDetail_text2);  
		        textView3 = (TextView) findViewById(R.id.ProductDetail_text3); 
		        
		        textView1.setBackgroundColor(Color.parseColor("#005ca1"));
		        
		        textView1.setOnClickListener(new MyOnClickListener(0));  
		        textView2.setOnClickListener(new MyOnClickListener(1));  
		        textView3.setOnClickListener(new MyOnClickListener(2));        
		        
		        /**
				* 绑定控件设置值,以下为选项卡之外控件
				*/
		        
		tabString = getIntent().getExtras().getString("tabBar");
		arg2 = getIntent().getExtras().getString("arg2");
		tabBar = (TextView)findViewById(R.id.ProductDetail_TextView_tabBar);
		
		Log.v("wwwwwwwww", arg2);
		
		back = (Button)findViewById(R.id.ProductDetail_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tabBar.setText(tabString);//设定导航条名称
		

		/**
		 * 选项卡一
		 */
		
		String introtext;
		byte b[] = android.util.Base64.decode(date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_FULLTEXT), Base64.DEFAULT);
		introtext = new String(b);
		
		
		Log.v("----综合介绍----", introtext);
		webView = (WebView)pageView1.findViewById(R.id.Product_Complex_WebView1);
		webJava(webView);
		initWeb(webView,introtext);
		
		
		/**
		 * 选项卡二
		 */
		
		Log.v("----参数KEY_CHANSHU1----", new String(date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU1)));
		
		//String introtext1 = new String(date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU1));
		
		//byte b1[] = android.util.Base64.decode(date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU32), Base64.DEFAULT);
		//introtext1 = new String(b1);
		
		
		Log.v("----参数KEY_CHANSHU1----", date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU1));
		webView3 =(WebView)pageView2.findViewById(R.id.Product_Parameter_WebView1);
		webJava(webView3);
		initWeb(webView3,date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU1));

		/**String [] contentStrings = {
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU1),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU2),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU3),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU4),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU5),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU6),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU7),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU8),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU9),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU10),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU11),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU12),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU13),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU14),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU15),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU16),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU17),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU18),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU19),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU20),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU21),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU22),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU23),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU24),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU25),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU26),
				date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_CANSHU32),
				};
		
		
		
		
		listView = (ListView)pageView2.findViewById(R.id.Product_Parameter_ListView);
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < parameterString.length; i++) {
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("title",parameterString[i]);
			itemMap.put("content",contentStrings[i]);
			arrayList.add(itemMap);
		}
		 SimpleAdapter mAdapter = new SimpleAdapter(ProductDetailActivity.this,
				arrayList, R.layout.product_parameter_listview_cell, new String[] {
						"title", "content"
						 },
				new int[] { R.id.Product_Cell_title,
						R.id.Product_Cell_Content,
						 });
		listView.setAdapter(mAdapter);*/
		
		/**
		 * 选项卡三
		 */
		String introtext2;
		byte b2[] = android.util.Base64.decode(date.get(Integer.parseInt(arg2)).get(HomeActivity.KEY_INTROTEXT), Base64.DEFAULT);
		introtext2 = new String(b2);
		
		Log.v("----售后----", introtext2);
		webView2 = (WebView)pageView3.findViewById(R.id.Product_Aftermarket_WebView);
		webJava(webView2);
		initWeb(webView2,introtext2);
		

	}

	/**
	 * 本地html文件内设置支持Javascript
	 */
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void webJava(WebView webView)
	{
		webView.getSettings().setPluginsEnabled(true);
		WebSettings webSettings = webView.getSettings();       
        webSettings.setJavaScriptEnabled(true);  
	}
	
	/**
	 * 加载本地Html
	 */
	
	private void initWeb(WebView webView,String string) {
		
		
		//byte b[] = android.util.Base64.decode(in, Base64.DEFAULT);
		//in = new String(b);
		
		Normal normal = new Normal(this);
		String summary = normal.getFromAssets("template.html");
		summary = summary.replace("titleString", "我是标题");
		summary = summary.replace("introtextString", string);
		summary = summary.replace("modified_timeString", "2013年11月22日");
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
        }  
    }
	
	

	
}
