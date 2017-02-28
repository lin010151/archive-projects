package com.fm.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**负责处理所有需要永久保存的数据*/
public final class UserData 
{
	/**没有太大意义的String*/
	private static final String USER_NAME = "mrsoft";

	
	/**是否第一次打开*/
	public static boolean firstRun(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean("firstRun", true);
	}
	
	
	/**标记非第一次打开（不再出现引导界面）*/
	public static void markFirstRun(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();        				
		editor.putBoolean("firstRun", false);
		editor.commit();
	}
	

	/**保存IP*/
	public static void saveIP(Context context, String ip)
	{
		SharedPreferences sp = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit(); 
		editor.putString("ip", ip);
		editor.commit();
	}
	
	
	/**获取保存的IP*/
	public static String getIP(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
		return sp.getString("ip", null);
	}
}
