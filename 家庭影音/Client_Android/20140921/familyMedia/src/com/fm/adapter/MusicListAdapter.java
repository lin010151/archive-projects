package com.fm.adapter;

import com.fm.util.SDCardFile;
import com.fm.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**歌曲列表的适配器*/
public class MusicListAdapter extends BaseAdapter 
{
	private String[]	m_title;
	private Context		mContext;  
	private String		m_curMusic = "";
	
	private static int			m_curIndex = 0;			//当前歌曲的序号
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
    
    
    public MusicListAdapter(Context context, String[] title, String curMusic) 
    {  
        mContext = context;  
        m_title = title;
        m_curMusic = curMusic;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music, null);  
            
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
        
       
        String name = SDCardFile.getFileNameWithoutExtension(m_title[position]);
        
        holder.tvTitle.setText(name);  
        
        if (name.equals(m_curMusic))
        {
        	holder.tvTitle.setTextColor(Color.RED); 
        	m_curIndex = position;
        }
        else
        {
        	holder.tvTitle.setTextColor(Color.BLACK); 
        }
      
        
        holder.tvText.setText("");
      
        return convertView; 
  
    }  
 
    
    /**用于优化的类*/
    public class ViewHolder 
    { 
    	public ImageView	image;
        public TextView		tvTitle;
        public TextView		tvText;
    } 
    
    
    public static int getCurIndex()
    {
    	return m_curIndex;
    }

}
