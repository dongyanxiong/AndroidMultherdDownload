package com.example.androidmultherddownloadthree.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidmultherddownloadthree.R;

/**
 * 适用器，用于listView显示
 * @author miaowei
 *
 */
public class DownLoadAdapter extends BaseAdapter {
	
	/**
	 * 加载指定布局
	 */
	private LayoutInflater mInflater;
	/**
	 * 存入的数据
	 */
	private List<Map<String, String>> data;
	/**
	 * 上下文
	 */
	private Context mContext;
	
	/**
	 * 点击事件
	 */
	private OnClickListener click;
	
	/**
	 * 用于适配listView显示
	 * @param context 上下文
	 * @param data 要显示的数据对象
	 */
	public DownLoadAdapter(Context context,List<Map<String, String>> data) {
		
		this.mContext=context;  
        mInflater = LayoutInflater.from(context);  
        this.data=data; 
	}
	
	/**
	 * 重新刷新数据
	 * @param data 要显示的数据对象
	 */
	public void refresh(List<Map<String, String>> data) {  
        this.data=data;  
        this.notifyDataSetChanged();  
	}

	/**
	 * 设置点击事件
	 * @param click
	 */
	public void setOnclick(OnClickListener click) {  
        this.click=click;  
    } 
	
	public OnClickListener getClick() {  
        return click;  
    }  
	
	
    public void setClick(OnClickListener click) {  
        this.click = click;  
    } 
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Map<String , String> bean = data.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_listview_item, null);
			
			holder = new ViewHolder();
			holder.resouceName = (TextView)convertView.findViewById(R.id.tv_resouce_name);
			holder.startDownload = (Button)convertView.findViewById(R.id.btn_start);
			holder.pauseDownload = (Button)convertView.findViewById(R.id.btn_pause);
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.resouceName.setText(bean.get("name"));
		
		return convertView;
	}
	
	/**
	 * 减少重复加载
	 * @author miaowei
	 *
	 */
	private class ViewHolder {   
        public TextView resouceName;  
        public Button startDownload;  
        public Button pauseDownload;  
        
          
       
    }
}
