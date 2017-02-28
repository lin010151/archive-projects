package com.fm.other;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import android.util.Log;


/**接收文件线程*/
public final class DownloadFile extends Thread
{
	private Socket	m_client;		//服务器的socket
    private int		m_fileLen;
    private String	m_savePath = "sdcard/MyRecvVoice";		// 接收到的文件的保存路径
    
    
    /**
     * 
     * @param serverSocket 服务器的socket
     * @param savePath 接收到的文件的保存路径
     * @param fileLength 文件的长度
     */
	public DownloadFile(Socket serverSocket, String savePath, int fileLength)
	{
		m_client = serverSocket;  
	    m_fileLen = fileLength;
	    m_savePath = savePath;
	}
	
	
	/**线程运行*/
    public void run()
    {   	
    	if(m_client == null)
    	{
    		return;
    	}
    	
    	try
    	{
    		
			InputStream inputStream = m_client.getInputStream();
			
			
			byte[] buffer = new byte[1024];
	    	
	    	int readCount = 0;
	    	int count=0;
	    	
	    	// 创建目录（不存在则创建）  
	    	File file = new File(m_savePath + "/");		// TODO "/"的作用？  
	        if (!file.exists())
	        {  
	            file.mkdirs();  
	        }  
	       
	    	
	    	
	        Log.d("gdie", "循环");
	    	BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(new File("sdcard/MyRecvVoice/123.wav")));
	    	while (ThreadPool.isRuning) 
	    	{ 
	    		readCount = inputStream.read(buffer);
	    		count = count+readCount;
	    		fo.write(buffer, 0,readCount);
	    		//System.out.println("recv len " + String.valueOf(count)+"readCount "+String.valueOf(readCount));
	    		Log.i("_TAG", "recv len"+ String.valueOf(count)+"readCount "+String.valueOf(readCount));
	    		if (count >= m_fileLen)
		    		break;
	    	}
	    	
	    	Log.d("gdie", "接收成功");
	    	
	    
	    	fo.flush();
	    	fo.close();
	    	return;
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	Log.d("gdie", "接收失败");
    
    }
}
