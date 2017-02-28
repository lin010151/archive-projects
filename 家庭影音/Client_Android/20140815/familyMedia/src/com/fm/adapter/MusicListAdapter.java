package com.fm.adapter;

import com.fm.other.SDCardFile;
import com.fm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**歌曲列表的适配器*/
public final class MusicListAdapter extends BaseAdapter 
{
	private String[]	m_title;
	private Context		mContext;  
	
	
	/**
	 * 
	 * @param context
	 * @param title	歌曲的名字
	 */
    public MusicListAdapter(Context context, String[] title) 
    {  
        mContext = context;  
        m_title = title;
    }  
 
    
    @Override
    public int getCount() 
    {  
        return m_title.length;  
    }  
 
    
    @Override  
    public boolean areAllItemsEnabled() 
    {  
        return false;  
    }  
 
    
    @Override
    public Object getItem(int position) 
    {  
        return position;  
    }  
 
    
    @Override
    public long getItemId(int position) 
    {  
        return position;  
    }  
 
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {  
    	
    	ViewHolder holder = null; 
    	
        if (convertView == null) 
        { 
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_music_list, null);  
            
            holder = new ViewHolder(); 
            holder.image = (ImageView) convertView.findViewById(R.id.music_image); 
            holder.tvTitle = (TextView) convertView.findViewById(R.id.music_title); 
            holder.tvText = (TextView) convertView.findViewById(R.id.music_text);  
            
            convertView.setTag(holder); 
        } 
        else 
        { 
            holder = (ViewHolder) convertView.getTag(); 
        } 
        
       
        holder.tvTitle.setText(SDCardFile.getFileNameWithoutExtension(m_title[position]));  
        holder.tvText.setText("");
      
        return convertView; 
      
    	/*
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music_list, null);  
        ImageView iamge = (ImageView) view.findViewById(R.id.music_image);  
        TextView title = (TextView) view.findViewById(R.id.music_title);  
        TextView text= (TextView) view.findViewById(R.id.music_text);  
      
        //只显示歌曲的文件名，不显示完整路径
        title.setText(SDCardFile.getFileNameWithoutExtension(m_title[position]));  
        text.setText("");
        //iamge.setImageResource(R.drawable.music);  
        return view;  
       */
    }  
 
    
    /**用于优化的类*/
    public final class ViewHolder 
    { 
    	public ImageView	image;
        public TextView		tvTitle;
        public TextView		tvText;
    } 

}
