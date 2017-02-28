package com.fm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**负责处理所有需要永久保存的数据*/
public class UserData 
{
	private static SharedPreferences	m_sp;		// 全局唯一的
	private static Context				m_context;
	
	
	/**
	 * 初始化该类
	 * @注意 使用该类其他成员函数前必须调用该函数，
	 * 所以最好在第一个Activity的开头就调用该函数
	 */
	public static void init(Context context)
	{
		m_context = context;
		m_sp = m_context.getSharedPreferences("mrsoft", Context.MODE_PRIVATE);
	}
	

	private static void putBoolean(String name, boolean value)
	{
		Editor editor = m_sp.edit();        				
		editor.putBoolean(name, value);
		editor.commit();
	}
	
	
	private static void putString(String name, String value)
	{
		Editor editor = m_sp.edit();        				
		editor.putString(name, value);
		editor.commit();
	}
	
	
	/**是否第一次打开*/
	public static boolean firstRun()
	{
		return m_sp.getBoolean("firstRun", true);
	}
	
	
	/**标记非第一次打开（不再出现引导界面）*/
	public static void markFirstRun()
	{
		putBoolean("firstRun", false);
	}
	

	/**保存IP*/
	public static void saveIP(String ip)
	{
		putString("ip", ip);
	}
	
	
	/**获取保存的IP*/
	public static String getIP()
	{
		return m_sp.getString("ip", null);
	}
	
	
	/**保存端口号*/
	public static void savePort(String port)
	{
		App.log("保存端口号" + port);
		putString("port", port);
	}
	
	
	/**获取端口号*/
	public static String getPort()
	{
		return m_sp.getString("port", null);
	}
}
