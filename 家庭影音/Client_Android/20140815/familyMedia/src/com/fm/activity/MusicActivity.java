package com.fm.activity;

import java.util.concurrent.ExecutorService;
import com.fm.adapter.MusicListAdapter;
import com.fm.other.Client;
import com.fm.other.Protocol;
import com.fm.other.ThreadPool;
import com.fm.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


/**歌曲列表窗口*/
public final class MusicActivity extends Activity 
{
	//控件
	private ListView	lvMusic;		//歌曲列表
	//消息
	private final int MSG_NET_ERROR = 1;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);   
        
        //初始化控件
        lvMusic = (ListView) findViewById(R.id.lvMusic);
        

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
        
 	
  		// 加载歌曲列表
  		if (PlayerActivity.listss != null)
  		{
  			MusicListAdapter adapter = new MusicListAdapter(MusicActivity.this, PlayerActivity.listss);
  			lvMusic.setAdapter(adapter);
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
    			Toast.makeText(MusicActivity.this, "网络错误", Toast.LENGTH_LONG).show();
    			break;
			}
    	}
    };
}
