package com.fm.util;

import com.fm.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class App 
{
	/**
	 * 显示一个自定义的Toast
	 * @param text 要显示的文本
	 */
	public static void toast(String text)
	{
		if (text == null)
		{
			return;
		}
		
		Context context = Const.context;
		
		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		View layout = View.inflate(context, R.layout.view_toast, null);  
		((TextView)layout.findViewById(R.id.text0)).setText(text);
		t.setView(layout);
		t.show();
	}
	
	
	/**在Logcat显示*/
	public static void log_e(String msg)
	{
		log("_EXCEPTION", msg);
	}
	
	
	/**在Logcat显示*/
	public static void log(String msg)
	{
		log("_TAG", msg);
	}
	
	/**在Logcat显示*/
	public static void log(Exception e)
	{
		log("_EXCEPTION", e.getMessage() + e.toString());
	}
	
	
	/**在Logcat显示*/
	public static void log(String tag, String msg)
	{
		if (Const.debug)
		{
			//msg为空时不会显示
			if (msg.equals("") || msg == null)
			{
				msg = "  ";
			}
			
			if (tag.equals(""))
			{
				tag = "_TAG";
			}
			
			Log.d(tag, msg);
		}
		
		
	}
	
	
	/**
	 * 判断是否有网络连接（数据连接和wifi连接）
	 * @param context 当前上下文
	 * @return 连接返回true，未连接返回false
	 * @注意 模拟器上该函数返回false
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		if (context != null) 
    	{ 
    		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
    		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
    		if (mNetworkInfo != null) 
    		{ 
    			return mNetworkInfo.isAvailable(); 
    		} 
    	} 
    	
    	return false; 
	}
}
