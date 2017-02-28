package com.fm.activity;

import java.util.ArrayList;
import java.util.List;

import com.fm.R;
import com.fm.adapter.GuideAdapter;
import com.fm.util.UserData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;


/**引导窗口（第一次打开该应用时的引导界面）*/
public class GuideActivity extends Activity 
{
	private static final int TO_THE_END = 0;
	private static final int LEAVE_FROM_END = 1;

	private int[] ids = { R.drawable.guide_pic_1, R.drawable.guide_pic_2,
			R.drawable.guide_pic_3, R.drawable.guide_pic_4};
	
	private List<View> guides = new ArrayList<View>();
	//控件
	private ViewPager	pager;
	private ImageView	open;
	private ImageView	curDot;
	private LinearLayout	layout;	
	
	private int			offset;// λ����
	private int			curPos = 0;// ��¼��ǰ��λ��


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		// 初始化控件
		layout = (LinearLayout) findViewById(R.id.guide_layout);
		curDot = (ImageView) findViewById(R.id.guide_ivCurDot);
		open = (ImageView) findViewById(R.id.guide_ivExperience);
		
		// 添加所有圆点
		for (int i = 0; i < ids.length; i++)
		{
			ImageView imageView = new ImageView(GuideActivity.this);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			imageView.setImageResource(R.drawable.guide_dot1);
			imageView.setLayoutParams(params);
			//iv.setScaleType(ScaleType.FIT_XY);
			layout.addView(imageView);
		}
		

		for (int i = 0; i < ids.length; i++)
		{
			ImageView iv = new ImageView(this);
			iv.setImageResource(ids[i]);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			guides.add(iv);
		}

		
		
		curDot.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() 
		{
			public boolean onPreDraw() 
			{
				offset = curDot.getWidth();
				return true;
			}
		});

		
		GuideAdapter adapter = new GuideAdapter(guides);
		pager = (ViewPager) findViewById(R.id.contentPager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() 
		{

			public void onPageSelected(int arg0) 
			{
				moveCursorTo(arg0);
				if (arg0 == ids.length - 1) 
				{
					// 最后一页
					handler.sendEmptyMessageDelayed(TO_THE_END, 500);
				} 
				else if (curPos == ids.length - 1) 
				{
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}
				curPos = arg0;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			public void onPageScrollStateChanged(int arg0) {}
			
		});
		
		
		open.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
                Intent intent = new Intent(GuideActivity.this, ConnectActivity.class);  
                startActivity(intent);  
                overridePendingTransition(R.anim.drop_in, R.anim.fade_out);
                finish();  
				//标记，不再出现引导界面
                UserData.markFirstRun();
			}
		});
	}

	
	/**
	 * 移动小圆点
	 * @param position ֵ
	 */
	private void moveCursorTo(int position) 
	{
		TranslateAnimation anim = new TranslateAnimation(offset*curPos, offset*position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		curDot.startAnimation(anim);
	}

	
	private Handler handler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			if (msg.what == TO_THE_END)
			{
				open.setVisibility(View.VISIBLE);
			}
			else if (msg.what == LEAVE_FROM_END)
			{
				open.setVisibility(View.GONE);
			}
		}
	};
	
	
}