package com.evebit.shangchen;
/**
 * 促销活动
 */
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import com.evebit.adapter.DoingAdapter;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;
import com.evebit.models.GuidePageAdapter_Image;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

public class DoingActivity extends Activity implements android.view.View.OnClickListener{
	
	/**
	 * 绑定控件
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews; 
	private ViewGroup viewGroup;
	private View pageView1,pageView2,pageView3;
	private ImageView imageView1,imageView2,imageView3;
	
	
	private DoingAdapter mAdapter;
	private ListView listView;
	private TextView tabBar;
	private Button back;
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
	private ArrayList<Hashtable<String, String>> Data = new ArrayList<Hashtable<String, String>>();//存储获取的网络信息
	private String imgUrlString = HomeActivity.KEY_URL+"index.php?option=com_content&statez=13&bannerid=5"+HomeActivity.KEY_ID;
	private String Doingurl = HomeActivity.KEY_URL+"index.php?option=com_content&id=6&statez=10"+HomeActivity.KEY_ID;
	private ArrayList<Hashtable<String, String>> imgUrldata = new ArrayList<Hashtable<String, String>>();//存储获取的图片地址
	private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>() ;//存储网络图片,在滑动列表显示，最大值为3
	private Timer timer = new Timer();
	private TimerTask task;
	private int timeInt =1;
	private int timeMax = 3;
	
	
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();
		/**
		 * 根据数据数目获取图片
		 * 如果数据少于3条，则显示对应数目的pageViews
		 * 另外要刷新timeInt计时器的最大值timeMax
		 */
//		if (Data.size()>=3) {
//			timeMax = 3;
//		}
//		else {
//			timeMax = Data.size();
//		}
		
	
		
		LayoutInflater inflater = getLayoutInflater();  
		pageViews = new ArrayList<View>();  
		                
		pageView1 = inflater.inflate(R.layout.home_image1, null);
		pageView2 = inflater.inflate(R.layout.home_image2, null);
		pageView3 = inflater.inflate(R.layout.home_image3, null);
		pageViews.add(pageView1);
		pageViews.add(pageView2);
		pageViews.add(pageView3); 
//		switch (timeMax) {
//		case 0:
//			handler.sendEmptyMessage(2);
//			break;
//		case 1:
//			pageViews.add(pageView1);
//			break;
//		case 2:
//			pageViews.add(pageView1);
//			pageViews.add(pageView2);
//			break;
//		case 3:
//			pageViews.add(pageView1);
//			pageViews.add(pageView2);
//			pageViews.add(pageView3); 
//
//			break;
//		default:
//			break;
//		}
		
		viewGroup = (ViewGroup)inflater.inflate(R.layout.doing, null); 
		viewPager = (ViewPager)viewGroup.findViewById(R.id.Doing_guidePages);  
		GuidePageAdapter_Image adapter = new GuidePageAdapter_Image(pageViews);//声明自定义adapter，放入内容
		viewPager.setAdapter(adapter);  
		viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
				
		 
		setContentView(viewGroup);
		
		/**
         * 绑定控件设置值,以下为3张导航图片
         */
		imageView1 = (ImageView)pageView1.findViewById(R.id.home1_image);
		imageView2 = (ImageView)pageView2.findViewById(R.id.home2_image);
		imageView3 = (ImageView)pageView3.findViewById(R.id.home3_image);
		imageView1.setScaleType(ScaleType.FIT_XY);
		imageView2.setScaleType(ScaleType.FIT_XY);
		imageView3.setScaleType(ScaleType.FIT_XY);
		imageView1.setOnClickListener(this);
		imageView2.setOnClickListener(this);
		imageView3.setOnClickListener(this);
		
		/**
		* 绑定控件设置值,以下为选项卡之外控件
		*/
		

		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Doing_TextView_tabBar);
		back = (Button)findViewById(R.id.Doing_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tabBar.setText(tabString);//设定导航条名称
		listView = (ListView)findViewById(R.id.Doing_ListView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pushWeb(arg2); 
			}
		});	
		
		/**
		 * 更新UI ，根据获取的数据判断显示多少个滑动图片
		 * 启动获取图片线程（imgThread）获取滑动图片
		 * 
		 */
		
		
		
		
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			
			progressDialog = ProgressDialog.show(DoingActivity.this, "", getResources().getString(R.string.xlistview_header_hint_loading), true, false);
			progressDialog.setCancelable(true);
			DoingThread();
			imagesThread();

		}
		else {
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
	}

	

	private void imagesThread()
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
					handler.sendEmptyMessage(4);
					
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	
	/**
	 * 促销活动
	 */
	
	private void DoingThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
				try {
					
					data = DataManeger.getTestData(Doingurl);
					ArrayList<Test_Model> datalist = data.getData();
					for (Test_Model test_Model : datalist) {					
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						hashtable.put(HomeActivity.KEY_TITLE, (test_Model.getTitle()==null? "": test_Model.getTitle()));
						hashtable.put(HomeActivity.KEY_CREATED, (test_Model.getCreated()==null? "": test_Model.getCreated()));
						hashtable.put(HomeActivity.KEY_CREATED_BY_ALIAS, (test_Model.getCreated_by_alias()==null? "": test_Model.getCreated_by_alias()));
						hashtable.put(HomeActivity.KEY_INTROTEXT, (test_Model.getIntrotext()==null? "": test_Model.getIntrotext()));
						hashtable.put(HomeActivity.KEY_IMGURL, (test_Model.getImgUrl()==null? "": test_Model.getImgUrl()));
						hashtable.put(HomeActivity.KEY_IMGURL2, (test_Model.getImgUrl2()==null? "": test_Model.getImgUrl2()));
						hashtable.put(HomeActivity.KEY_METADESC, (test_Model.getMetadesc()==null? "": test_Model.getMetadesc()));
						
						Data.add(hashtable);
					}
					handler.sendEmptyMessage(1);
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}.start();
	}
	
	
	
	
	private void ListView()
	{
		Log.v("date的长度", Data.size() + "");

		for (int i = 0; i < Data.size(); i++) {
			HashMap<String, String> itemMap = new HashMap<String, String>();
			itemMap.put(HomeActivity.KEY_TITLE,Data.get(i).get(HomeActivity.KEY_TITLE));
			itemMap.put(HomeActivity.KEY_METADESC,Data.get(i).get(HomeActivity.KEY_METADESC));
			itemMap.put(HomeActivity.KEY_IMGURL, Data.get(i).get(HomeActivity.KEY_IMGURL));
			arrayList.add(itemMap);
		}
		
		mAdapter = new DoingAdapter(DoingActivity.this,arrayList);
		listView.setAdapter(mAdapter);
	}
	
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
						handler.sendEmptyMessage(0);
					}
				};
				
				timer.schedule(task, 6000,6000);
			}
			
		}.start();
	}
	/**
	 * msg: 
	 * 0 定时器
	 * 1 数据获取刷新列表
	 * 2 数据获取更新UI
	 * 3 更新滑动图片
	 */
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				viewPager.setCurrentItem(timeInt);
				break;
			case 1:
				progressDialog.dismiss();
				ListView();
				break;
			case 2:
				AlertDialog.Builder builder = new AlertDialog.Builder(DoingActivity.this);
				builder.setTitle("当前无数据");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						onBackPressed();
					}
				});
				builder.create().show();
				break;
			case 3:
				/**
				 * 更新滑动图片
				 */
				
//				switch (timeMax) {
//				case 1:
//					imageView1.setImageBitmap(bitmaps.get(0));
//					break;
//				case 2:
//					imageView1.setImageBitmap(bitmaps.get(0));
//					imageView2.setImageBitmap(bitmaps.get(1));
//					break;
//				case 3:
//					imageView1.setImageBitmap(bitmaps.get(0));
//					imageView2.setImageBitmap(bitmaps.get(1));
//					imageView3.setImageBitmap(bitmaps.get(2));
//					break;
//				default:
//					break;
//				}
				imageView1.setImageBitmap(bitmaps.get(0));
				imageView2.setImageBitmap(bitmaps.get(1));
				imageView3.setImageBitmap(bitmaps.get(2));
				timeThread();
				break;
			case 4:
				imgThread();
				break;
			default:
				break;
			}
			
		}
		
	};
	
	

	/**
	 * 添加日期  2014年4月1日
	 * 作者：田兵
	 * 功能，解决图片加载时的溢出问题，主要蝕通过缩放显示像素的密度，来解决这个问题*/
	
	

	
	/**
	 * 网络获取需要显示的图片，获取完毕后存储到bitmaps中，然后通知imgHandler
	 */
	private void imgThread()
	{
		 new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < timeMax; i++) {
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
						//代码完成
						bitmap = BitmapFactory.decodeStream(bis,null,options);
						bis.close();
						is.close();
						bitmaps.add(bitmap);
					} catch (Exception e) {
						// TODO: handle exception
						bitmaps.add(null);
					}
				}
				
				handler.sendEmptyMessage(3);
			}
			
		}.start();
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
        	//页面判断
        	if(arg0>2){  
                arg0=arg0%pageViews.size();  
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		
	}
	

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Log.v("--finish--", "yes");
		super.finish();
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.home1_image:
			//pushWeb(0);
			break;
		case R.id.home2_image:
			//pushWeb(1);
			break;
		case R.id.home3_image:
			//pushWeb(2);
			break;

		default:
			break;
		}
	}
	
	private void pushWeb(int i)
	{
		DoingWebViewActivity activityw = new DoingWebViewActivity();
		activityw.data = Data;
		activityw.mark = i;
		Intent intent = new Intent(DoingActivity.this,DoingWebViewActivity.class);
		intent.putExtra("tabBar", arrayList.get(i).get("title").toString());
		startActivity(intent);
	}

	
}
