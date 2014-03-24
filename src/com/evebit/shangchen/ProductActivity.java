package com.evebit.shangchen;
/**
 * 产品中心
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


import com.evebit.adapter.LazyAdapter;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;

import com.evebit.listview.XListView;
import com.evebit.listview.XListView.IXListViewListener;
import com.evebit.models.GuidePageAdapter;
import com.evebit.models.Normal;
import com.evebit.sortlistview.CharacterParser;
import com.evebit.sortlistview.ClearEditText;
import com.evebit.sortlistview.PinyinComparator;
import com.evebit.sortlistview.SideBar;
import com.evebit.sortlistview.SortAdapter;
import com.evebit.sortlistview.SortModer;
import com.evebit.sortlistview.SideBar.OnTouchingLetterChangedListener;



import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity implements IXListViewListener{

	/**
	 * 注明  参数 introtext在此类中被修改成售后服务的网页
	 * aftersales被修改成内容简介
	 */
	/**
	 * 绑定控件
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews; 
	private ViewGroup viewGroup;
	private View pageView1,pageView2,pageView3;
	//两个关于字母排序列表的控件
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private EditText mClearEditText;
	private String search;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModer> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	/***/
	
	
	private TextView textView1;//头部3个选项
	private TextView textView2;
	private TextView textView3;
	

	
	
	private LazyAdapter SumAdapter;
	private LazyAdapter RankingAdapter;
	private XListView sumListView;
	private ListView rankingListView;
	private TextView tabBar;
	private Button back;
	private int mark = 1;
	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private ArrayList<HashMap<String, String>> SumList = new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> RankingList = new ArrayList<HashMap<String,String>>();
	

	private String timeString = "刚刚";//记录下拉刷新时间
	private ProgressDialog progressDialog;

	private String sumURL = HomeActivity.KEY_URL+"index.php?option=com_content&id=1&statez=10&limit=";
	private String rankingURL = HomeActivity.KEY_URL+"index.php?option=com_content&id=7&statez=10"+HomeActivity.KEY_ID;
	private String searchtext ;
	
	ArrayList<Hashtable<String, String>> SumData = new ArrayList<Hashtable<String, String>>();//存储获取的网络信息
	ArrayList<Hashtable<String, String>> RankingData = new ArrayList<Hashtable<String, String>>();//存储获取的网络信息
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		/**
		 * 设定滑动列表
		 */
				
		LayoutInflater inflater = getLayoutInflater();  
		pageViews = new ArrayList<View>();  
		                
		pageView1 = inflater.inflate(R.layout.product_sum, null);
		pageView2 = inflater.inflate(R.layout.product_ranking, null);
		pageView3 = inflater.inflate(R.layout.sort_list_index, null);


		pageViews.add(pageView1);
		pageViews.add(pageView2);
		pageViews.add(pageView3);
		
		        
		viewGroup = (ViewGroup)inflater.inflate(R.layout.product, null);  
		viewPager = (ViewPager)viewGroup.findViewById(R.id.Product_guidePages);  
		GuidePageAdapter adapter = new GuidePageAdapter(pageViews);//声明自定义adapter，放入内容
		viewPager.setAdapter(adapter);  
		viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
				
		setContentView(viewGroup);
		
		/**
		* 绑定控件设置值,以下为选项卡之外控件
		*/

		
		 textView1 = (TextView) findViewById(R.id.Product_text1);  
		 textView2 = (TextView) findViewById(R.id.Product_text2); 
		 textView3 = (TextView) findViewById(R.id.Product_text3);
		 mClearEditText = (EditText)findViewById(R.id.Product_text4);
		 
		 
		 
		mClearEditText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
					filterData(s.toString());
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				}
			});
		
		
		
		
		 Button button = (Button) findViewById(R.id.Product_Button_search);
		 
		 
		 button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
						//这里要利用adapter.getItem(position)来获取当前position所对应的对象
						//Toast.makeText(getApplication(), ((SortModer)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(ProductActivity.this,SearchProductActivity.class);
						//获取item的text文本
						Log.v("----searchbutton", mClearEditText.getText().toString());
						
						intent.putExtra("Search", mClearEditText.getText().toString());
						startActivity(intent);
					
				}
				});
		
		
		        
		 textView1.setOnClickListener(new MyOnClickListener(0));  
		 textView2.setOnClickListener(new MyOnClickListener(1));  
		 textView3.setOnClickListener(new MyOnClickListener(2));  
		
		
		
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
		
		/**
		 * 总列表
		 */
		sumListView = (XListView)pageView1.findViewById(R.id.Product_ListView);
		sumListView.setPullLoadEnable(true);
		sumListView.setXListViewListener(this);
		
		sumListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
				Log.v("---重点---", SumData.get(Integer.parseInt(String.valueOf(arg2-1))).get(HomeActivity.KEY_CANSHU1));
				ProductDetailActivity.date = SumData;
				intent.putExtra("arg2", String.valueOf(arg2-1));
				intent.putExtra("tabBar", SumList.get(arg2-1).get(HomeActivity.KEY_TITLE).toString());
				startActivity(intent);
			}
		});
		/**
		 * 排行列表
		 */
		
		rankingListView = (ListView)pageView2.findViewById(R.id.Product_Ranking_ListView);
		
		
		rankingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//ProductDetailActivity activDetailActivityity = new ProductDetailActivity();
				Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
				ProductDetailActivity.date = RankingData;
				intent.putExtra("arg2", String.valueOf(arg2));
				intent.putExtra("tabBar", RankingList.get(arg2).get(HomeActivity.KEY_TITLE).toString());
				
				startActivity(intent);
			}
		});
		
		
		
		initViews();
		

		
		
		
		Normal normal = new Normal(this);
		if (normal.note_Intent()) {
			
			progressDialog = ProgressDialog.show(ProductActivity.this, "", getResources().getString(R.string.xlistview_header_hint_loading), true, false);
			progressDialog.setCancelable(true);
			Thread();
			RankingThread();
		}
		else {
			Toast.makeText(getApplicationContext(), "请链接网络", Toast.LENGTH_SHORT).show();
		}
		
		
	}

	//绑定数据到 listview
	
	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();

		//设置右侧触摸监听
	
		sortListView = (ListView)pageView3.findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//Toast.makeText(getApplication(), ((SortModer)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ProductActivity.this,SearchProductActivity.class);
				//获取item的text文本
				Log.v("---sansung---",((SortModer)adapter.getItem(arg2)).getName() );
				intent.putExtra("Search",((SortModer)adapter.getItem(arg2)).getName() );
				intent.putExtra("tabBar", ((SortModer)adapter.getItem(arg2)).getName());
				startActivity(intent);
				
				
			}
		});
		
		SourceDateList = filledData(getResources().getStringArray(R.array.date));
		
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		
	}
	
	
	
	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModer> filledData(String [] date){
		List<SortModer> mSortList = new ArrayList<SortModer>();
		
		for(int i=0; i<date.length; i++){
			SortModer sortModel = new SortModer();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setFirstletter(sortString.toUpperCase());
			}else{
				sortModel.setFirstletter("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModer> filterDateList = new ArrayList<SortModer>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModer sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);

	}
	
	
	
	
	/**
	 * 初次加载数据
	 */
	private void Thread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				urlDate(sumURL+mark+HomeActivity.KEY_ID, SumData,2);
				
			}
			
		}.start();
	}
	/**
	 * 加载促销列表数据
	 */
	private void  RankingThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				urlDate(rankingURL, RankingData,9);
			}
			
		}.start();
	}
	


	
	
	
	/**
	 * 头部2个选项点击事件
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
	 * 头部2个图片更换
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
	
	
	
	/**
	 * 下拉刷新加载数据
	 */
	private void  RefreshThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mark = 1;
				urlDate(sumURL+mark+HomeActivity.KEY_ID, SumData,0);
			}
			
		}.start();
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		timeS();
		SumData.clear();
		
		RefreshThread();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

		if (SumData.size() /mark ==10) {
			mark++;
			//SumData.clear();
			LoadThread();
		}
		else {
			handler.sendEmptyMessage(3);
			Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 加载更多
	 */
	private void LoadThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				urlDate(sumURL+mark+HomeActivity.KEY_ID, SumData,1);
			}
			
		}.start();
	}
	
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0://下拉刷新事件关闭
				onLoad();
				SumList.clear();
				SumListView(4);
				break;
			case 1:
				SumListView(5);
				
				break;
			case 2:
				SumListView(4);
				break;
			case 3:
				onLoad();
				break;
			case 4:
				SumAdapter = new LazyAdapter(ProductActivity.this, SumList);
				sumListView.setAdapter(SumAdapter);
				break;
			case 5:
				SumAdapter.notifyDataSetChanged();
				onLoad();
				break;
			case 9:
				progressDialog.dismiss();
				rankingListView();
				break;
			
			default:
				break;
			}
		}
		
	};
	
	
	/**
	 * 加载所有产品列表
	 */
	private void SumListView(int what)
	{
		Log.v("--SumDate--", ((mark-1)*10)+"---"+SumData.size());
		if (SumData.size() != 0) {
			
		for (int i = ((mark-1)*10); i < SumData.size(); i++) {

			HashMap<String, String> itemMap = new HashMap<String, String>();
			

			itemMap.put(HomeActivity.KEY_TITLE,SumData.get(i).get(HomeActivity.KEY_TITLE));
			itemMap.put(HomeActivity.KEY_MONERY,("¥："+SumData.get(i).get(HomeActivity.KEY_MONERY)));
			itemMap.put(HomeActivity.KEY_AFTERSALES,SumData.get(i).get(HomeActivity.KEY_AFTERSALES));
			itemMap.put(HomeActivity.KEY_IMGURL,SumData.get(i).get(HomeActivity.KEY_IMGURL));
			
			SumList.add(itemMap);
		}
		
		handler.sendEmptyMessage(what);
		
		
		}
	}
	/**
	 * 加载促销产品列表
	 */
	private void rankingListView()
	{
		if (RankingData.size()!=0) {
			
		
		for (int i = 0; i < RankingData.size(); i++) {
			HashMap<String, String> itemMap = new HashMap<String, String>();
			

			itemMap.put(HomeActivity.KEY_TITLE,RankingData.get(i).get(HomeActivity.KEY_TITLE));
			itemMap.put(HomeActivity.KEY_MONERY,("¥："+RankingData.get(i).get(HomeActivity.KEY_MONERY)));
			itemMap.put(HomeActivity.KEY_AFTERSALES,RankingData.get(i).get(HomeActivity.KEY_AFTERSALES));
			itemMap.put(HomeActivity.KEY_IMGURL,RankingData.get(i).get(HomeActivity.KEY_IMGURL));
			RankingList.add(itemMap);
		}
		
		RankingAdapter = new LazyAdapter(ProductActivity.this, RankingList);
		rankingListView.setAdapter(RankingAdapter);
		}
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//数据统计
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//数据统计
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
	/**
	 * 下拉或加载更多关闭
	 */
	private void onLoad() {
		sumListView.stopRefresh();
		sumListView.stopLoadMore();
		
		sumListView.setRefreshTime(timeString);
	}
	/**
	 * 记录下拉刷新时间
	 */
	private void timeS() {
		Calendar calendar = Calendar.getInstance();
		String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(calendar.get(Calendar.MINUTE));
		
		timeString = hour + ":" + minute;
	}


	/**
	 * 数据获取
	 * @param url			数据地址
	 * @param arrayList		数据存储数组
	 * @param i				通知编号
	 */
	private void urlDate(String url, ArrayList<Hashtable<String, String>> arrayList,int i)
	{
		Test_Bean data;
		try {
			data = DataManeger.getTestData(url);
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
				Log.v("KEY_CANSHU1---",  test_Model.getCanshu1()+"--");
				/**
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
				hashtable.put(HomeActivity.KEY_CANSHU26, (test_Model.getCanshu26()==null? "": test_Model.getCanshu26()));*/
				//hashtable.put(HomeActivity.KEY_CANSHU32, (test_Model.getCanshu32()==null? "": test_Model.getCanshu32()));
				//Log.v("KEY_CANSHU32---",  test_Model.getCanshu32()+"--");
				arrayList.add(hashtable);	
				
				
			}
			handler.sendEmptyMessage(i);
			
		} catch (Y_Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}
	
}
