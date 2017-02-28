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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.fm.other.Client;
import com.fm.other.Protocol;
import com.fm.other.ThreadPool;
import com.fm.service.ClientService;
import com.fm.R;


/**播放器窗口*/
public final class PlayerActivity extends Activity implements SensorEventListener 
{
	// 控件
	private ImageButton 	img;		//显示光碟
	private Button			btnLast;	//上一首按钮
	private Button			btnNext;	//下一首按钮
	private Button			btnPlay;	//播放按钮
	private ImageButton		btnList;	//音乐列表按钮
	private SeekBar			bar;		//调节歌曲进度
	
	
	int lastX, lastY;
	final int	IMAGE_X = 170;
	final int	IMAGE_Y = 100;
	final int	ptX_2 = 100;
	final int	ptY_2 = 430;
	final int	ptX_1 = 0;
	final int	ptY_1 = 50;
	int imageLeft;
	int imageTop;
	int imageRight;
	int imageBottom;
	
	
	/**歌曲是否正在播放*/
	volatile boolean playing = false;
	
	private final int	MSG_UPDATE_UI = 1;		//更新UI
	private final int	MSG_NET_ERROR = 2;		//网络错误
	
	private MsgReceiver msgReceiver;  
	/**是否已经获得过音乐列表*/
	private boolean		haveGet = false;
	
	
	AnimationDrawable anim;
	
	// 传感器管理器
	private SensorManager mSensorManager;
	private Sensor acc_sensor;// 加速度传感器
		
		
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player); 
        
        // 初始化控件
        img = (ImageButton) findViewById(R.id.imgDisk2);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnLast = (Button) findViewById(R.id.btnLast);
        btnList = (ImageButton) findViewById(R.id.btnList);
        bar = (SeekBar) findViewById(R.id.pbDuration);
        
        initSeekBar(bar);
        
        img.setBackgroundResource(R.drawable.drawable_anim);
        anim = (AnimationDrawable) img.getBackground();
        //把光碟放回原位置
		originImage();
        
        //动态注册广播接收器  
        msgReceiver = new MsgReceiver();  
        IntentFilter intentFilter = new IntentFilter();  
        intentFilter.addAction("com.example.communication.RECEIVER");  
        registerReceiver(msgReceiver, intentFilter);

        //绑定service
        Intent intent2 = new Intent(PlayerActivity.this, ClientService.class);
		bindService(intent2, m_Connection, Context.BIND_AUTO_CREATE);
		
        // 获取屏幕分辨率
 		DisplayMetrics dm = getResources().getDisplayMetrics();
 		final int screenWidth = dm.widthPixels;
 		final int screenHeight = dm.heightPixels - 50;
 		
 		
 		img.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (event.getAction()) 
				{
				case MotionEvent.ACTION_DOWN:
					/*getX()是表示Widget相对于自身左上角的x坐标,
					 * 而getRawX()是表示相对于屏幕左上角的x坐标值
					 * (注意:这个屏幕左上角是手机屏幕左上角,
					 * 不管activity是否有titleBar或是否全屏幕),
					 * getY(),getRawY()一样的道理*/
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					//Toast.makeText(PlayerActivity.this, lastX + "&" + lastY, Toast.LENGTH_LONG).show();
					break;
					
				case MotionEvent.ACTION_MOVE:
					int dx = (int) event.getRawX() - lastX;
					int dy = (int) event.getRawY() - lastY;
					int left = v.getLeft() + dx;
					int bottom = v.getBottom() + dy;
					int right = v.getRight() + dx;
					int top = v.getTop() + dy;
					
					if (left < 0)
					{
						left = 0;
						right = v.getWidth();
					}

					if (top < 0) 
					{
						top = 0;
						bottom = v.getHeight();
					}

					if (right > screenWidth)
					{
						right = screenWidth;
						left = right - v.getWidth();
					}

					if (bottom > screenHeight) 
					{
						bottom = screenHeight;
						top = bottom - v.getHeight();
					}
					
					img.layout(left, top, right, bottom);
				
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					img.postInvalidate();				// 刷新界面
					
					playing = false;	// 移动时停止播放
					break;
					
				case MotionEvent.ACTION_UP:
					int dx2 = (int) event.getRawX();
					int dy2 = (int) event.getRawY();
					
					//如果离放光碟的地方很近
					if (Math.abs(dx2 - (ptX_2 + 30)) < 80 && Math.abs(dy2 - (ptY_2 + 10)) < 80)
					{
						/*
						//自动把光碟放回正确位置
						dx2 = ptX_2 - lastX;
						dy2 = ptY_2 - lastY;
						int left2 = v.getLeft() + dx2;
						int bottom2 = v.getBottom() + dy2;
						int right2 = v.getRight() + dx2;
						int top2 = v.getTop() + dy2;
						
						if (left2 < 0)
						{
							left2 = 0;
							right2 = v.getWidth();
						}

						if (top2 < 0) 
						{
							top2 = 0;
							bottom2 = v.getHeight();
						}

						if (right2 > screenWidth)
						{
							right2 = screenWidth;
							left2 = right2 - v.getWidth();
						}

						if (bottom2 > screenHeight) 
						{
							bottom2 = screenHeight;
							top2 = bottom2 - v.getHeight();
						}
						
						imageLeft = left2;
						imageTop = top2;
						imageRight = right2;
						imageBottom = bottom2;
						img.layout(left2, top2, right2, bottom2);
					
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						img.postInvalidate();				// 刷新界面
						*/
						play(true);
						/*
						rightImage();
						playing = true;
				    	
				    
				    	//恢复光碟的动画
						anim.start();
						*/
					}
					else		//离放光碟的地方很远
					{
						/*
						img.layout(ptX_1, ptY_1, ptX_1 + v.getWidth(), ptY_1 + v.getHeight());
						img.postInvalidate();				// 刷新界面
						*/
						//把光碟放回原位置
						pause(true);
						/*
						originImage();
						
						playing = false;
				    	
				    
				    	//停止光碟的动画
				    	anim.stop();
						*/
						
					}
					break;
				}
				return false;
			}
		});
 		
 		
 		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btnNext:
					nextMusic();
					break;
				}
				
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
 		
 		
 		btnList.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (haveGet)
				{
					// 跳转到歌曲列表窗口
					Intent intent = new Intent(PlayerActivity.this, MusicActivity.class);
					startActivity(intent);
				}
				else
				{
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
    }
	
	/**光盘每一帧的图片*/
	int[] diskImg = { R.drawable.dy1, R.drawable.dy2, R.drawable.dy3, 
			R.drawable.dy4 };
	
	private Handler handler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case MSG_UPDATE_UI:
    			//img.setBackgroundResource(R.drawable.drawable_anim);
    			//img.setImageResource(photo[msg.arg1]);
    			//img.layout(imageLeft, imageTop, imageRight, imageBottom);
				//img.postInvalidate();	
    			break;
    			
    		case MSG_NET_ERROR:
    			Toast.makeText(PlayerActivity.this, "网络错误", Toast.LENGTH_LONG).show();
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
		rightImage();
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
    	originImage();
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
    
    
    /**还原光碟的位置*/
    private void originImage()
    {
    	img.layout(ptX_1, ptY_1, ptX_1 + IMAGE_X, ptY_1 + IMAGE_Y);
		img.postInvalidate();
    }
    
    
    /**把光碟图片放在播放位置*/
    private void rightImage()
    {
    	img.layout(ptX_2, ptY_2, ptX_2 + IMAGE_X, ptY_2 + IMAGE_Y);
		img.postInvalidate();		
    }
    
    
	//菜单选项常量
  	private final int ITEM_EXIT = Menu.FIRST + 3;
  	private final int ITEM_RECORD = Menu.FIRST + 4;
  	
  	//设置菜单
  	@Override  
  	public boolean onCreateOptionsMenu(Menu menu) 
  	{	
  		//安卓4.0系统默认不显示图标，得用特殊方法才能显示出来
  		setIconEnable(menu, true); 

  		menu.add(0, ITEM_EXIT, 0, "退出");//.setIcon(R.drawable.exit);
  		menu.add(0, ITEM_RECORD, 0, "录音");
  		
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
  			
  		}

  		return true;
  	}
  	
  	
  	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
  		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			// 告诉服务器断开连接
			sendCommand(Protocol.CLOSE_CLIENT);
						
			// 关闭所有线程
			ThreadPool.shutduwn();
			
			
			//关闭所有服务
			stopService(new Intent(PlayerActivity.this, ClientService.class));
			
			
			
			finish();
			//按返回键后台
			//moveTaskToBack(false);  
			//return true;
			
			
			return false;
		}
		
  		//减小音量键
  		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.getRepeatCount() == 0)
		{
  			Toast.makeText(PlayerActivity.this, "减小音量键", Toast.LENGTH_LONG).show(); 
			return true;
		}
  		
  		//增大音量键
  		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && event.getRepeatCount() == 0)
		{
  			Toast.makeText(PlayerActivity.this, "增大音量键", Toast.LENGTH_LONG).show(); 
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
		public void onServiceDisconnected(ComponentName arg0) {
			ser = null;
			
		}
    	
    };
    
    @Override  
    protected void onDestroy() {  
        //停止服务  
        //stopService(mIntent);  
        //注销广播  
        unregisterReceiver(msgReceiver);  
        super.onDestroy();  
    }  
    
    
    public static String[] listss =null;
    
    // TODO
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
    		String []lists = strList.split(Protocol.getSeparator());
    
    		listss = lists;
    		
    		haveGet = true;
    		
    		// 跳转到歌曲列表窗口
			Intent intent = new Intent(PlayerActivity.this, MusicActivity.class);
			startActivity(intent);
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
    		
    	case Protocol.PLAY:
    		play(false);
    		break;
    		
    	case Protocol.PAUSE:
    		pause(false);
    		break;
    		 
    	}

    }
    
    
    /**接收线程发送的广播*/
    public class MsgReceiver extends BroadcastReceiver 
    {
    	  
    	@Override
    	public void onReceive(Context arg0, Intent intent) 
    	{
    		//拿到进度，更新UI  

        	//发送Action为com.example.communication.RECEIVER的广播  
        	//拿到进度，更新UI  
        	String recvStr = intent.getStringExtra("receive");
        	
        	dealCommand(recvStr);
         
    	}

    }
    
    
    /**初始化进度条哦*/
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


	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		// 手机晃动，当前时间 
		long current_time = java.lang.System.currentTimeMillis();
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
				// 进行换歌操作
				nextMusic();
			}
		}
		
		/* 重新开始检测 */
		last_x = x;
		last_y = y;
		last_z = z;
	}
	
	
}