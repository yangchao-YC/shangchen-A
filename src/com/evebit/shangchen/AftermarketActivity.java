package com.evebit.shangchen;
/**
 * 售后服务
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.evebit.adapter.MyAdapter;
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

public class AftermarketActivity extends Activity{


	private MyAdapter mAdapter;
	private ListView listView;
	private TextView tabBar;
	private Button back;
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();
	private String url = HomeActivity.KEY_URL+"index.php?option=com_content&id=9&statez=10"+HomeActivity.KEY_ID;
	/**
	 * 产品标识
	 * 
	 * 1，三星、2，联想、3，诺基亚、4，vivo、5，酷比、6，摩托罗拉、
	 * 7，酷派、8HTC、9，OPPO、10，魅族、11，LG、12，苹果、
	 * 13，黑莓、14，天语、15，金立、16，长虹、17，中兴、
	 * 18，华为、19，飞利浦、20，索尼、21，小米、22，TCL
	 */
	
	private int [] images = {R.drawable.logo1,
			R.drawable.logo1,R.drawable.logo2,R.drawable.logo3,R.drawable.logo4,
			R.drawable.logo5,R.drawable.logo6,R.drawable.logo7,R.drawable.logo8,
			R.drawable.logo9,R.drawable.logo10,R.drawable.logo11,R.drawable.logo12,
			R.drawable.logo13,R.drawable.logo14,R.drawable.logo15,R.drawable.logo16,
			R.drawable.logo17,R.drawable.logo18,R.drawable.logo19,R.drawable.logo20,
			R.drawable.logo21,R.drawable.logo22};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aftermarket);
		
		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Aftermarket_TextView_tabBar);
		back = (Button)findViewById(R.id.Aftermarket_Button_back);
		listView = (ListView)findViewById(R.id.Aftermarket_ListView);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		tabBar.setText(tabString);
		
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			Thread();
		}
		else {
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
		

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				phone(arrayList.get(arg2).get(HomeActivity.KEY_CANSHU2).toString());
			}
		});
		
		
		
	}

	
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
						hashtable.put(HomeActivity.KEY_CANSHU1, images[Integer.parseInt((test_Model.getCanshu1()==null? "0": test_Model.getCanshu1()))]);
						hashtable.put(HomeActivity.KEY_CANSHU2,"电话："+(test_Model.getCanshu2()==null? "": test_Model.getCanshu2()));
						hashtable.put(HomeActivity.KEY_CANSHU3,"地址："+(test_Model.getCanshu3()==null? "": test_Model.getCanshu3()));
						arrayList.add(hashtable);
					}
					handler.sendEmptyMessage(0);
				} catch (Y_Exception e) {
					// TODO Auto-generated catch block
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
			
			
			mAdapter = new MyAdapter(AftermarketActivity.this, arrayList);
			listView.setAdapter(mAdapter);
		}
		
	};
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

