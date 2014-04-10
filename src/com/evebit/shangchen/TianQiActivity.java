package com.evebit.shangchen;
/**
 * 天气页面
 * 
 * 
 * appkey友盟    52980ff956240b0ce2077333
 */


import java.util.ArrayList;
import java.util.Hashtable;

import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Bean_TianQi;
import com.evebit.json.Test_Model;
import com.evebit.json.Test_Model_TianQi;
import com.evebit.json.Y_Exception;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class TianQiActivity extends Activity {




	private TextView tabBar;
	private Button back;
	private WebView webView;
	
	private TextView weekTextView1,weekTextView2,weekTextView3,weekTextView4,weekTextView5;     //日期
	private ImageView Image1,Image2,Image3,Image4,Image5;										//图片
	private TextView tempTextView1,tempTextView2,tempTextView3,tempTextView4,tempTextView5;		//度数
	private TextView weatherTextView1,weatherTextView2,weatherTextView3,weatherTextView4,weatherTextView5;	//天气状况
	private TextView windTextView1,windTextView2,windTextView3,windTextView4,windTextView5;		//风
	
	
	private TextView index_TextView;//穿衣
	private TextView index_d_TextView;//穿衣建议
	private TextView index_tr_TextView;//旅游
	private TextView index_xc_TextView;//洗车
	private TextView index_uv_TextView;//紫外线
	private TextView index_ag_TextView;//过敏
	private TextView index_co_TextView;//舒适度
	
	private int [] Aimages ={
			R.drawable.a_0,R.drawable.a_1,R.drawable.a_2,R.drawable.a_3,R.drawable.a_4,
			R.drawable.a_5,R.drawable.a_6,R.drawable.a_7,R.drawable.a_8,R.drawable.a_9,
			R.drawable.a_10,R.drawable.a_11,R.drawable.a_12,R.drawable.a_13,R.drawable.a_14,
			R.drawable.a_15,R.drawable.a_16,R.drawable.a_17,R.drawable.a_18,R.drawable.a_19,
			R.drawable.a_20,R.drawable.a_21,R.drawable.a_22,R.drawable.a_23,R.drawable.a_24,
			R.drawable.a_25,R.drawable.a_26,R.drawable.a_27,R.drawable.a_28,R.drawable.a_29,
			R.drawable.a_30,R.drawable.a_31,R.drawable.a_nothing
			};
	private int [] Bimages = {
			R.drawable.b_0,R.drawable.b_1,R.drawable.b_2,R.drawable.b_3,R.drawable.b_4,
			R.drawable.b_5,R.drawable.b_6,R.drawable.b_7,R.drawable.b_8,R.drawable.b_9,
			R.drawable.b_10,R.drawable.b_11,R.drawable.b_12,R.drawable.b_13,R.drawable.b_14,
			R.drawable.b_15,R.drawable.b_16,R.drawable.b_17,R.drawable.b_18,R.drawable.b_19,
			R.drawable.b_20,R.drawable.b_21,R.drawable.b_22,R.drawable.b_23,R.drawable.b_24,
			R.drawable.b_25,R.drawable.b_26,R.drawable.b_27,R.drawable.b_28,R.drawable.b_29,
			R.drawable.b_30,R.drawable.b_31,R.drawable.b_nothing
	};
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private String [] weekString   = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	private String [] week  = {"周一","周二","周三","周四","周五","周六","周日",
			"周一","周二","周三","周四","周五","周六","周日"};
	
	private int weekWark;
	private ArrayList<Hashtable<String, String>> date = new ArrayList<Hashtable<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tianqi);
		PushAgent.getInstance(this).onAppStart();	
		/**
		 * 绑定控件
		 */
		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Tianqi_TextView_tabBar);
		webView = (WebView)findViewById(R.id.TianQi_WebView);
		WebSettings settings =webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDisplayZoomControls(false);
		settings.setPluginsEnabled(true);
		tabBar.setText(tabString);
		
		
		
		/**
		 * 天气控件
		 */
		weekTextView1 = (TextView)findViewById(R.id.TianQi_Week1);
		weekTextView2 = (TextView)findViewById(R.id.TianQi_Week2);
		weekTextView3 = (TextView)findViewById(R.id.TianQi_Week3);
		weekTextView4 = (TextView)findViewById(R.id.TianQi_Week4);
		weekTextView5 = (TextView)findViewById(R.id.TianQi_Week5);
		
		Image1 = (ImageView)findViewById(R.id.TianQi_Image1);
		Image2 = (ImageView)findViewById(R.id.TianQi_Image2);
		Image3 = (ImageView)findViewById(R.id.TianQi_Image3);
		Image4 = (ImageView)findViewById(R.id.TianQi_Image4);
		Image5 = (ImageView)findViewById(R.id.TianQi_Image5);
		
		tempTextView1 = (TextView)findViewById(R.id.TianQi_temp1);
		tempTextView2 = (TextView)findViewById(R.id.TianQi_temp2);
		tempTextView3 = (TextView)findViewById(R.id.TianQi_temp3);
		tempTextView4 = (TextView)findViewById(R.id.TianQi_temp4);
		tempTextView5 = (TextView)findViewById(R.id.TianQi_temp5);
		
		weatherTextView1 = (TextView)findViewById(R.id.TianQi_weather1);
		weatherTextView2 = (TextView)findViewById(R.id.TianQi_weather2);
		weatherTextView3 = (TextView)findViewById(R.id.TianQi_weather3);
		weatherTextView4 = (TextView)findViewById(R.id.TianQi_weather4);
		weatherTextView5 = (TextView)findViewById(R.id.TianQi_weather5);
		
		
		windTextView1 = (TextView)findViewById(R.id.TianQi_wind1);
		windTextView2 = (TextView)findViewById(R.id.TianQi_wind2);
		windTextView3 = (TextView)findViewById(R.id.TianQi_wind3);
		windTextView4 = (TextView)findViewById(R.id.TianQi_wind4);
		windTextView5 = (TextView)findViewById(R.id.TianQi_wind5);
		

		index_TextView = (TextView)findViewById(R.id.TianQi_index);
		index_d_TextView = (TextView)findViewById(R.id.TianQi_index_d);
		index_tr_TextView = (TextView)findViewById(R.id.TianQi_index_tr);
		index_xc_TextView = (TextView)findViewById(R.id.TianQi_index_xc);
		index_uv_TextView = (TextView)findViewById(R.id.TianQi_index_uv);
		index_ag_TextView = (TextView)findViewById(R.id.TianQi_index_ag);
		index_co_TextView = (TextView)findViewById(R.id.TianQi_index_co);
		
		
		back = (Button)findViewById(R.id.Tianqi_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			Therad();
			webView.loadUrl(HomeActivity.KEY_TIANQI_FLASH);
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(TianQiActivity.this);
			builder.setTitle("当前网络，请检查网络状况");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});
			builder.create().show();
		}
		

	}

	private void Therad2()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		}.start();
	}
	private void Therad()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {		
					Test_Bean_TianQi data;
					data = DataManeger.getTestData_TianQi(HomeActivity.KEY_TIANQI);
					Test_Model datalist = data.getData();
				
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						hashtable.put("date_y", datalist.getWeatherinfo().getDate_y());
						hashtable.put("week", datalist.getWeatherinfo().getweek());
						hashtable.put("temp1",datalist.getWeatherinfo().gettemp1());
						hashtable.put("temp2", datalist.getWeatherinfo().gettemp2());
						hashtable.put("temp3", datalist.getWeatherinfo().gettemp3());
						hashtable.put("temp4", datalist.getWeatherinfo().gettemp4());
						hashtable.put("temp5", datalist.getWeatherinfo().gettemp5());
						hashtable.put("temp6", datalist.getWeatherinfo().gettemp6());
						hashtable.put("img1", datalist.getWeatherinfo().getimg1());
						hashtable.put("img3", datalist.getWeatherinfo().getimg3());
						hashtable.put("img5", datalist.getWeatherinfo().getimg5());
						hashtable.put("img7", datalist.getWeatherinfo().getimg7());
						hashtable.put("img9", datalist.getWeatherinfo().getimg9());
						hashtable.put("img11", datalist.getWeatherinfo().getimg11());
						hashtable.put("weather1", datalist.getWeatherinfo().getweather1());
						hashtable.put("weather2", datalist.getWeatherinfo().getweather2());
						hashtable.put("weather3", datalist.getWeatherinfo().getweather3());
						hashtable.put("weather4", datalist.getWeatherinfo().getweather4());
						hashtable.put("weather5", datalist.getWeatherinfo().getweather5());
						hashtable.put("weather6", datalist.getWeatherinfo().getweather6());
						hashtable.put("index", datalist.getWeatherinfo().getindex());
						hashtable.put("index_d", datalist.getWeatherinfo().getindex_d());
						hashtable.put("index_xc", datalist.getWeatherinfo().getindex_xc());
						hashtable.put("index_tr", datalist.getWeatherinfo().getindex_tr());
						hashtable.put("index_co", datalist.getWeatherinfo().getindex_co());
						hashtable.put("index_cl", datalist.getWeatherinfo().getindex_cl());
						hashtable.put("index_ls", datalist.getWeatherinfo().getindex_ls());
						hashtable.put("index_ag", datalist.getWeatherinfo().getindex_ag());
						hashtable.put("index_uv", datalist.getWeatherinfo().getindex_uv());
						hashtable.put("wind1", datalist.getWeatherinfo().getwind1());
						hashtable.put("wind2", datalist.getWeatherinfo().getwind2());
						hashtable.put("wind3", datalist.getWeatherinfo().getwind3());
						hashtable.put("wind4", datalist.getWeatherinfo().getwind4());
						hashtable.put("wind5", datalist.getWeatherinfo().getwind5());
						hashtable.put("wind6", datalist.getWeatherinfo().getwind6());
						date.add(hashtable);
					handler.sendEmptyMessage(0);
					
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					Log.v("---TianQi---", "114");
					e.printStackTrace();
				}
			}
			
		}.start();
	}

	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			for (int i = 0; i < weekString.length; i++) {
				if (date.get(0).get("week").equals(weekString[i])) {
					Log.v("---TianQi---", i + "------" +weekString[i]);
					weekWark = i;
				
				}
				
			}
			//设置日期
			weekTextView1.setText(week[weekWark]);
			weekTextView2.setText(week[weekWark+1]);
			weekTextView3.setText(week[weekWark+2]);
			weekTextView4.setText(week[weekWark+3]);
			weekTextView5.setText(week[weekWark+4]);
			//设置图片
			Image1.setImageResource(Aimages[Integer.parseInt(date.get(0).get("img1"))]);
			Image2.setImageResource(Bimages[Integer.parseInt(date.get(0).get("img3"))]);
			Image3.setImageResource(Bimages[Integer.parseInt(date.get(0).get("img5"))]);
			Image4.setImageResource(Bimages[Integer.parseInt(date.get(0).get("img7"))]);
			Image5.setImageResource(Bimages[Integer.parseInt(date.get(0).get("img9"))]);

			//设置度数
			tempTextView1.setText(date.get(0).get("temp1"));
			tempTextView2.setText(date.get(0).get("temp2"));
			tempTextView3.setText(date.get(0).get("temp3"));
			tempTextView4.setText(date.get(0).get("temp4"));
			tempTextView5.setText(date.get(0).get("temp5"));
			//设置天气状况
			weatherTextView1.setText(date.get(0).get("weather1"));
			weatherTextView2.setText(date.get(0).get("weather2"));
			weatherTextView3.setText(date.get(0).get("weather3"));
			weatherTextView4.setText(date.get(0).get("weather4"));
			weatherTextView5.setText(date.get(0).get("weather5"));
			
			//设置风
			
			windTextView1.setText(date.get(0).get("wind1"));
			windTextView2.setText(date.get(0).get("wind2"));
			windTextView3.setText(date.get(0).get("wind3"));
			windTextView4.setText(date.get(0).get("wind4"));
			windTextView5.setText(date.get(0).get("wind5"));
			
			

			
			
			index_TextView.setText(date.get(0).get("index"));
			index_d_TextView.setText(date.get(0).get("index_d"));
			index_tr_TextView.setText(date.get(0).get("index_tr"));
			index_xc_TextView.setText(date.get(0).get("index_xc"));
			index_uv_TextView.setText(date.get(0).get("index_uv"));
			index_ag_TextView.setText(date.get(0).get("index_ag"));
			index_co_TextView.setText(date.get(0).get("index_co"));
			
			Log.v("---TianQi---", "129");
			Log.v("---TianQi---", date.toString());
		}
		
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onResume(this);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPause(this);
	}

	
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
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

}

