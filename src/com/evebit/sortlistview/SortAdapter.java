package com.evebit.sortlistview;

import java.util.List;

import com.evebit.shangchen.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;


/**
 * author tianbing*/
public class SortAdapter extends BaseAdapter implements SectionIndexer {

	private List<SortModer> list = null;
	private Context mContext ;
	
	
	
	//构造方法  的参数 是必须的
	public SortAdapter(Context mContext, List<SortModer> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	//考虑到数据会更新  需要一个调用方法来更新数据
	public void updateListView(List<SortModer> list){
		this.list=list;
		//调用一个更新数据的方法
		notifyDataSetChanged();
		
	}
	

	@Override
	public int getCount() {
	
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final SortModer mContent = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sort_list_item, null);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.sort_title);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.sort_catalog);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getFirstletter());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		
		return convertView;
	}
	
	
	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}
	
	
/*
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getFirstletter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
		
	 
	 /**正则表达式  用以判断首字母时候是英文，不是则归类了 #*/
	 
	 private String getAlpha(String str) {
			String  sortStr = str.trim().substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortStr.matches("[A-Z]")) {
				return sortStr;
			} else {
				return "#";
			}
		}

	 
	 /**
		 * 根据ListView的当前位置获取分类的首字母的Char ascii值
		 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getFirstletter().charAt(0);
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}
