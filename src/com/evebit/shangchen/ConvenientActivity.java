package com.evebit.shangchen;
/**
 * 便民服务
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

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

public class ConvenientActivity extends Activity{

	private SimpleAdapter mAdapter;
	private ListView listView;
	private TextView tabBar;
	private Button back;
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();

	private String [] nameStrings = {"天气预报","快递查询","车票预定","酒店预定"};
	private int [] icon = {R.drawable.convenient1,R.drawable.convenient2,R.drawable.convenient3,R.drawable.convenient4};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.convenient);
		
		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Convenient_TextView_tabBar);
		back = (Button)findViewById(R.id.Convenient_Button_back);
		listView = (ListView)findViewById(R.id.ConvenIent_ListView);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tabBar.setText(tabString);//设定导航条名称

		
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("title",nameStrings[i]);
			itemMap.put("image",icon[i]);
			arrayList.add(itemMap);
		}
		
		mAdapter = new SimpleAdapter(ConvenientActivity.this,
				arrayList, R.layout.convenient_listview, new String[] {
						"title", "image"
						 },
				new int[] { R.id.conveninet_listview_Text_Content,
						R.id.conveninet_listview_Image
						 });
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent;
				switch (arg2) {
				case 0://天气查询
					intent = new Intent(ConvenientActivity.this, TianQiActivity.class);
					intent.putExtra("tabBar", nameStrings[arg2]);
					startActivity(intent);
					break;
				case 1://快递查询
					intent = new Intent(ConvenientActivity.this, webActivity.class);
					intent.putExtra("tabBar", nameStrings[arg2]);
					intent.putExtra("url", "http://m.kuaidi100.com/index_all.html?type=&postid=");
					startActivity(intent);
					break;
				case 2://车票预定
					intent = new Intent(ConvenientActivity.this, webActivity.class);
					intent.putExtra("tabBar", nameStrings[arg2]);
					intent.putExtra("url", "http://www.12306.cn/mormhweb/");
					startActivity(intent);
					break;
				case 3://酒店预定
					intent = new Intent(ConvenientActivity.this, ContactActivity.class);
					intent.putExtra("tabBar", nameStrings[arg2]);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
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
