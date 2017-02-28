package com.fm.activity;

import com.fm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**欢迎窗口*/
public final class WelcomeActivity extends Activity 
{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);  
        
        
        //在欢迎界面停留0.7秒
        new Handler().postDelayed(new Runnable() 
  		{            
              @Override  
              public void run() 
              {  
            	  if (false)
            	  //if (UserData.firstRun(WelcomeActivity.this))	//如果第一次打开应用
              	{
              		//跳转到引导界面
                      Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);  
                      startActivity(intent);  
                      overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
                      finish();  
              	}
              	else
              	{
              		//跳转到主界面
                      Intent intent = new Intent(WelcomeActivity.this, ConnectActivity.class);  
                      startActivity(intent);  
                      overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
                      finish();  
              	}
              	
              }  
          }, 700);
    }
}
