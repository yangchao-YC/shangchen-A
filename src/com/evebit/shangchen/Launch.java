package com.evebit.shangchen;
/**
 * 启动页面
 * 
 * 
 * appkey友盟    52980ff956240b0ce2077333
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

import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

public class Launch extends Activity {

	private Timer timer = new Timer();
	private TimerTask task;
	
	private String enterpriseUrl = HomeActivity.KEY_URL+"index.php?option=com_content&id=2&statez=10"+HomeActivity.KEY_ID;
	ArrayList<String> Enterdata = new ArrayList<String>();//存储获取的企业信息0为企业简介1为企业文化 2为企业荣誉
	
	private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>() ;//存储网络图片,在滑动列表显示，最大值为3
	private ArrayList<Hashtable<String, String>> EnterList = new ArrayList<Hashtable<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);
		MobclickAgent.setDebugMode(true);
		//timeStart();
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			EnterThread();
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(Launch.this);
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

	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				EnterThread();
				break;
			case 3:
				push();
				break;
			default:
				break;
			}
			
		}
		
	};
	
	/**
	 * 3简介，4文化，5荣誉
	 * 原本来自 launch
	 */
	private void push()
	{
		
		for (int i = 0; i < EnterList.size(); i++) {
			if (EnterList.get(i).get(HomeActivity.KEY_CATID).equals("3")) {
				String introtext;
				byte b[] = android.util.Base64.decode(EnterList.get(i).get(HomeActivity.KEY_FULLTEXT), Base64.DEFAULT);
				introtext = new String(b);
				Enterdata.add(introtext);
			}
		}
		for (int i = 0; i < EnterList.size(); i++) {
			if (EnterList.get(i).get(HomeActivity.KEY_CATID).equals("4")) {
				String introtext;
				byte b[] = android.util.Base64.decode(EnterList.get(i).get(HomeActivity.KEY_FULLTEXT), Base64.DEFAULT);
				introtext = new String(b);
				Enterdata.add(introtext);
			}
		}
		for (int i = 0; i < EnterList.size(); i++) {
			if (EnterList.get(i).get(HomeActivity.KEY_CATID).equals("5")) {
				String introtext;
				byte b[] = android.util.Base64.decode(EnterList.get(i).get(HomeActivity.KEY_FULLTEXT), Base64.DEFAULT);
				introtext = new String(b);
				Enterdata.add(introtext);
			}
		}
		
		
		
		Intent intent = new Intent(Launch.this,HomeActivity.class);
		//HomeActivity.bitmaps = bitmaps;
		HomeActivity.Enterdata = Enterdata;
		startActivity(intent);
		finish();
	}
	/**
	 * 定时器
	 
	private void timeStart()
	{
		task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				timer.cancel();
				Intent intent = new Intent(Launch.this,HomeActivity.class);
				startActivity(intent);
				finish();
				
			}
		};
		
		timer.schedule(task, 1000,1000);
	}
	*/
	
	
	
	
	
	
	/**
	 * 获取企业信息
	 */
	private void EnterThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
				try {
					data = DataManeger.getTestData(enterpriseUrl);
					ArrayList<Test_Model> datalist = data.getData();
					for (Test_Model test_Model : datalist) {
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						hashtable.put(HomeActivity.KEY_CATID, (test_Model.getCatid()==null? "": test_Model.getCatid()));
						hashtable.put(HomeActivity.KEY_FULLTEXT, (test_Model.getFulltext()==null? "": test_Model.getFulltext()));
						EnterList.add(hashtable);	
					}
					handler.sendEmptyMessage(3);
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	

	
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
