package com.fm.activity;

import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fm.util.App;
import com.fm.util.Client;
import com.fm.util.ThreadPool;
import com.fm.util.UserData;
import com.fm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fm.service.ClientService;


/**连接服务器窗口*/
public class ConnectActivity extends Activity 
{
	//控件
	private Button			m_btnConnect;		// 连接服务器按钮
	private Button			btnScan;
	private EditText		m_txtIp;			// IP地址输入框
	private EditText		m_txtPort;			// 端口号输入框
	private CheckBox		checkBox;			// 记住IP选框
	private ProgressBar		progress;
	//消息常量
	private final int		MSG_CONNECT_SUCCEED		= 1;	//连接服务器成功
	private final int		MSG_CONNECT_FAIL		= 2;	//连接服务器失败

	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
      
        //初始化控件
        m_btnConnect = (Button) findViewById(R.id.connect_btnConnect);
        m_txtIp = (EditText) findViewById(R.id.connect_txtIp);
        m_txtPort = (EditText) findViewById(R.id.connect_txtPort);
        checkBox = (CheckBox) findViewById(R.id.connect_checkbox);
        progress = (ProgressBar) findViewById(R.id.connect_progressBar);
        btnScan = (Button)  findViewById(R.id.connect_btnScan);

		m_btnConnect.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String ip = m_txtIp.getText().toString().trim();
				String strPort = m_txtPort.getText().toString().trim();
				
				if (ip.equals(""))
				{
					App.toast("请输入服务端的IP地址");
					return;
				}
		
				if (!isIpRight(ip))
				{
					App.toast("IP地址格式不正确");
					return;
				}
				
				connect(ip, strPort);
		        
		        
			}
		}); 
	
		btnScan.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//打开扫描界面扫描条形码或二维码
				Intent intent = new Intent(ConnectActivity.this,ScanActivity.class);
				startActivityForResult(intent, 0);
	
		        
			}
		}); 
		
		
		//记住密码复选框监听
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        { 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            { 
            
            	if (isChecked)		//用户选择记住IP
            	{
            		UserData.saveIP(m_txtIp.getText().toString());
            		UserData.savePort(m_txtPort.getText().toString());
            	}
            	else				//用户选择取消记住IP
            	{
            		UserData.saveIP(null);
            		UserData.savePort(null);
            	}
            }
        });
   
        
        String ip = UserData.getIP();
        if (ip != null)
        {
        	
        	m_txtIp.setText(ip);
        	
        	
        	String port = UserData.getPort();
        	if (port != null)
        	{
        		m_txtPort.setText(port);
        	}
        	
        	checkBox.setChecked(true);		//注意：一定要在自动填写端口号后
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
    			progress.setVisibility(View.INVISIBLE);
    			
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
					UserData.saveIP(m_txtIp.getText().toString());
					UserData.savePort(m_txtPort.getText().toString());
				}
				
				break;
				
    		case MSG_CONNECT_FAIL:
    			progress.setVisibility(View.INVISIBLE);
    			
    			if (!Client.haveConnect())		// 加判断是因为线程不同步
    			{
    				App.toast("连接失败");
    			}
				break;
	
			}
    	}
    };
    
 
    private void connect(final String ip, String strPort)
    {
    	if (!App.isNetworkAvailable(ConnectActivity.this))
		{
			App.toast("网络异常，请检查网络设置");
			return;
		}
		
		
		final int port;
		try
		{
			port = Integer.parseInt(strPort);
		}
		catch(Exception e)
		{
			App.toast("请输入正确的端口号");
			return;
		}
		
		progress.setVisibility(View.VISIBLE);
		
		//TODO 初始化线程池
		ThreadPool.m_threadPool = null;
		ThreadPool.isRuning = true;
		ThreadPool.n++;
        ExecutorService fixedThreadPool = ThreadPool.getThreadPool();
        

        App.log("N为" + ThreadPool.n);
        try
        {
	        fixedThreadPool.execute(new Runnable() 
			{ 
		        @Override
		        public void run() 
		        {	
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


					handler.sendMessage(msg);	
				
		        }
		    });
        }
        catch(Exception e)
        {
        	App.toast("连接失败（异常" + e.getMessage() + "），请重新连接");
        }
    }
    
    
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
    
    
    /**
     * 判断Ip地址的格式是否正确
     * @param ip
     * @return
     */
    private static boolean isIpRight(String ip)
    {
    	String regex = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
    	return ip.matches(regex);
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) 
		{
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			//App.toast(scanResult);
			Pattern pat = Pattern.compile("ip<([\\d\\D]+?)>[\\d\\D]*?port<([\\d\\D]+?)>");  
			Matcher mat = pat.matcher(scanResult);
			if (mat.find()) 
			{
				m_txtIp.setText(mat.group(1));
				m_txtPort.setText(mat.group(2));
				
				connect(mat.group(1), mat.group(2));
			}
			else
			{
				App.toast("二维码有误");
			}
			
		}
	}
    
}
