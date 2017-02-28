package com.fm.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.fm.service.ClientService;
import com.fm.util.App;
import com.fm.util.Client;
import com.fm.util.Protocol;
import com.fm.util.ThreadPool;
import com.fm.window.MenuPopupWindow;
import com.fm.R;


/**播放器窗口*/
public class PlayerActivity extends Activity implements SensorEventListener 
{
	// 控件
	private ImageButton 	img;		//显示光碟
	private Button			btnLast;	//上一首按钮
	private Button			btnNext;	//下一首按钮
	private Button			btnPlay;	//播放按钮
	private Button			btnList;	//音乐列表按钮
	private Button			btnMode;
	private Button			btnShutDown;
	private SeekBar			bar;		//调节歌曲进度
	private Button			btnMore;
	private TextView		tvTitle;	//显示歌曲名
	private ProgressBar		progress;
	private TextView		tvSound;
	//消息
	private final int		MSG_NET_ERROR = 2;		//网络错误
	
	private boolean			enter = false;		//是否要进入歌曲列表
	/**歌曲是否正在播放*/
	volatile boolean playing = false;

	private MsgReceiver msgReceiver;  
	
	/**是否已经获得过音乐列表*/
	private boolean		haveGet = false;
	public static String		musicName = "";			//当前正在播放的歌曲名
	
	AnimationDrawable anim;
	
	// 传感器管理器
	private SensorManager mSensorManager;
	private Sensor acc_sensor;// 加速度传感器
		
	MenuPopupWindow		menuWindow; //弹出框
		
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player); 

        // 初始化控件
        img = (ImageButton) findViewById(R.id.player_imgDisk);
        btnPlay = (Button) findViewById(R.id.player_btnPlay);
        btnNext = (Button) findViewById(R.id.player_btnNext);
        btnLast = (Button) findViewById(R.id.player_btnLast);
        btnMore = (Button) findViewById(R.id.player_btnMore);        
        btnList = (Button) findViewById(R.id.player_btnList);
        bar = (SeekBar) findViewById(R.id.player_pbDuration);
        tvTitle = (TextView) findViewById(R.id.player_tvTitle);
        progress = (ProgressBar) findViewById(R.id.player_progressBar);
        btnMode = (Button) findViewById(R.id.player_btnMode); 
        tvSound = (TextView) findViewById(R.id.player_sound); 
        btnShutDown = (Button) findViewById(R.id.player_btnShutDown); 
        
        menuWindow = new MenuPopupWindow(PlayerActivity.this, listener2, exitListener);
        
        initSeekBar(bar);
        
        img.setBackgroundResource(R.drawable.drawable_anim);
        anim = (AnimationDrawable) img.getBackground();
      
        //动态注册广播接收器  
        msgReceiver = new MsgReceiver();  
        IntentFilter intentFilter = new IntentFilter();  
        intentFilter.addAction("com.example.communication.RECEIVER");  
        registerReceiver(msgReceiver, intentFilter);

        //绑定service
        Intent intent2 = new Intent(PlayerActivity.this, ClientService.class);
		bindService(intent2, m_Connection, Context.BIND_AUTO_CREATE);
		
   	
 		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.player_btnNext:
					nextMusic();
					break;
				}
				
			}
		}); 
 		
 		
 		btnShutDown.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendCommand(Protocol.SHUT_DOWN);
				
			}
		}); 
 		
 		
 		btnLast.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				lastMusic();
			}
		}); 
 		
 		
 		btnMore.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//显示菜单窗口				
				//设置layout在PopupWindow中显示的位置
				menuWindow.showAtLocation(PlayerActivity.this.findViewById(R.id.player_btnMore), 
						Gravity.TOP | Gravity.RIGHT, 5, 150); 
			}
		}); 
 		
 		
 		btnMode.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		}); 
 		
 		
 		btnList.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (haveGet)
				{
					//App.toast("have get true");	//TODO 真机有时运行不到
					// 跳转到歌曲列表窗口
					Intent intent = new Intent(PlayerActivity.this, MusicActivity.class);
					startActivity(intent);
					
				}
				else
				{ 
					enter = true;
					
					progress.setVisibility(View.VISIBLE);
					sendCommand(Protocol.GET_LIST);
				}
				
				
			}
		}); 
 		
 
 		btnPlay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (playing)
				{
					pause(true);
				}
				else
				{
					play(true);					
				}

			}
		}); 
		
 		
 		// 获得传感器管理对象
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// 获取加速度传感器
		acc_sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 注册加速度传感器
		mSensorManager.registerListener(this, acc_sensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		
		//获得播放器的当前状态
		sendCommand(Protocol.INIT);
		
		//屏幕常亮
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); 
		// in onResume() call
		mWakeLock.acquire(); 
		// in onPause() call 
		//mWakeLock.release();
    }
	
	
	
	
	private Handler handler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case MSG_NET_ERROR:
    			App.toast("网络错误");
    			break;
    		}
    		
    	}
    };
    
    
    /**
     * 发送命令给服务器
     * @param command 命令，值为Protocol里定义的常量
     */
    private void sendCommand(final int command)
    {
    	ExecutorService fixedThreadPool = ThreadPool.getThreadPool();	
		fixedThreadPool.execute(new Runnable() 
		{ 
	        @Override
	        public void run() 
	        {	
	        	String sendStr = Protocol.getSendString(command);
	        	if (!Client.sendText(sendStr))
	        	{
	        		Message msg = handler.obtainMessage();
		        	msg.what = MSG_NET_ERROR; 
					handler.sendMessage(msg);	
	        	}
	        }
	    });
    }
    
 
    /**
     * 播放
     * @param send 是否要发送播放命令给服务器
     */
    private void play(boolean send)
    {
    	playing = true;
    	
    	//把播放图标变成暂停图标
    	btnPlay.setBackgroundResource(R.drawable.btn_pause);
    	//恢复光碟的动画
		anim.start();

		if (send)
		{
			sendCommand(Protocol.PLAY);		
		}

    }
    
    
    /**
     * 停止播放
     * @param send 是否要发送暂停命令给服务器
     */
    private void pause(boolean send)
    {
    	playing = false;
    	
    	//把暂停图标变成播放图标
    	btnPlay.setBackgroundResource(R.drawable.btn_play);
    	
    	//停止光碟的动画
    	anim.stop();
    	
    	
    	if (send)
    	{
    		sendCommand(Protocol.PAUSE);
    	}
    	
   
    }
    
    
    /**上一首*/
    private void lastMusic()
    {
    	sendCommand(Protocol.LAST);
    	play(true);
    }
    
    
    /**下一首*/
    private void nextMusic()
    {
    	sendCommand(Protocol.NEXT);
    	play(true);
    }
   
    
	//菜单选项常量
  	private final int ITEM_EXIT = Menu.FIRST + 3;
  	private final int ITEM_RECORD = Menu.FIRST + 4;
  	private final int ITEM_TEST = Menu.FIRST + 5;
  	//设置菜单
  	@Override  
  	public boolean onCreateOptionsMenu(Menu menu) 
  	{	
  		//安卓4.0系统默认不显示图标，得用特殊方法才能显示出来
  		setIconEnable(menu, true); 

  		menu.add(0, ITEM_EXIT, 0, "退出");//.setIcon(R.drawable.exit);
  		menu.add(0, ITEM_RECORD, 0, "录音");
  		menu.add(0, ITEM_TEST, 0, "调试");
  		return true;
  	}
  	
  	
  	/**
  	 * 控制菜单的图标是否显示（安卓4.0以上的系统默认菜单无图标）
  	 * @param menu 菜单
  	 * @param enable 是否显示图标
  	 */
	private static void setIconEnable(Menu menu, boolean enable)  
	{  
		try   
		{  
			Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");  
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);  
			m.setAccessible(true);  
                
              //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特�?)  
			m.invoke(menu, enable);  
                
		} 
		catch (Exception e)   
		{  
			e.printStackTrace();  
		}  
	}  
      
  	
  	@Override
  	public boolean onOptionsItemSelected(MenuItem item)
  	{ 
  		switch (item.getItemId())
  		{
  		case ITEM_EXIT:
  			System.exit(0);
  			break;
  			
  		case ITEM_RECORD:
  			break;
  			
  		case ITEM_TEST:
  			
  			break;
  			
  		}

  		return true;
  	}
  	
  	
  	/**退出按钮监听*/
  	private OnClickListener listener2 = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// 告诉服务器断开连接
			sendCommand(Protocol.CLOSE_CLIENT);
		}
	};
	
  	/**退出按钮监听*/
  	private OnClickListener exitListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			// 告诉服务器断开连接
			sendCommand(Protocol.CLOSE_CLIENT);
			release();

			App.log("客户端退出");
			menuWindow.dismiss();
			PlayerActivity.this.finish();		
		}
	};
  	
  	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
  		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{	
			// 告诉服务器断开连接
			sendCommand(Protocol.CLOSE_CLIENT);
			release();//先后
			finish();
			
			
			//按返回键后台
			//moveTaskToBack(false);  
			//return true;
			
			
			return false;
		}
		
		
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0)
		{
			if (menuWindow.isShowing())
			{
				menuWindow.dismiss();
			}
			else
			{
				//显示菜单窗口				
				//设置layout在PopupWindow中显示的位置
				menuWindow.showAtLocation(PlayerActivity.this.findViewById(R.id.player_btnMore), 
						Gravity.TOP | Gravity.RIGHT, 5, 150); 
			}
			return false;
		}
		
  		//减小音量键
  		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.getRepeatCount() == 0)
		{
  			sendCommand(Protocol.TURN_DOWN);
			return true;
		}
  		
  		//增大音量键
  		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && event.getRepeatCount() == 0)
		{
  			sendCommand(Protocol.TURN_UP);
			return true;
		}
  		
		return super.onKeyDown(keyCode, event);
	}
  	
  	
  	
  	
  	ClientService ser;
    
  	
    private ServiceConnection m_Connection = new ServiceConnection()
    {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) 
		{
			ser = ((ClientService.LocalBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) 
		{
			ser = null;
		}
    	
    };
    
    
    @Override  
    protected void onDestroy() 
    {  
        //停止服务  
        //stopService(mIntent);  
        //注销广播  
        unregisterReceiver(msgReceiver);  
        super.onDestroy();  
    }  
    
    
    public static String[] listss =null;
    
    // TODO 定位
    /**
     * 处理命令
     * @param cmd 命令，值为Protocol里的常量
     */
    private void dealCommand(String cmd)
    {
    	switch (Protocol.getMommand(cmd))
    	{
    	case Protocol.LIST:
    		String strList = Protocol.getContent();
    		String []lists = strList.split(Protocol.getSeparator());	//注意正则表达式
    
    		listss = lists;
    		
    		haveGet = true;
    		
    		progress.setVisibility(View.INVISIBLE);
    		
    		if (enter)
    		{
	    		// 跳转到歌曲列表窗口
				Intent intent = new Intent(PlayerActivity.this, MusicActivity.class);
				startActivity(intent);
				enter = false;
    		}
    		break;
    		
    	case Protocol.PROGRESS:
    		String strProgress = Protocol.getContent();
    		int progress;
    		try
    		{
    			progress = Integer.parseInt(strProgress);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			return;
    		}
    		bar.setProgress(progress);
    		break;
    		
    	case Protocol.PLAY:
    		play(false);
    		break;
    		
    	case Protocol.PAUSE:
    		pause(false);
    		break;
    		
    	case Protocol.NAME:
    		musicName = Protocol.getContent();
    		tvTitle.setText(musicName);
    		App.log("设置标题");	
    		break;
    		
    	case Protocol.SOUND:
    		tvSound.setText("音量：" + Protocol.getContent());
    		break;
    		 
    	}

    }
    
    
    /**接收线程发送的广播*/
    public class MsgReceiver extends BroadcastReceiver 
    {
    	@Override
    	public void onReceive(Context arg0, Intent intent) 
    	{
        	String recvStr = intent.getStringExtra("receive");
        	App.log("窗口收到", recvStr);

        	if (recvStr.equals("服务器断开"))
        	{
        		//MyDialog.showCustomMessageOK(PlayerActivity.this, "系统提示", "服务器已关闭");
        		release();
        		finish();

        		//Intent intent = new Intent(PlayerActivity.this, MusicActivity.class);
    			//startActivity(intent);
        	}
        	else
        	{
        		dealCommand(recvStr);
        	}
        	

    	}

    }
    
    
    /**初始化进度条*/
    void initSeekBar(SeekBar bar)
    {
    	bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
    	{
    		int mProgress = 0;;
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
			{
				mProgress = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) 
			{		
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) 
			{
				ExecutorService fixedThreadPool = ThreadPool.getThreadPool();	
				fixedThreadPool.execute(new Runnable() 
				{ 
			        @Override
			        public void run() 
			        {	
			        	String strProgress = "" + mProgress;
			        	String sendStr = Protocol.getSendString(Protocol.PROGRESS, strProgress);

			        	if (!Client.sendText(sendStr))
			        	{
			        		Message msg = handler.obtainMessage();
				        	msg.what = MSG_NET_ERROR; 
							handler.sendMessage(msg);	
			        	}
			        }
			    });
			}
		});
    }


    /**
     * 判断一个服务是否正在运行
     * @param context
     * @param className 服务的类名（如com.example.MyService)
     * @return
     */
	public static boolean serviceIsWorked(Context context, String className)  
	{  
		ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);  
		
		for (int i = 0 ; i < runningService.size(); i++)  
		{  
			if (runningService.get(i).service.getClassName().toString().equals(className))  
			{  
				return true;  
			}  
		}  
		return false;  
	}  
	
	
	
	// 解除加速度传感器
	// mSensorManager.unregisterListener(this);
	
	// 摇晃的最小速度，越小灵敏度越高
	private final int HOLD_SPEED = 3800;
	/* 结束时三轴加速度x、y、z的值 */
	private float last_x, last_y = 4.5f, last_z = 9.8f;
	private float x, y, z;
	private long last_time;

	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}


	private long lastTime = 0;	//上一次换歌的时间
	
	
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		// 手机晃动，当前时间 
		long current_time = System.currentTimeMillis();
		long diffTime = (current_time - last_time);
		/* 如果持续的时间超过100毫秒，就计算晃动幅度 */
		if (diffTime > 100) 
		{
			last_time = current_time;
			x = event.values[SensorManager.DATA_X];
			y = event.values[SensorManager.DATA_Y];
			z = event.values[SensorManager.DATA_Z];
			float speed = 10000 * Math.abs(x + y + z - last_x - last_y - last_z) / diffTime;
			// Log.e(TAG, "speed=" + speed);
			/* 检测到摇晃后执行 */
			if (speed > HOLD_SPEED) 
			{
				if ((System.currentTimeMillis() - lastTime) > 1000)	//防止一次摇动换两首歌
	            {  
					// 进行换歌操作
					nextMusic();
					
	                lastTime = System.currentTimeMillis();   
	            } 
				
			}
		}
		
		/* 重新开始检测 */
		last_x = x;
		last_y = y;
		last_z = z;
	}
	
	
	/**退出时的资源释放处理*/
	private void release()
	{
		//Client.close();
		
		App.log("准备关闭所有线程");
		// 关闭所有线程
		ThreadPool.shutduwn();
		
		//关闭所有服务
		stopService(new Intent(PlayerActivity.this, ClientService.class));
	
	}
}