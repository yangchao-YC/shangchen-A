package com.evebit.shangchen;
/**
 * 菜单页面
 */


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;
import com.evebit.models.GuidePageAdapter_Image;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class HomeActivity extends Activity implements android.view.View.OnClickListener {


	/**
	 * 数据地址	1:首页3张图片的地址
				2：产品中心1的地址
				3：产品中心1详细参数的地址
				4：产品中心2的地址
				5：产品中心2详细参数的地址
				6：促销活动的地址
				7：售后服务的地址
				8：联系我们的地址
				9：天猫商城地址
	 */
	/**
	 * Json参数
	 * imgurl   	图片地址
	 * introtext 	内容简介		（需解码）
	 * fulltext  	网页详情显示（需解码）
	 * created  	时间
	 * created_by_alias 作者
	 * monery		价格
	 * title		标题
	 * aftersales   售后
	 */
	private String imgUrlString = HomeActivity.KEY_URL+"index.php?option=com_content&statez=13"+HomeActivity.KEY_ID;//
	ArrayList<Hashtable<String, String>> imgUrldata = new ArrayList<Hashtable<String, String>>();//存储获取的图片地址
	
	public static final String KEY_ID = "&uid=14"; 
	public static final String KEY_TIANQI = "http://m.weather.com.cn/data/101070701.html"; //天气地址
	public static final String KEY_TIANQI_FLASH = "http://flash.weather.com.cn/sk2/shikuang.swf?id=101070701"; //天气flash地址
	public static final String KEY_TIANMAO = "http://www.hldab.com/"; //http://m.tmall.com/?spm=0.0.0.0&sid=ab13adb60ac4a24c&v=1&pds=stedition%23h"; //"http://m.tmall.com/?sprefer=sypc01"
															//http://m.tmall.com/?spm=0.0.0.0&sid=ab13adb60ac4a24c&v=1&pds=stedition%23h
															//http://m.tmall.com/?v=0
	public static final String KEY_IMGURL = "imgurl"; 
	public static final String KEY_CATID = "catid"; 
	public static final String KEY_URL = "http://appsource.evebit.com/"; 

	

	public static final String KEY_IMGURL2 = "imgurl2"; 
	public static final String KEY_INTROTEXT = "introtext";
	public static final String KEY_FULLTEXT = "fulltext";
	public static final String KEY_CREATED = "created";
	public static final String KEY_CREATED_BY_ALIAS = "created_by_alias";
	public static final String KEY_MONERY = "monery";
	public static final String KEY_TITLE = "title";
	public static final String KEY_AFTERSALES = "aftersales";
	public static final String KEY_METADESC = "metadesc";
	public static final String KEY_CANSHU1 = "canshu1";
	public static final String KEY_CANSHU2 = "canshu2";
	public static final String KEY_CANSHU3 = "canshu3";
	public static final String KEY_CANSHU4 = "canshu4";
	public static final String KEY_CANSHU5 = "canshu5";
	public static final String KEY_CANSHU6 = "canshu6";
	public static final String KEY_CANSHU7 = "canshu7";
	public static final String KEY_CANSHU8 = "canshu8";
	public static final String KEY_CANSHU9 = "canshu9";
	public static final String KEY_CANSHU10 = "canshu10";
	public static final String KEY_CANSHU11 = "canshu11";
	public static final String KEY_CANSHU12 = "canshu12";
	public static final String KEY_CANSHU13 = "canshu13";
	public static final String KEY_CANSHU14 = "canshu14";
	public static final String KEY_CANSHU15 = "canshu15";
	public static final String KEY_CANSHU16 = "canshu16";
	public static final String KEY_CANSHU17 = "canshu17";
	public static final String KEY_CANSHU18 = "canshu18";
	public static final String KEY_CANSHU19 = "canshu19";
	public static final String KEY_CANSHU20 = "canshu20";
	public static final String KEY_CANSHU21 = "canshu21";
	public static final String KEY_CANSHU22 = "canshu22";
	public static final String KEY_CANSHU23 = "canshu23";
	public static final String KEY_CANSHU24 = "canshu24";
	public static final String KEY_CANSHU25 = "canshu25";
	public static final String KEY_CANSHU26 = "canshu26";
	public static final String KEY_CANSHU32 = "canshu1"; //新添加参数表
	
	/**
	 * 绑定控件
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews; 
	private ViewGroup viewGroup;
	private View home1,home2,home3;
	private ImageView imageView1,imageView2,imageView3;
	private ImageView poine1,poine2,poine3;	
	private Button button1,button2,button3,button4,button5,button6,button7,button8,button9;//此9个为下方按钮，各自对应说明在onClick方法内可查看	
/**
 * 数据变量
 */
	public static ArrayList<String> Enterdata = new ArrayList<String>();//存储获取的企业信息0为企业简介1为企业文化 2为企业荣誉
	public static ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>() ;//存储网络图片,在滑动列表显示，最大值为3	
	/**
	 * 声明定时器，做动态图片用
	 */
	private Timer timer = new Timer();
	private TimerTask task;
	private int timeInt =1; 
	private int poine = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater();
		PushAgent.getInstance(this).onAppStart();	
		
        pageViews = new ArrayList<View>();  
                
        home1 = inflater.inflate(R.layout.home_image1, null);
        home2 = inflater.inflate(R.layout.home_image2, null);
        home3 = inflater.inflate(R.layout.home_image3, null);

        pageViews.add(home1);
        pageViews.add(home2);
        pageViews.add(home3);  
        
        viewGroup = (ViewGroup)inflater.inflate(R.layout.home, null);  
        viewPager = (ViewPager)viewGroup.findViewById(R.id.Home_guidePages);  
        GuidePageAdapter_Image adapter = new GuidePageAdapter_Image(pageViews);//声明自定义adapter，放入内容
        viewPager.setAdapter(adapter);  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
		
        setContentView(viewGroup);
		
        /**
         * 绑定控件设置值,以下为选项卡之外控件
         */
		button1 = (Button)findViewById(R.id.Home_button1);
		button2 = (Button)findViewById(R.id.Home_button2);
		button3 = (Button)findViewById(R.id.Home_button3);
		button4 = (Button)findViewById(R.id.Home_button4);
		button5 = (Button)findViewById(R.id.Home_button5);
		button6 = (Button)findViewById(R.id.Home_button6);
		button7 = (Button)findViewById(R.id.Home_button7);
		button8 = (Button)findViewById(R.id.Home_button8);
		button9 = (Button)findViewById(R.id.Home_button9);
		poine1 = (ImageView)findViewById(R.id.Home_top_image1);
		poine2 = (ImageView)findViewById(R.id.Home_top_image2);
		poine3 = (ImageView)findViewById(R.id.Home_top_image3);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		
		/**
         * 绑定控件设置值,以下为3张导航图片
         */
		imageView1 = (ImageView)home1.findViewById(R.id.home1_image);//企业信息图片
		imageView2 = (ImageView)home2.findViewById(R.id.home2_image);//产品中心图片
		imageView3 = (ImageView)home3.findViewById(R.id.home3_image);//促销活动图片
		imageView1.setScaleType(ScaleType.FIT_XY);
		imageView2.setScaleType(ScaleType.FIT_XY);
		imageView3.setScaleType(ScaleType.FIT_XY);
		imageView1.setOnClickListener(this);
		imageView2.setOnClickListener(this);
		imageView3.setOnClickListener(this);
		
		timeThread();//启动定时器
		//EnterThread();
		//UrlThread();
		//DoingThread();//促销活动信息
		Handler.sendEmptyMessage(2);
		
		
		
		//从Launch.java搬运过来 为了在本类提交图片下载事件
				Normal normal = new Normal(this);
				if (normal.note_Intent()) {
					UrlThread();
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
					builder.setTitle(getResources().getString(R.string.Intent));
					builder.setPositiveButton(getResources().getString(R.string.client_no_update_ok), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
					builder.create().show();
				}
		
	}
	
	
	/**
	 * 来自 launch*/
	
	private Handler handler1 = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				imgThread();
				break;
			case 2:
				imageView1.setImageBitmap(bitmaps.get(0));
				imageView2.setImageBitmap(bitmaps.get(1));
				imageView3.setImageBitmap(bitmaps.get(2));
				break;
			default:
				break;
			}
			
		}
		
	};
	
	private void pull(){
		
		
	}
	
	private void imgThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++) {
					URL imageUrl =null;
					Bitmap bitmap =null;
					try {
						imageUrl = new URL(imgUrldata.get(i).get(HomeActivity.KEY_IMGURL));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
		
						HttpURLConnection connection =(HttpURLConnection)imageUrl.openConnection();
						connection.setDoInput(true);
						connection.connect();
						InputStream is=connection.getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is);
						
						
						//设置图片缩放，以避免内存溢出
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = false;
						options.inSampleSize = 3 ;//将width，hight 设为原来的十分之一
						options.inPreferredConfig = Bitmap.Config.RGB_565;
						options.inPurgeable = true;
						options.inInputShareable = true;
						
						bitmap = BitmapFactory.decodeStream(bis,null,options);
						bis.close();
						is.close();
						bitmaps.add(bitmap);
					} catch (Exception e) {
						// TODO: handle exception
						bitmaps.add(null);
					}
				}
				
				handler1.sendEmptyMessage(2);
			}
			
		}.start();
	}
	
	private void UrlThread()//
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
				try {
					
					data = DataManeger.getTestData(imgUrlString);
					ArrayList<Test_Model> datalist = data.getData();
					for (Test_Model test_Model : datalist) {					
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						hashtable.put(HomeActivity.KEY_IMGURL, (test_Model.getImgUrl()==null? "": test_Model.getImgUrl()));
						imgUrldata.add(hashtable);	
					}
					Log.v("图片地址", imgUrldata.toString());
					handler1.sendEmptyMessage(0);
					
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}.start();
	}
	
	
	
	
	
	/**
	 * msg.what 
	 * 0:定时器消息，进行图片翻页
	 * 1：图片网络地址获取完毕，启动网络获取图片
	 * 2：
	 */
	private Handler Handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				viewPager.setCurrentItem(timeInt);
				switch (poine) {
				case 0:
					poine1.setImageResource(R.drawable.point_true);
					poine2.setImageResource(R.drawable.point_flase);
					poine3.setImageResource(R.drawable.point_flase);
					break;
				case 1:
					poine1.setImageResource(R.drawable.point_flase);
					poine2.setImageResource(R.drawable.point_true);
					poine3.setImageResource(R.drawable.point_flase);
					break;
				case 2:
					poine1.setImageResource(R.drawable.point_flase);
					poine2.setImageResource(R.drawable.point_flase);
					poine3.setImageResource(R.drawable.point_true);
					break;
				default:
					break;
				}
				break;
				
			case 1:
				switch (poine) {
				case 0:
					poine1.setImageResource(R.drawable.point_true);
					poine2.setImageResource(R.drawable.point_flase);
					poine3.setImageResource(R.drawable.point_flase);
					break;
				case 1:
					poine1.setImageResource(R.drawable.point_flase);
					poine2.setImageResource(R.drawable.point_true);
					poine3.setImageResource(R.drawable.point_flase);
					break;
				case 2:
					poine1.setImageResource(R.drawable.point_flase);
					poine2.setImageResource(R.drawable.point_flase);
					poine3.setImageResource(R.drawable.point_true);
					break;
				default:
					break;
				}
				break;
			
			default:
				break;
			}
			
		}
		
	};
	
	private void timeThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				task = new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						timeInt++;
//						if (timeInt == 3) {
//							timeInt = 0;
//							
//						}
						Handler.sendEmptyMessage(0);
					}
				};
				
				timer.schedule(task, 6000,6000);
			}
			
		}.start();
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

        	timeInt = (arg0 + 1);
        	
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
        	poine = arg0;
        	//页面判断
        	if(arg0>2){  
                arg0=arg0%pageViews.size();  
                poine = arg0;
               }  
        	Handler.sendEmptyMessage(1);
        }  
    }
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	
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
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		
		switch (v.getId()) {
		case R.id.Home_button1://"在线商城"
//			intent = new Intent(HomeActivity.this, webActivity.class);
//			intent.putExtra("tabBar",getResources().getString(R.string.Home_string_button1).toString());
//			intent.putExtra("url", tianmao);			
//			startActivity(intent);
				
			intent = new Intent(HomeActivity.this, webActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button1).toString());
			intent.putExtra("url",KEY_TIANMAO);//"http://bxhaiyang.taobao.com/");
			startActivity(intent);
			/*
			intent = new Intent(HomeActivity.this, WebViewActivity.class);
			intent.putExtra("tabBar",getResources().getString(R.string.Home_string_button1).toString());
			intent.putExtra("show", "no");
			startActivity(intent);
			*/
			break;
		case R.id.Home_button2://"应用下载"
			intent = new Intent(HomeActivity.this, webActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button2).toString());
			intent.putExtra("url", "http://hotapp.dolphin-browser.com/hotapps.html#home");
			
			startActivity(intent);
			break;
		case R.id.Home_button3://"企业信息"
			intent = new Intent(HomeActivity.this, EnterpriseActivity.class);
			EnterpriseActivity.list = Enterdata;
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button3).toString());
			startActivity(intent);
			break;
		case R.id.Home_button4://"促销活动"
			intent = new Intent(HomeActivity.this, DoingActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button4).toString());
			startActivity(intent);
			break;
		case R.id.Home_button5://"产品中心"
			intent = new Intent(HomeActivity.this, ProductActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button5).toString());
			startActivity(intent);
			break;
		case R.id.Home_button6://"售后服务"
			intent = new Intent(HomeActivity.this, AftermarketActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button6).toString());
			startActivity(intent);
			break;
		case R.id.Home_button7://"账单缴费"
			intent = new Intent(HomeActivity.this, PaymentActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button7).toString());
			startActivity(intent);
			break;
		case R.id.Home_button8://"便民服务"
			intent = new Intent(HomeActivity.this, ConvenientActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button8).toString());
			startActivity(intent);
			break;
		case R.id.Home_button9://"联系我们"
			intent = new Intent(HomeActivity.this, ContactActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button9).toString());
			startActivity(intent);
			break;

		case R.id.home1_image://"企业信息"
			intent = new Intent(HomeActivity.this, EnterpriseActivity.class);
			EnterpriseActivity.list = Enterdata;
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button3).toString());
			startActivity(intent);
			break;
		case R.id.home2_image://"产品中心"
			intent = new Intent(HomeActivity.this, ProductActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button5).toString());
			startActivity(intent);
			break;
		case R.id.home3_image://"促销活动"
			intent = new Intent(HomeActivity.this, DoingActivity.class);
			intent.putExtra("tabBar", getResources().getString(R.string.Home_string_button4).toString());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
