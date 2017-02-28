package com.fm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.fm.util.App;
import com.fm.util.Client;
import com.fm.util.DownloadFile;
import com.fm.util.ThreadPool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


/**后台监听服务*/
public class ClientService extends Service 
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
		        		App.log("接收文字死循环");
		        		String recvStr = Client.receiveText();
		        		if (recvStr != null)
		        		{
		        			App.log("服务接收并发送文字：" + recvStr);
			        		// 以广播的形式把接收到的文字发送出去
			        		intent.putExtra("receive", recvStr);  
			                sendBroadcast(intent); 		        			
		        		}
		        		else
		        		{
		        			App.log("接收到空文字");
		        		}

		   
			        }
		        	
		        	App.log("接收文字死循环退出 isRuning=" + ThreadPool.isRuning);
		        	
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
		        		App.log("接收文件死循环");
			        	
						try 
						{
							BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
							try
							{
								String strlen= in.readLine();
								
								App.log("SocketListen", strlen);

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
								App.log("SocketListen", "no recv");
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
