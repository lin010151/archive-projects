package com.fm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.fm.other.Client;
import com.fm.other.DownloadFile;
import com.fm.other.ThreadPool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


/**后台监听服务*/
public final class ClientService extends Service 
{
	private final IBinder mBinder = new LocalBinder();
	private volatile int 	whatRecv = 0;	//0为接收文字，1为接收文件
	private Intent intent = new Intent("com.example.communication.RECEIVER"); 
	
	
	@Override
    public void onCreate() 
    {
        super.onCreate();
        
        if (whatRecv == 0)	//接收文字
		{
			ExecutorService fixedThreadPool = ThreadPool.getThreadPool();	
			fixedThreadPool.execute(new Runnable() 
			{ 
		        @Override
		        public void run() 
		        {	
		        	
		        	while (ThreadPool.isRuning)
			        {
		        		String recvStr = Client.receiveText();
		        		
		        		// 以广播的形式把接收到的文字发送出去
		        		intent.putExtra("receive", recvStr);  
		                sendBroadcast(intent); 
		   
			        }
		        	
		        	
		        }
		    });
		}
		else if(whatRecv == 1)	//接收文件
		{
	        ExecutorService fixedThreadPool = ThreadPool.getThreadPool();	
			fixedThreadPool.execute(new Runnable() 
			{ 
		        @Override
		        public void run() 
		        {	
		        	Socket client = Client.getServerSocket();
		        	while (ThreadPool.isRuning)
			        {
			        	
						try 
						{
							BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
							try
							{
								String strlen= in.readLine();
								
								Log.i("SocketListen", strlen);
								final int len = Integer.parseInt(strlen);
								
								
								DownloadFile downFile = new DownloadFile(client, "sdcard/MyRecvVoice", len);
						
								downFile.start();
								
								
								//线程执行完之前不返回
								while (downFile.isAlive())
									;//null    										
								
								
								
								/*
	    			        	Message msg = handler.obtainMessage();
	    			        	msg.what = MSG_RECEIVE_SUCCEED; 
	    						handler.sendMessage(msg);	
	    						*/		
							}
							catch (IOException e)
							{
								Log.i("SocketListen", "no recv");
							}
							
							
							
							
						} 
						catch (IOException e) 
						{
							
						}	 
			        }
		        	
		        	
		        }
		    });
		}
    }
	
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return mBinder;
	}

	
	public class LocalBinder extends Binder
	{
		public ClientService getService()
		{
			return ClientService.this;
		}
	}
}
