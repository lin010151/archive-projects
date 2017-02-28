package com.fm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**通讯协议类*/
public class Protocol 
{
	/**停止播放*/
	public static final int		STOP			= 1;//尽量不要使用
	/**播放*/
	public static final int		PLAY			= 2;
	/**暂停*/
	public static final int		PAUSE			= 3;
	/**下一首*/
	public static final int		NEXT			= 4;
	/**上一首*/
	public static final int		LAST			= 5;
	/**关闭播放器（关闭服务端）*/
	public static final int		CLOSE_SERVER	= 6;
	/**获得列表*/
	public static final int		GET_LIST		= 7;
	/**设置音量*/
	public static final int		SET_SOUND 		= 8;
	/**电脑关机*/
	public static final int		SHUT_DOWN 		= 9;
	/**进度的百分比*/
	public static final int		PROGRESS 		= 10;
	/**客户端退出*/
	public static final int		CLOSE_CLIENT 	= 11;
	/**获得初始化数据*/
	public static final int		INIT 			= 12;
	/**减小音量*/
	public static final int		TURN_DOWN 		= 13;
	/**增大音量*/
	public static final int		TURN_UP 		= 14;
//收到的协议	
	/**收到列表*/
	public static final int		LIST 			= 101;
	/**歌曲名*/
	public static final int		NAME 			= 102;
	/**音量大小*/
	public static final int		SOUND			= 103;
	
	
	/**消息的附加内容*/	
	static String m_content = null;
	
	
	/**根据命令获得要发送的字符串*/
	public static String getSendString(int command)
	{
		switch (command)
		{
		case PLAY:
			return "cmd<play>";
			
		case STOP:
			return "cmd<stop>";
			
		case PAUSE:
			return "cmd<pause>";
			
		case NEXT:
			return "cmd<next>";
			
		case LAST:
			return "cmd<last>";
			
		case CLOSE_SERVER:
			return "cmd<close>";
		
		case CLOSE_CLIENT:
			return "cmd<close_client>";
			
		case GET_LIST:
			return "cmd<get_list>";
			
		case SHUT_DOWN:
			return "cmd<shut_down>";
			
		case INIT:
			return "cmd<init>";
		
		case TURN_UP:
			return "cmd<turn_up>";
			
		case TURN_DOWN:
			return "cmd<turn_down>";
		}
		
		return null;
	}
	
	
	/**根据命令获得要发送的字符串*/
	public static String getSendString(int command, String content)
	{
		switch (command)
		{
		case PROGRESS:
			return "cmd2<progress><" + content + ">";
			
		case PLAY:
			return "cmd2<play><" + content + ">";
		}
		return null;
	}
	
	
	/**根据接收到的文字获得要执行的命令*/
	public static int getMommand(String recvStr)
	{
		// if (recvStr.startsWith("cmd2")) 不能准确？？？？？？？？？？？？
		if (recvStr.startsWith("cd"))
		{
			Pattern pat = Pattern.compile("cd<([\\d\\D]+?)><([\\d\\D]+?)>");  
			Matcher mat = pat.matcher(recvStr);
			if (mat.find())
			{
				switch (mat.group(1))
                {
                    case "list":
                    	m_content = mat.group(2);
                        return LIST;
                        
                    case "progress":
                    	m_content = mat.group(2);
                    	return PROGRESS;
                    	
                    case "name":
                    	m_content = mat.group(2);
                    	return NAME;
                    	
                    case "sound":
                    	m_content = mat.group(2);
                    	return SOUND;
                    	
                }
				
			}
			else
			{
				return 0;
			}
		}
		else if (recvStr.startsWith("cmd"))
        {
			Pattern pat = Pattern.compile("cmd<([\\d\\D]+?)>");  
			Matcher mat = pat.matcher(recvStr);
			if (mat.find())
			{
				switch (mat.group(1))
                {
                    case "play":
                        return PLAY;
                    case "stop":
                        return STOP;
                    case "pause":
                        return PAUSE;
                    case "next":
                        return NEXT;
                    case "last":
                        return LAST;
                }
				
			}
			else
			{
				return 0;
			}
        }
		App.log("解析错误的命令：" + recvStr);

		return 0;
	}
	
	
	/**获得接收到的消息的附加内容*/
	public static String getContent()
	{
		return m_content;
	}
	
	
	/**获得分隔符*/
	public static String getSeparator()
	{
		return "abcd";		// TODO "|"不能？？？
	}
}
