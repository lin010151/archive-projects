package com.fm.activity;

import com.fm.R;
import com.fm.util.App;
import com.fm.util.SwitchButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


/**设置窗口*/
public class SettingActivity extends Activity 
{
	//控件
	private Button			btnBack;
	private SwitchButton	btn;
		
		
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);   
        
        //初始化控件
        btnBack = (Button) findViewById(R.id.setting_btnBack);
        btn = (SwitchButton) findViewById(R.id.setting_switch);

        btn.setChecked(true);
        
        btnBack.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		finish();
        	}
        }); 
        
        
        btn.setOnCheckedChangeListener(new OnCheckedChangeListener()
        { 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            { 
            
            	if (isChecked)	
            	{
            
            	}
            	else	
            	{

            	}
            }
        });
    }
}
