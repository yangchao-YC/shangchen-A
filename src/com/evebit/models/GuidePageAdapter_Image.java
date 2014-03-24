package com.evebit.models;

import java.util.ArrayList;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class GuidePageAdapter_Image extends PagerAdapter {
	private ArrayList<View> pageViews;

	public GuidePageAdapter_Image(ArrayList<View>pageViews){
		this.pageViews = pageViews;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		// ((ViewPager) container).removeView(pageViews.get(position)); 
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		//((ViewPager) container).addView(pageViews.get(position));  
        //return pageViews.get(position);  
		try {
    		((ViewPager) container).addView(pageViews.get(position%pageViews.size()),0);  
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return pageViews.get(position%pageViews.size());
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		// TODO Auto-generated method stub
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startUpdate(View container) {
		// TODO Auto-generated method stub

	}
}

