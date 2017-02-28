package com.fm.activity;

import java.util.concurrent.ExecutorService;

import com.fm.adapter.MusicListAdapter;
import com.fm.util.App;
import com.fm.util.Client;
import com.fm.util.Protocol;
import com.fm.util.ThreadPool;
import com.fm.window.SearchPopupWindow;
import com.fm.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**歌曲列表窗口*/
public class MusicActivity extends Activity 
{
	//控件
	private ListView	lvMusic;		//歌曲列表
	private Button		btnBack;
	private Button		btnSearch;
	private TextView	tvInfo;
	private ProgressBar progress;
	//消息
	private final int 	MSG_NET_ERROR = 1;
	
	SearchPopupWindow	searchWindow; //弹出框
	
	String[]			musicList;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);   
        
        //初始化控件
        lvMusic = (ListView) findViewById(R.id.music_lvMusic);
        btnBack = (Button) findViewById(R.id.music_btnBack);
        btnSearch = (Button) findViewById(R.id.music_btnSearch);
        tvInfo = (TextView) findViewById(R.id.music_tvInfo); 
        progress = (ProgressBar) findViewById(R.id.music_progressBar); 
        
        
        btnBack.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		finish();
        	}
        }); 
        
        
        tvInfo.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		progress.setVisibility(View.VISIBLE);
        	}
        }); 
        
        
        //为弹出窗口实现监听类
        OnClickListener  itemsOnClick = new OnClickListener()
        {
    		public void onClick(View v)
    		{
    			String input = searchWindow.getInputText();
    			App.toast(input);
    			searchWindow.dismiss();
    		
    		}
        };
        
        
        searchWindow = new SearchPopupWindow(MusicActivity.this, itemsOnClick);
        

        btnSearch.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
				//显示窗口，设置layout在PopupWindow中显示的位置
				searchWindow.showAtLocation(MusicActivity.this.findViewById(R.id.music_btnSearch), 
						 Gravity.TOP | Gravity.RIGHT, 5, 150); 
        	}
        }); 
        
        
        lvMusic.setOnItemClickListener(new OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int posotion, long arg3) 
            {
            	final String musicName = PlayerActivity.listss[posotion];
            	
                ExecutorService fixedThreadPool = ThreadPool.getThreadPool();
        		fixedThreadPool.execute(new Runnable() 
        		{ 
        	        @Override
        	        public void run() 
        	        {

        	        	String sendStr = Protocol.getSendString(Protocol.PLAY, musicName);
	    	        	if (!Client.sendText(sendStr))
	    	        	{
	    	        		Message msg = handler.obtainMessage();
	    		        	msg.what = MSG_NET_ERROR; 
	    					handler.sendMessage(msg);	
	    	        	}
	    	        	
	    	        	finish();
        	        }
        	    });
            
            }
        });
        
 	
        musicList = PlayerActivity.listss;
        
  		// 加载歌曲列表
  		if (musicList != null)
  		{
  			MusicListAdapter adapter = new MusicListAdapter(MusicActivity.this, 
  					musicList, PlayerActivity.musicName);
  			lvMusic.setAdapter(adapter);
  			int index = MusicListAdapter.getCurIndex();
  			App.log("" + index);
  			lvMusic.setSelection(index);//未实现
  		
  		}
  		else
  		{
  			tvInfo.setVisibility(View.VISIBLE);
  			//TODO红颜色，下划线
  			//TODO刷新按钮
  		}
  		
  		
  	
  		
  		
  		
    }
	
	
	
    
    
	private Handler handler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case MSG_NET_ERROR:
    			App.toast("网络错误");
    			break;
			}
    	}
    };
}
