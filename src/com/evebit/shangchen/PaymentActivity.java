package com.evebit.shangchen;
/**
 * 账单缴费
 */
import java.util.ArrayList;

import com.evebit.models.GuidePageAdapter;
import com.evebit.models.Normal;
import com.umeng.analytics.MobclickAgent;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends Activity implements android.view.View.OnClickListener{


	/**
	 * 绑定控件
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews; 
	private ViewGroup viewGroup;
	private View payment_calls1,payment_flow2,payment_payment3;
	
	
	private TextView textView1;//头部3个选项
	private TextView textView2;
	private TextView textView3;

	
	/**
     * 绑定控件设置值,以下为查询话费模块
     */
	private EditText Calls_EditText_Message;
	private EditText Calls_EditText_Phone;
	private Button Calls_Button;
	private Button Calls_button1,Calls_button2,Calls_button3;//1是电信  2是移动  3是联通
	private TextView Calls_TextView1,Calls_TextView2;
	
	/**
     * 绑定控件设置值,以下为查询流量模块
     */
	private EditText Flow_EditText_Message;
	private EditText Flow_EditText_Phone;
	private Button Flow_Button;
	private Button Flow_Button1,Flow_Button2,Flow_Button3;//1是电信  2是移动  3是联通
	private TextView Flow_TextView1,Flow_TextView2;
	/**
     * 绑定控件设置值,以下为缴费模块
     */
	
	private WebView Payment_webView;
	
	
	
	private Button back;
	private TextView tabBar;
	
	
	
	
	

	/**
	 * 数据变量
	 */
	private String tabString = null;//导航条名
	private String urlString = "http://wvs.m.taobao.com/?sprefer=p23596";
   String nameString []  = {//运营商名称
			"中国移动","中国联通","中国电信"
			};
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.payment);
		/**
		 * 设定滑动列表
		 */
				
				LayoutInflater inflater = getLayoutInflater();  
		        pageViews = new ArrayList<View>();  
		                
		        payment_calls1 = inflater.inflate(R.layout.payment_calls, null);
		        payment_flow2 = inflater.inflate(R.layout.payment_flow, null);
		        payment_payment3 = inflater.inflate(R.layout.payment_payment, null);

		        pageViews.add(payment_calls1);
		        pageViews.add(payment_flow2);
		        pageViews.add(payment_payment3);  
		        
		        viewGroup = (ViewGroup)inflater.inflate(R.layout.payment, null);  
		        viewPager = (ViewPager)viewGroup.findViewById(R.id.Payment_guidePages);  
		        GuidePageAdapter adapter = new GuidePageAdapter(pageViews);//声明自定义adapter，放入内容
		        viewPager.setAdapter(adapter);  
		        viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
				
		        setContentView(viewGroup);
		        /**
		         * 绑定控件设置值,以下为选项卡之外控件
		         */
		        textView1 = (TextView) findViewById(R.id.Payment_text1);  
		        textView2 = (TextView) findViewById(R.id.Payment_text2);  
		        textView3 = (TextView) findViewById(R.id.Payment_text3);  

		        textView1.setOnClickListener(new MyOnClickListener(0));  
		        textView2.setOnClickListener(new MyOnClickListener(1));  
		        textView3.setOnClickListener(new MyOnClickListener(2));
		        
		        back = (Button)findViewById(R.id.Payment_Button_back);
		        back.setOnClickListener(this);
		        tabString = getIntent().getExtras().getString("tabBar");
				tabBar = (TextView)findViewById(R.id.Payment_TextView_tabBar);
				tabBar.setText(tabString);//设定导航条名称
				
				
		        /**
		         * 绑定控件设置值,以下为查询话费模块
		         */
		        
				
				Calls_EditText_Message = (EditText)payment_calls1.findViewById(R.id.Payment_calls_EditText_Message);
				Calls_EditText_Phone = (EditText)payment_calls1.findViewById(R.id.Payment_calls_EditText_Phone);
				Calls_Button = (Button)payment_calls1.findViewById(R.id.Payment_calls_Button);
				Calls_Button.setOnClickListener(this);
				
				Calls_button1 = (Button)payment_calls1.findViewById(R.id.payment_calls_button1);
				Calls_button2 = (Button)payment_calls1.findViewById(R.id.payment_calls_button2);
				Calls_button3 = (Button)payment_calls1.findViewById(R.id.payment_calls_button3);
				Calls_TextView1 = (TextView)payment_calls1.findViewById(R.id.payment_calls_text);
				Calls_TextView2 = (TextView)payment_calls1.findViewById(R.id.payment_calls_text_phone);
				
				Calls_button1.setOnClickListener(this);
				Calls_button2.setOnClickListener(this);
				Calls_button3.setOnClickListener(this);

				/**
		         * 绑定控件设置值,以下为查询流量模块
		         */
				

				Flow_EditText_Message = (EditText)payment_flow2.findViewById(R.id.Payment_flow_EditText_Message);
				Flow_EditText_Phone = (EditText)payment_flow2.findViewById(R.id.Payment_flow_EditText_Phone);
				Flow_Button = (Button)payment_flow2.findViewById(R.id.Payment_flow_Button);
				Flow_Button.setOnClickListener(this);

				Flow_Button1 = (Button)payment_flow2.findViewById(R.id.payment_flow_button1);
				Flow_Button2 = (Button)payment_flow2.findViewById(R.id.payment_flow_button2);
				Flow_Button3 = (Button)payment_flow2.findViewById(R.id.payment_flow_button3);
				Flow_TextView1 = (TextView)payment_flow2.findViewById(R.id.payment_flow_text);
				Flow_TextView2 = (TextView)payment_flow2.findViewById(R.id.payment_flow_text_phone);
				
				Flow_Button1.setOnClickListener(this);
				Flow_Button2.setOnClickListener(this);
				Flow_Button3.setOnClickListener(this);
				
				/**
		         * 绑定控件设置值,以下为充值
		         */
				
				Payment_webView = (WebView)payment_payment3.findViewById(R.id.Payment_payment_WebView);
				
				WebSettings settings =Payment_webView.getSettings();
				settings.setJavaScriptEnabled(true);
				settings.setPluginsEnabled(true);
		
				
				
				Payment_webView.loadUrl(urlString);
			
				
				
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
			

			
			//滑动事件
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
		        	if (arg0 == 2) {
		        		Normal normal = new Normal(PaymentActivity.this);
		        		if (normal.note_Intent()) {
		        		}
		        		else {
		        			Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
		        		}
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
			public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.launch, menu);
				return true;
			}

			@Override
			public boolean onPrepareOptionsMenu(Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.Payment_Button_back:
					onBackPressed();
					
					break;
					
				case R.id.Payment_calls_Button:
					Log.v("到我这了", "我是点击1");
					sendSMS(Calls_EditText_Phone.getText().toString(),Calls_EditText_Message.getText().toString());
					break;
					
				case R.id.Payment_flow_Button:
					Log.v("到我这了", "我是点击2");
					Log.v("payment", Flow_EditText_Message.getText().toString());
					sendSMS(Flow_EditText_Phone.getText().toString(),Flow_EditText_Message.getText().toString());
					break;
					
				case R.id.payment_calls_button1:
					Calls_EditText_Phone.setText(getResources().getString(R.string.Payment_phone3));
					Calls_EditText_Message.setText(getResources().getString(R.string.Payment_phone_value2));
					
					Calls_TextView1.setText(nameString[2]);
					Calls_TextView2.setText(getResources().getString(R.string.Payment_phone4));
					break;
				case R.id.payment_calls_button2:
					Calls_EditText_Phone.setText(getResources().getString(R.string.Payment_phone1));
					Calls_EditText_Message.setText(getResources().getString(R.string.Payment_phone_value1));
					Calls_TextView1.setText(nameString[0]);
					Calls_TextView2.setText(getResources().getString(R.string.Payment_phone2));
					break;
				case R.id.payment_calls_button3:
					Calls_EditText_Phone.setText(getResources().getString(R.string.Payment_phone2));
					Calls_EditText_Message.setText(getResources().getString(R.string.Payment_phone_value2));
					Calls_TextView1.setText(nameString[1]);
					Calls_TextView2.setText(getResources().getString(R.string.Payment_phone2));
					break;
					
					
				case R.id.payment_flow_button1:
					//设定EditText的值
					Flow_EditText_Phone.setText(getResources().getString(R.string.Payment_phone3));
					Flow_EditText_Message.setText(getResources().getString(R.string.Payment_flow_value3));
					Flow_TextView1.setText(nameString[2]);
					Flow_TextView2.setText(getResources().getString(R.string.Payment_phone4));
					break;
				case R.id.payment_flow_button2:
					Flow_EditText_Phone.setText(getResources().getString(R.string.Payment_phone1));
					Flow_EditText_Message.setText(getResources().getString(R.string.Payment_flow_value1));
					Flow_TextView1.setText(nameString[0]);
					Flow_TextView2.setText(getResources().getString(R.string.Payment_phone2));
					break;
				case R.id.payment_flow_button3:
					Flow_EditText_Phone.setText(getResources().getString(R.string.Payment_phone2));
					Flow_EditText_Message.setText(getResources().getString(R.string.Payment_flow_value2));
					Flow_TextView1.setText(nameString[1]);
					Flow_TextView2.setText(getResources().getString(R.string.Payment_phone2));
					break;
					
					
					
				default:
					break;
				}
			}
			
			
			private void sendSMS(String phoneString,String smsString)
			{
				try {
					
					Uri smsToUri = Uri.parse("smsto:"+phoneString);  //如果想自己制定发送人，则填空，如：（"smsto:"）
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", smsString); 
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("我是短信异常", "Exception: ~~~~~~~~~~ ~~~~~~~~~~", e);
				}
			}
		}
