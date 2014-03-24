package com.evebit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.evebit.shangchen.HomeActivity;
import com.evebit.shangchen.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoingAdapter extends BaseAdapter {

	
	private Activity activity;  
    private ArrayList<HashMap<String, String>> data;  
    private static LayoutInflater inflater=null;  
    public ImageLoader imageLoader; //用来下载图片的类，后面有介绍  
    
    public DoingAdapter(Activity a,ArrayList<HashMap<String, String>> d){
    	activity = a;
    	data = d;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	imageLoader = new ImageLoader(activity.getApplicationContext());
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
			vi = inflater.inflate(R.layout.doing_listview, null);
		
		
			LinearLayout layout = (LinearLayout)vi.findViewById(R.id.doing_listview_bg);
			TextView title = (TextView)vi.findViewById(R.id.doing_listview_Text_Title);
			TextView content = (TextView)vi.findViewById(R.id.doing_listview_Text_Content);
			ImageView imageViews = (ImageView)vi.findViewById(R.id.doing_listview_Image);
			
			HashMap<String, String> song = new HashMap<String, String>();
			
			song = data.get(position);

			if (position%2 == 0) {
				layout.setBackgroundResource(R.drawable.listview_bg2);
			}
			else {
				layout.setBackgroundResource(R.drawable.listview_bg1);
			}
			title.setText(song.get(HomeActivity.KEY_TITLE));
			content.setText(song.get(HomeActivity.KEY_CREATED));
			imageLoader.DisplayImage(song.get(HomeActivity.KEY_IMGURL), imageViews);
			return vi;
			
		
		
	}

}
