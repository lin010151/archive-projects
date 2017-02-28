package com.fm.window;

import com.fm.R;
import com.fm.activity.QTCodeActivity;
import com.fm.activity.SettingActivity;
import com.fm.util.MyDialog;

import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


/**菜单弹出窗口*/
public class MenuPopupWindow extends PopupWindow 
{
	private View			mMenuView;
	
	private LinearLayout	llAbout;
	private LinearLayout	llShare;
	private LinearLayout	llScan;
	private LinearLayout	llExit;		// 退出按钮
	private LinearLayout	llSetting;

	
	/**分别是扫描监听   ，退出监听*/
	public MenuPopupWindow(final Activity context, OnClickListener scanListener, OnClickListener exitListener) 
	{
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dialog_menu, null);
		

		int w = context.getWindowManager().getDefaultDisplay().getWidth();

		//初始化控件
		llAbout = (LinearLayout) mMenuView.findViewById(R.id.menu_llAbout); 
		llShare = (LinearLayout) mMenuView.findViewById(R.id.menu_llShare); 
		llScan = (LinearLayout) mMenuView.findViewById(R.id.menu_llScan); 
		llExit = (LinearLayout) mMenuView.findViewById(R.id.menu_llExit); 
		llSetting = (LinearLayout) mMenuView.findViewById(R.id.menu_llSetting); 
		
		//取消按钮
		llExit.setOnClickListener(exitListener);
		
		
		llScan.setOnClickListener(scanListener);
		
		
		llSetting.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(context, SettingActivity.class);
				context.startActivity(intent);
				
				dismiss();
			}
		});
		
		
		llAbout.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				showAbout(context);
				
                MenuPopupWindow.this.dismiss();
			}
		});
		
		
		llShare.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(context, QTCodeActivity.class);
				context.startActivity(intent);
				
				/*
				String[] str = { "QQ空间", "微信" };
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch (which)
						{
						case 0:	
							break;
							
						case 1:
						
							break;
							
						case 2:
							break;
						}
					}
				};
				MyDialog.showSelectDialog(context, null, str, listener);
				*/
				dismiss();
			}
		});
		
		

		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		this.setWidth(w/2+50);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.mystyle);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() 
		{
			
			public boolean onTouch(View v, MotionEvent event) 
			{
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (y < height)
					{
						dismiss();
					}
				}				
				return true;
			}
		});

	}

	
	private void showAbout(final Context context)
	{
		View CustomView;
		final LayoutInflater inflater=((Activity) context).getLayoutInflater();
		AlertDialog.Builder builder2=new AlertDialog.Builder(context);
		CustomView=inflater.inflate(R.layout.dialog_about, null);

		Builder builder = builder2.setView(CustomView);
		
	
		final AlertDialog dialog=builder.show();
        //点击屏幕外侧，dialog不消失
		dialog.setCanceledOnTouchOutside(false);

		
		ImageButton customviewtvimgCancel=(ImageButton)CustomView.findViewById(R.id.customviewtvimgCancel);
		customviewtvimgCancel.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				
			    dialog.dismiss();
			}
		});
		
		
	};

}
