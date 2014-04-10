package com.evebit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.shangchen.HomeActivity;
import com.evebit.shangchen.R;

import android.R.integer;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPhoneAdapter extends BaseAdapter {

	
	private Activity activity;  
    private ArrayList<HashMap<String, Object>> data;  
    private static LayoutInflater inflater=null;  
    public ImageLoader imageLoader; //用来下载图片的类，后面有介绍  
    
    public MyPhoneAdapter(Activity a,ArrayList<HashMap<String, Object>> d){
    	activity = a;
    	data = d;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //	imageLoader = new ImageLoader(activity.getApplicationContext());
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		if (convertView == null) 
			vi = inflater.inflate(R.layout.contact_listview_cell, null);
		
		
			LinearLayout layout = (LinearLayout)vi.findViewById(R.id.contact_listview_bg);
			TextView title = (TextView)vi.findViewById(R.id.contact_listview_Text_Title);
			TextView phone = (TextView)vi.findViewById(R.id.contact_listview_Text_Phone);
			TextView content = (TextView)vi.findViewById(R.id.contact_listview_Text_Content);
			
			HashMap<String, Object> song = new HashMap<String, Object>();
			
			song = data.get(position);

			if (position%2 == 0) {
				layout.setBackgroundResource(R.drawable.listview_bg2);
			}
			else {
				layout.setBackgroundResource(R.drawable.listview_bg1);
			}
			title.setText(String.valueOf(song.get(HomeActivity.KEY_TITLE)));
			phone.setText(String.valueOf(song.get(HomeActivity.KEY_CANSHU1)));
			content.setText(String.valueOf(song.get(HomeActivity.KEY_CANSHU2)));
			//imageLoader.DisplayImage(song.get(HomeActivity.KEY_IMGURL), imageViews);
			return vi;
			
		
		
	}

}
