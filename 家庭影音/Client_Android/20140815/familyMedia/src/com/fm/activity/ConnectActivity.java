package com.fm.activity;

import java.util.concurrent.ExecutorService;

import com.fm.other.Client;
import com.fm.other.ThreadPool;
import com.fm.other.UserData;
import com.fm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.fm.service.ClientService;


/**连接服务器窗口*/
public final class ConnectActivity extends Activity 
{
	//控件
	private Button			m_btnConnect;		// 连接服务器按钮
	private EditText		m_txtIp;			// IP地址输入框
	private EditText		m_txtPort;			// 端口号输入框
	private CheckBox		checkBox;			//记住IP选框
	//消息常量
	private final int		MSG_CONNECT_SUCCEED		= 1;	//连接服务器成功
	private final int		MSG_CONNECT_FAIL		= 2;	//连接服务器失败

	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        //初始化控件
        m_btnConnect = (Button) findViewById(R.id.btnConnect);
        m_txtIp = (EditText) findViewById(R.id.txtIp);
        m_txtPort = (EditText) findViewById(R.id.txtPort);
        checkBox = (CheckBox) findViewById(R.id.login_checkbox);
        
        
		//连接服务器按钮监听
		m_btnConnect.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String ip = m_txtIp.getText().toString().trim();
				String strPort = m_txtPort.getText().toString().trim();
				
				final int port;
				try
				{
					port = Integer.parseInt(strPort);
				}
				catch(Exception e)
				{
					Toast.makeText(ConnectActivity.this, "请输入正确的端口号", Toast.LENGTH_LONG).show();
					return;
				}
				
		        ExecutorService fixedThreadPool = ThreadPool.getThreadPool();
		        
		        // TODO
		        //if (fixedThreadPool.)
		        //fixedThreadPool.shutdownNow();		//关闭所有线程，可能之前用户已经点击了连接
		        Log.d("_TAG", "获得线程池");
		        fixedThreadPool.execute(new Runnable() 
				{ 
			        @Override
			        public void run() 
			        {	
			        	Log.d("_TAG", "开始连接线程");
			        	Message msg = handler.obtainMessage();
			        	
			        	boolean suecceed = Client.connect(ip, port);
			        	if (suecceed)
			        	{
			        		msg.what = MSG_CONNECT_SUCCEED; 
			        	}
			        	else
			        	{
			        		msg.what = MSG_CONNECT_FAIL; 	        		
			        	}
			        	Log.d("_TAG", "发送消息");
						handler.sendMessage(msg);	
			        }
			    });
		        
		        Log.d("_TAG", "按钮事件完成");
			}
		}); 
	
		
		//记住密码复选框监听
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        { 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            { 
            	if (isChecked)		//用户选择记住密码
            	{
            		Log.d("_TAG", "保存密码");
            		UserData.saveIP(ConnectActivity.this, m_txtIp.getText().toString());
            	}
            	else				//用户选择取消密码
            	{
            		UserData.saveIP(ConnectActivity.this, null);
            	}
            }
        });
   
        
        String ip = UserData.getIP(ConnectActivity.this);
        if (ip != null)
        {
        	
        	m_txtIp.setText(ip);
        	checkBox.setChecked(true);
        }
    
    }
   

    private Handler handler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case MSG_CONNECT_SUCCEED:		
    			// 跳转到主窗口
    			Intent intent = new Intent(ConnectActivity.this, PlayerActivity.class);
				startActivity(intent);
				finish();
				
				// 开启服务，用来接收服务器发送过来的命令
				Intent intent2 = new Intent(ConnectActivity.this, ClientService.class);
				startService(intent2);
				
				
				// 登陆成功并且勾选记住IP，则保存IP
				if (checkBox.isChecked())
				{
					UserData.saveIP(ConnectActivity.this, m_txtIp.getText().toString());
				}
				
				break;
				
    		case MSG_CONNECT_FAIL:
    			if (!Client.haveConnect())		// 加判断是因为线程不同步
    			{
    				Toast.makeText(ConnectActivity.this, "连接失败", Toast.LENGTH_LONG).show();
    			}
				break;
	
			}
    	}
    };
    
 
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
  		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			// 关闭所有线程
			ThreadPool.shutduwn();
		
			finish();
		
			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
