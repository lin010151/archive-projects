package com.fm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**客户端类，负责与服务器的交互*/
public class Client 
{
	private static String	m_serverIp = "10.0.57.160";		//服务器的IP
	private static int		m_port = 1234; 					//服务器的端口
	private static Socket	m_socket = null;				//连接服务器的socket
	private static boolean	m_haveConnect;					//是否连接上服务器
	
	
	/**获取服务器的socket*/
	public static Socket getServerSocket()
	{
		return m_socket;
	}
	
	
	/**获取服务器IP*/
	public static String getServerIp()
	{
		return m_serverIp;
	}
	
	
	/**获得服务器端口号*/
	public static int getServerPort()
	{
		return m_port;
	}
	
	
	/**
	 * 设置服务器的IP和端口号，并且连接服务器
	 * @param serverIp 服务器的IP地址
	 * @param port 服务器的端口号
	 * @return 连接成功返回true，连接失败（出错、异常）返回false
	 * @注意 阻塞
	 */
	public static boolean connect(String serverIp, int port)
	{
		m_serverIp = serverIp;
		m_port = port;
		
		try 
		{	
			m_socket = new Socket(m_serverIp, m_port);	
			//sendText("android"); TODO
		} 
		catch (UnknownHostException e)
		{
			App.log("fm", "UnknownHostException");
			return false;
		} 
		catch (IOException e) 
		{
			App.log("fm", "IOException");
			
			return false;
		}
		
		
		m_haveConnect = true;
		return true;
		
	}
	
	
	//Client.sendFile("sdcard/123.png");
	/**
	 * 发送文件给服务器
	 * @param path 要发送的文件的完整路径
	 * @return 发送成功返回true，发送失败返回false
	 * @注意 阻塞
	 */
	public static boolean sendFile(String path)
	{
		if (m_socket == null || path == null)
		{
			return false;
		}
		
	    File file = new File(path);	
	 
    	if (m_socket.isOutputShutdown())
    	{
    		
    		try 
    		{
				m_socket.setKeepAlive(true);
			} 
    		catch (SocketException e) 
    		{
				e.printStackTrace();
				App.log("客户端已关闭");
			}
    	}
    	FileInputStream reader = null;
    	FileInputStream readLen = null;
  
        byte[] buf = null;
        
        try
        {
        	OutputStream outPut = m_socket.getOutputStream();
       
        	
        	ByteArrayOutputStream ow = new ByteArrayOutputStream();
        	
        	int count =0;
        	int buffersize= 256;
        	int fileLen=0;
        	buf = new byte[buffersize];
        	while ((count = readLen.read(buf)) > 0)
        	{
        		fileLen = fileLen + count;
        	}
        	String strLen =String.valueOf(fileLen);
        	strLen = strLen+"\n";
        	App.log(strLen);

        	byte[] buffLen = strLen.getBytes();
        	byte[] buffL = new byte[50];
        	for (int i = 0; i < 50; i++)
        	{
        		if (i < buffLen.length)
        		{
        			buffL[i] = buffLen[i];
        		}
        		else
        		{
        			buffL[i] = 0;
        		}
        	}
        	outPut.write(buffL);
        	outPut.flush();
        	count = 0;
      
        	
        	reader = new FileInputStream(file);
        	while ((count = reader.read(buf)) > 0) 
        	{
        		ow.write(buf, 0, count);
        		
        	}  
        	
        	outPut.write(ow.toByteArray());
        	outPut.flush();
       
        	App.log("文件发送完成！！！");
        
        
        }
        catch(Exception e)
        {
        	App.log_e("socket执行异常：" + e.toString());
        }
        finally 
        {
        	try 
        	{  
                // 结束对象  
                buf = null;           
                reader.close();  
            } 
        	catch (Exception e) 
        	{  
  
            }  
        }
	    
	    
	    
		return true;
	}
	
	
	/**
	 * 发送文本给服务器
	 * @param text 要发送的文字
	 * @return 发送成功返回true，发送失败返回false
	 */
	public static boolean sendText(String text)
	{
		if (m_socket == null)
		{
			App.log("发送失败 NULL");
			return false;
		}

		// 获取输出流  
		try 
		{
			byte[] buffer = text.getBytes("UTF8");
			OutputStream ouput = m_socket.getOutputStream();
			ouput.write(buffer);
			
			/*
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

			writer.write(text);  
	        writer.flush();  
	        writer.close();
	        */
	        return true;
		} 
		catch (Exception e) 
		{
			App.log("发送异常：" + e.toString());
			return false;
		}  
       
        
	}
	
	
	/**
	 * 从服务器接收文本
	 * @return 返回接收到的文本，
	 * 			接收过程中若服务器关闭，返回"服务器断开"，
	 * 			3秒内收不到服务器发来的文本或其他异常情况，则返回nulll
	 */
	public static String receiveText()
	{
		if (m_socket == null)
		{
			App.log("服务socket为空");
			return null;
		}

		try 
		{
			if (m_socket.isClosed())
			{
				App.log("服务socket已关闭");
				return null;
			}
			
			int len;
			final int LEN = 10 * 1024;
			byte[] buf = new byte[LEN];
			String line = "";
			InputStream input = m_socket.getInputStream();
			
			m_socket.setSoTimeout(3000);				//最长阻塞3秒
			while ((len = input.read(buf)) == LEN)		//阻塞，TODO 注意刚好的bug
			{
				line += new String(buf, 0, len);
			}
			
			if (len == -1)
			{
				App.log("服务器断开");
				return "服务器断开";	//TODO 估计是服务器断开
			}
			else
			{
				line += new String(buf, 0, len);
				//App.log("接收到文字" + line);
				return line;
			}
			
		} 
		catch (SocketTimeoutException err)
		{
			App.log("时间异常");
			return null;
		}
		catch (IOException e) 
		{
			App.log("IO异常" + e.toString());
			return null;
		}
		
	}
	
	
	/**关闭连接(关闭连接服务器的socket)*/
	public static void close()
	{
		try 
		{
			m_socket.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	

	/**判断是否已经连接上服务器*/
	public static boolean haveConnect()
	{
		return m_haveConnect;
	}
}
