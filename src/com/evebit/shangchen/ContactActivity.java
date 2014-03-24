package com.evebit.shangchen;
/**
 * 联系我们
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.evebit.adapter.MyPhoneAdapter;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactActivity extends Activity{
	/**
	 *  Home参数解释：
	 *  HomeActivity.KEY_TITLE：		标题
	 *  HomeActivity.KEY_CANSHU1：	电话
	 *  HomeActivity.KEY_CANSHU2：	地址
	 */
		private MyPhoneAdapter mAdapter;
		private ListView listView;
		private TextView tabBar;
		private Button back;
		/**
		 * 数据变量
		 */
		private String tabString = null;//导航条名
		private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();
		private ArrayList<HashMap<String, Object>> Date = new ArrayList<HashMap<String,Object>>();
		private String url = HomeActivity.KEY_URL+"index.php?option=com_content&id=8&statez=10"+HomeActivity.KEY_ID;
		private String [] HotelNmae ={
							"7天连锁酒店","汉庭连锁酒店","格林豪泰连锁酒店","锦江之星连锁酒店","岭南佳园连锁酒店",
							"莫泰168连锁酒店","如家连锁酒店","速8连锁酒店","携程旅行网","阳光旅行网","艺龙旅行网","芒果网"};
		private String [] HotelPhone ={
				"4008740087","4008121121","4006998998","4008209999","4008308331",
				"4008207168","4008203333","4006038888","4008206666","4006869999","4006161616","4006640066"
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		tabString = getIntent().getExtras().getString("tabBar");
		listView = (ListView)findViewById(R.id.Contact_ListView);
		tabBar = (TextView)findViewById(R.id.Contact_TextView_tabBar);
		back = (Button)findViewById(R.id.Contact_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		tabBar.setText(tabString);//设定导航条名称
		
		if (tabString.equals("酒店预定")) {
			
			handler.sendEmptyMessage(1);
		}
		else {
			handler.sendEmptyMessage(0);
		}
		
	
		
		
	}
	/**
	 * msg 
	 * 0:获取联系我们数据
	 * 1：获取酒店信息数据
	 * 2：加载列表
	 */
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				conactDate();
				break;
			case 1:
				ConvenientDate();
				break;
			case 2:
				ListView();
				break;
			default:
				break;
			}
		}
		
	};
	
	/**
	 * 获取联系我们数据
	 */
	private void conactDate()
	{
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			Thread();
		}
		else {
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 获取酒店信息数据
	 */
	private void ConvenientDate()
	{
		for (int i = 0; i < HotelNmae.length; i++) {
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put(HomeActivity.KEY_TITLE,HotelNmae[i]);
			itemMap.put(HomeActivity.KEY_CANSHU1,"电话："+HotelPhone[i]);
			itemMap.put(HomeActivity.KEY_CANSHU2,"");
			arrayList.add(itemMap);
		}
		
		mAdapter = new MyPhoneAdapter(ContactActivity.this, arrayList);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				phone(arrayList.get(arg2).get(HomeActivity.KEY_CANSHU1).toString());
				
			}
		});
	}
	
	
	/**
	 * 获取联系我们数据
	 * 
	 */
	private void Thread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
				try {
					data = DataManeger.getTestData(url);
					ArrayList<Test_Model> datalist = data.getData();
					for (Test_Model test_Model : datalist) {					
						HashMap<String, Object> hashtable = new HashMap<String, Object>();
						hashtable.put(HomeActivity.KEY_TITLE, (test_Model.getTitle()==null? "": test_Model.getTitle()));
						hashtable.put(HomeActivity.KEY_CANSHU1, "电话："+(test_Model.getCanshu1()==null? "": test_Model.getCanshu1()));
						hashtable.put(HomeActivity.KEY_CANSHU2, "地址："+(test_Model.getCanshu2()==null? "": test_Model.getCanshu2()));
						Date.add(hashtable);
					}
					handler.sendEmptyMessage(2);
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	

	
	/**
	 * 更新UI
	 */
	private void ListView()
	{

		mAdapter = new MyPhoneAdapter(ContactActivity.this, Date);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				phone(Date.get(arg2).get(HomeActivity.KEY_CANSHU1).toString());
				
			}
		});
		
	}
	
	//调用系统拨号界面并指定号码
	private void phone(String tel)
	{
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel)); 
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	        startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
}

