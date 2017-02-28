package com.fm.service;

import java.util.concurrent.ExecutorService;

import com.fm.util.App;
import com.fm.util.ThreadPool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**后台播放影音服务*/
public class MusicService extends Service 
{
	@Override
    public void onCreate() 
    {
        super.onCreate();
        
        ExecutorService fixedThreadPool = ThreadPool.getThreadPool();	
		fixedThreadPool.execute(new Runnable() 
		{ 
	        @Override
	        public void run() 
	        {	
	        	try
	        	{
	        		Thread.sleep(1000);
	        	}
	        	catch (Exception err)
	        	{
	        		
	        	}
	        	
	        	App.log("播放影音");

	        }
	    });
    }
	
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}

}
