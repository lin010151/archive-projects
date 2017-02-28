package com.fm.window;

import com.fm.R;
import com.fm.util.App;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;


/**搜索弹出窗口*/
public class SearchPopupWindow extends PopupWindow 
{
	private ImageButton		btnSearch;		// 搜索按钮
	private ImageButton		btnBack;		//返回
	private EditText		txtKeyword;		// 关键词输入框
	private View			mMenuView;
	
	
	/**
	 * 
	 * @param context
	 * @param itemsOnClick 搜索按钮的监听事件
	 */
	public SearchPopupWindow(final Activity context, OnClickListener itemsOnClick) 
	{
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dialog_search, null);
		
		btnSearch = (ImageButton) mMenuView.findViewById(R.id.search_btnSearch);
		txtKeyword = (EditText) mMenuView.findViewById(R.id.search_txtKeywords);
		
		
		btnSearch.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				String keyword = txtKeyword.getText().toString().trim();
				
				if (keyword.equals(""))
				{
					App.toast("请输入关键词");
					return;
				}
				
				
				
				
				SearchPopupWindow.this.dismiss();
			}
		});
		

		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		//设置按钮监听
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置弹出窗体的属性
		this.setWidth(w - 20);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
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
				
				int height = mMenuView.findViewById(R.id.searchL1).getTop();//TODO
				int y = (int) event.getY();
				if (event.getAction()==MotionEvent.ACTION_UP)
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

	
	/**获得输入框输入的内容*/
	public String getInputText()
	{
		return txtKeyword.getText().toString().trim();
	}
}
