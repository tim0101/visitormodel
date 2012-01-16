package com.hailang.visitormodel.adapter;

import java.util.List;

import com.hailang.visitormodel.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	private List<PackageInfo> appPakList;
	private LayoutInflater layoutInfla;
	private PackageManager packageManager;
	public AppListAdapter(Context context){
		layoutInfla=LayoutInflater.from(context);
		packageManager=context.getPackageManager();
		this.appPakList=packageManager.getInstalledPackages(0);
	}
	@Override
	public int getCount() {
		return appPakList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return appPakList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInfla.inflate(R.layout.applist_itemt, null);
			viewHolder.icon=(ImageView)convertView.findViewById(R.id.igw_icon);
			viewHolder.appName=(TextView)convertView.findViewById(R.id.txt_appname);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder)convertView.getTag();
		}
		viewHolder.icon.setImageDrawable(packageManager.getApplicationIcon(appPakList.get(position).applicationInfo));
		viewHolder.appName.setText(packageManager.getApplicationLabel(appPakList.get(position).applicationInfo));
		return convertView;
	}
	class ViewHolder{
		ImageView icon;
		TextView appName;
	}
}
