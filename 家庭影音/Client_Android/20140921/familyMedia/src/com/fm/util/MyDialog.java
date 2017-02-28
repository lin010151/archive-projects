package com.fm.util;

import com.fm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MyDialog 
{

	/**
	 * 显示一个列表对话框
	 * @param context 上下文
	 * @param title 对话框的标题
	 * @param select 列表上的文字
	 * @param listener 监听 DialogInterface.OnClickListener类型
	 */
	public static void showSelectDialog(Context context, String title, String[] select,
			DialogInterface.OnClickListener listener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(select, listener);
		builder.show();
	}
	
	
	/**
	 * 显示一个 OK/CANCEL 对话框, make sure no keyboard is visible
	 * @param title 标题
	 * @param pMsg 内容
	 */
	public static void showCustomMessage(final Context context, String title, final String pMsg, OnClickListener listener) 
	{
		final Dialog lDialog = new Dialog(context,
				android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.dialog_ok_cancel);
		((TextView) lDialog.findViewById(R.id.dialog_title)).setText(title);
		((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
		((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
		((Button) lDialog.findViewById(R.id.cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks CANCEL
						lDialog.dismiss();
					}
				});
		((Button) lDialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks OK
						
						lDialog.dismiss();
						((Activity) context).finish();
					}
				});
		lDialog.show();

	}
	
	
	/**
	 * it will show the OK dialog like iphone, make sure no keyboard is visible
	 * @param pTitle 对话框的标题
	 * @param pMsg 对话框的内容
	 */
	public static void showCustomMessageOK(Context context, String pTitle, final String pMsg) 
	{
		final Dialog lDialog = new Dialog(context,
				android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.dialog_ok);
		((TextView) lDialog.findViewById(R.id.dialog_title)).setText(pTitle);
		((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
		((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
		((Button) lDialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks OK
						lDialog.dismiss();
					}
				});
 		lDialog.show();

	}
}
