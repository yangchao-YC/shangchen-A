package com.evebit.shangchen;
import com.umeng.message.entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.message.PushAgent;
import com.umeng.message.UHandler;
import com.umeng.message.UTrack;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class Myapplication extends Application {

	private static final String TAG = Myapplication.class.getName();

	private PushAgent mPushAgent;

	@Override
	public void onCreate() {
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.setDebugMode(true);
		
		// 如果想使用SDK提供的通知展示消息， 但是只是想自定义点击行为， 可以调用下面的接口。
		UHandler notifyClickHandler = new UHandler() {

			@Override
			public void handleMessage(Context context, UMessage msg) {
				Log.d(TAG, "add your custom message handling logic here: "
						+ msg.custom);
				try {
					JSONObject json = new JSONObject(msg.custom);
					Log.d(TAG, "json="+json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		// mPushAgent.setNotificationClickHandler(notifyClickHandler);

		// 如果希望自定义消息的处理形式， 可以下面使用接口。 消息处理完全交给应用处理。SDK 不展示通知栏。此种方式不能统计消息打开动作。
		UHandler handler = new UHandler() {
			@Override
			public void handleMessage(Context context, UMessage msg) {
				Log.d(TAG, "add your custom message handling logic here: "
						+ msg.custom);
				try {
					
					UTrack.getInstance(context).trackMsgClick(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		//mPushAgent.setMessageHandler(handler);
	}
	
}
