package com.fm.activity;

import com.fm.R;
import com.fm.util.Const;
import com.fm.util.UserData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;


/**欢迎窗口*/
public class WelcomeActivity extends Activity 
{
	private Animation	animation;
	private View 		view;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		view = View.inflate(this, R.layout.activity_welcome, null);
		setContentView(view);
		
		Const.context = this.getApplicationContext();
		UserData.init(WelcomeActivity.this);
		
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() 
		{
			@Override
			public void onAnimationStart(Animation arg0) {}

			@Override
			public void onAnimationRepeat(Animation arg0) {}

			@Override
			public void onAnimationEnd(Animation arg0) 
			{
				//在欢迎界面停留0.5秒
				new Handler().postDelayed(new Runnable() 
				{            
		            @Override  
		            public void run() 
		            {  
		            	if (UserData.firstRun())	//如果第一次打开应用
		            	{
		            		//跳转到引导界面
		                    Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);  
		                    startActivity(intent);  
		                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		                    finish();  
		            	}
		            	else
		            	{
		            		//跳转到主界面
		                    Intent intent = new Intent(WelcomeActivity.this, ConnectActivity.class);  
		                    startActivity(intent);  
		                    finish();  
		            	}
		            	
		            }  
		        }, 500);
				
				
			}
		});
		
		
		
	
	}
}
