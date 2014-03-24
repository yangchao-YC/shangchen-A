package com.evebit.shangchen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.evebit.adapter.LazyAdapter;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;
import com.evebit.models.Normal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchProductActivity extends Activity {

	private LazyAdapter searchAdapter;
	private String searchtext =null;
	ArrayList<Hashtable<String, String>> searchData = new ArrayList<Hashtable<String, String>>();
	private ArrayList<HashMap<String, String>> SearchList = new ArrayList<HashMap<String,String>>();
	private String searchURL = HomeActivity.KEY_URL+"index.php?option=com_content&statez=3"+HomeActivity.KEY_ID+"&serachtext=";
	private String sumURL = HomeActivity.KEY_URL+"index.php?option=com_content&id=1&statez=10&limit=";
	
	private ListView searchListView;
	private ProgressDialog progressDialog;
	
	
	private TextView tabBar;
	private Button back;
	
	private String tabString = null;//导航条名
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_product);
		
		
		searchtext = getIntent().getExtras().getString("Search");
		Log.v("--Search--",  getIntent().getExtras().getString("Search"));
		Log.v("this.searchtext----", "56---"+this.searchtext);
		searchURL = searchURL+this.searchtext;
		
		searchListView = (ListView)findViewById(R.id.Sort__Search_ListView);
		
		
				
		tabString = getIntent().getExtras().getString("tabBar");
		tabBar = (TextView)findViewById(R.id.Product_TextView_tabBar);
		
		back = (Button)findViewById(R.id.Product_Button_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tabBar.setText(tabString);//设定导航条名称
		
		
		
		
		
		searchListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//ProductDetailActivity activDetailActivityity = new ProductDetailActivity();
				Intent intent = new Intent(SearchProductActivity.this,ProductDetailActivity.class);
				ProductDetailActivity.date = searchData;
				intent.putExtra("arg2", String.valueOf(arg2));
				intent.putExtra("tabBar", SearchList.get(arg2).get(HomeActivity.KEY_TITLE).toString());
				
				startActivity(intent);
			}
		});
		
				
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			
			progressDialog = ProgressDialog.show(SearchProductActivity.this, "", getResources().getString(R.string.xlistview_header_hint_loading), true, false);
			progressDialog.setCancelable(true);
			Thread();
			
		//	searchListView();
			
		}
		else {
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			searchListView();
			
		}
		
		
	};
	
	
	
	private void Thread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.v("searchurl----", "136---"+searchURL);
				//urlDate(sumURL,searchData);
				urlDate(searchURL, searchData);
				
			}
			
		}.start();
	}
	
	
	

	
	
	
	private void searchListView()
	{
		Log.v("search----", "123---"+searchData.size());
		
		if (searchData.size()!=0) {
			
		
		for (int i = 0; i < searchData.size(); i++) {
			HashMap<String, String> itemMap = new HashMap<String, String>();
			

			itemMap.put(HomeActivity.KEY_TITLE,searchData.get(i).get(HomeActivity.KEY_TITLE));
			itemMap.put(HomeActivity.KEY_MONERY,("¥："+searchData.get(i).get(HomeActivity.KEY_MONERY)));
			itemMap.put(HomeActivity.KEY_AFTERSALES,searchData.get(i).get(HomeActivity.KEY_AFTERSALES));
			itemMap.put(HomeActivity.KEY_IMGURL,searchData.get(i).get(HomeActivity.KEY_IMGURL));
			SearchList.add(itemMap);
		}
		
		searchAdapter = new LazyAdapter(SearchProductActivity.this, SearchList);
		searchListView.setAdapter(searchAdapter);
		}
	}

	private void urlDate(String url, ArrayList<Hashtable<String, String>> arrayList)
	{
		Test_Bean data;
		try {
			data = DataManeger.getTestData(url);
			
			Log.v("data----", "182---"+data.getData().toString());
			
			ArrayList<Test_Model> datalist = data.getData();
			for (Test_Model test_Model : datalist) {					
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				hashtable.put(HomeActivity.KEY_TITLE, (test_Model.getTitle()==null? "": test_Model.getTitle()));
				hashtable.put(HomeActivity.KEY_INTROTEXT, (test_Model.getIntrotext()==null? "": test_Model.getIntrotext()));
				hashtable.put(HomeActivity.KEY_FULLTEXT, (test_Model.getFulltext()==null? "": test_Model.getFulltext()));
				hashtable.put(HomeActivity.KEY_MONERY, (test_Model.getMonery()==null? "": test_Model.getMonery()));
				hashtable.put(HomeActivity.KEY_AFTERSALES, (test_Model.getAftersales()==null? "": test_Model.getAftersales()));
				hashtable.put(HomeActivity.KEY_IMGURL, (test_Model.getImgUrl()==null? "": test_Model.getImgUrl()));
				hashtable.put(HomeActivity.KEY_CANSHU1, (test_Model.getCanshu1()==null? "": test_Model.getCanshu1()));
				hashtable.put(HomeActivity.KEY_CANSHU2, (test_Model.getCanshu2()==null? "": test_Model.getCanshu2()));
				hashtable.put(HomeActivity.KEY_CANSHU3, (test_Model.getCanshu3()==null? "": test_Model.getCanshu3()));
				hashtable.put(HomeActivity.KEY_CANSHU4, (test_Model.getCanshu4()==null? "": test_Model.getCanshu4()));
				hashtable.put(HomeActivity.KEY_CANSHU5, (test_Model.getCanshu5()==null? "": test_Model.getCanshu5()));
				hashtable.put(HomeActivity.KEY_CANSHU6, (test_Model.getCanshu6()==null? "": test_Model.getCanshu6()));
				hashtable.put(HomeActivity.KEY_CANSHU7, (test_Model.getCanshu7()==null? "": test_Model.getCanshu7()));
				hashtable.put(HomeActivity.KEY_CANSHU8, (test_Model.getCanshu8()==null? "": test_Model.getCanshu8()));
				hashtable.put(HomeActivity.KEY_CANSHU9, (test_Model.getCanshu9()==null? "": test_Model.getCanshu9()));
				hashtable.put(HomeActivity.KEY_CANSHU10, (test_Model.getCanshu10()==null? "": test_Model.getCanshu10()));
				hashtable.put(HomeActivity.KEY_CANSHU11, (test_Model.getCanshu11()==null? "": test_Model.getCanshu11()));
				hashtable.put(HomeActivity.KEY_CANSHU12, (test_Model.getCanshu12()==null? "": test_Model.getCanshu12()));
				hashtable.put(HomeActivity.KEY_CANSHU13, (test_Model.getCanshu13()==null? "": test_Model.getCanshu13()));
				hashtable.put(HomeActivity.KEY_CANSHU14, (test_Model.getCanshu14()==null? "": test_Model.getCanshu14()));
				hashtable.put(HomeActivity.KEY_CANSHU15, (test_Model.getCanshu15()==null? "": test_Model.getCanshu15()));
				hashtable.put(HomeActivity.KEY_CANSHU16, (test_Model.getCanshu16()==null? "": test_Model.getCanshu16()));
				hashtable.put(HomeActivity.KEY_CANSHU17, (test_Model.getCanshu17()==null? "": test_Model.getCanshu17()));
				hashtable.put(HomeActivity.KEY_CANSHU18, (test_Model.getCanshu18()==null? "": test_Model.getCanshu18()));
				hashtable.put(HomeActivity.KEY_CANSHU19, (test_Model.getCanshu19()==null? "": test_Model.getCanshu19()));
				hashtable.put(HomeActivity.KEY_CANSHU20, (test_Model.getCanshu20()==null? "": test_Model.getCanshu20()));
				hashtable.put(HomeActivity.KEY_CANSHU21, (test_Model.getCanshu21()==null? "": test_Model.getCanshu21()));
				hashtable.put(HomeActivity.KEY_CANSHU22, (test_Model.getCanshu22()==null? "": test_Model.getCanshu22()));
				hashtable.put(HomeActivity.KEY_CANSHU23, (test_Model.getCanshu23()==null? "": test_Model.getCanshu23()));
				hashtable.put(HomeActivity.KEY_CANSHU24, (test_Model.getCanshu24()==null? "": test_Model.getCanshu24()));
				hashtable.put(HomeActivity.KEY_CANSHU25, (test_Model.getCanshu25()==null? "": test_Model.getCanshu25()));
				hashtable.put(HomeActivity.KEY_CANSHU26, (test_Model.getCanshu26()==null? "": test_Model.getCanshu26()));
				//hashtable.put(HomeActivity.KEY_CANSHU32, (test_Model.getextra_fields_search()==null? "": test_Model.getextra_fields_search()));
				arrayList.add(hashtable);	
				
				
			}
			
		
			
			handler.sendEmptyMessage(0);
		} catch (Y_Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

}
