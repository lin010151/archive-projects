package edu.department.employment.infosys.gui;

import java.util.ArrayList;
import java.util.Arrays;
import edu.department.employment.infosys.R;
import edu.department.employment.infosys.data.GlobalDataInstance;
import edu.department.employment.infosys.gui.MtitlePopupWindow.OnPopupWindowClickListener;
import edu.department.employment.infosys.jazzyviewpager.JazzyViewPager;
import edu.department.employment.infosys.jazzyviewpager.JazzyViewPager.TransitionEffect;
import edu.department.employment.infosys.jazzyviewpager.OutlineContainer;
import edu.department.employment.infosys.utility.DimensionUtility;
import edu.department.employment.infosys.utility.ImageAnimation;
import edu.department.employment.infosys.utility.PagerAdapterExt;
import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author dragonzhu
 * 
 */
@SuppressLint("RtlHardcoded")
@SuppressWarnings("deprecation")
public class MainFrameActivity extends ActivityGroup implements OnClickListener {

	private JazzyViewPager mJazzy;			// 标题栏页面切换效果
	private JazzyViewPager mpager;			// 页面视图
	private int pageNo;						// 页面索引

	private TextView mSelectedItem = null;	// 选中的消息条目	
	private RelativeLayout mHeader = null;	// 头部消息条目的Layout	
	private int mItemWidth = 0;				// 消息分类中每条分类的宽度	
	private int startX = 0;					// 条目背景移动开始位置
	
	// 消息分类
	private TextView mInform = null;
	private TextView mRecruitment = null;
	private TextView mPractive = null;
	private TextView mSession = null;
	
	private Context context = null;			// 上下文
	private LocalActivityManager manager = null;	// 本地Activity管理器
	
	//设置的初始化
	private MtitlePopupWindow SettingPopupWindow;
	private MtitlePopupWindow seteffectPopupWindow;
	private String [] items = {"标题效果", "页面效果", "关于"};
	private String [] effectitems={"标准", "便笺", "内立方体", "外立方体", "垂直翻转", 
			"水平翻转", "堆叠", "缩小", "放大", "旋转向上", "旋转向下", "折叠"};
	private int setefft;							// 设置特效的上一级条目

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);		// 关联主界面布局xml文件
		
		context = MainFrameActivity.this;			// 获取上下文		
		pageNo=0;									// 初始化页面索引
		setefft=0;									// 设置特效的上一级条目

		// viewpager加载多个activity
		// LocalActivityManager类是管理activity的
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		
        initeViews();								// 初始化控件及动画效果
        
        // 设置过渡效果 		 
     	setupJazziness(TransitionEffect.Standard);	// 装载及设置标题切换的效果
     	setupPagerView(TransitionEffect.Standard);	// 装载及设置页面切换的效果
        
		//设置的跳转
        Button mSettingbtn = (Button) findViewById(R.id.setting_btn);
        mSettingbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingPopupWindow.showAsDropDown(v);
			}
		});

        // 设置标题效果弹出菜单
        seteffectPopupWindow=new MtitlePopupWindow(this);
        seteffectPopupWindow.changeData(Arrays.asList(effectitems));
        seteffectPopupWindow.setOnPopupWindowClickListener(effectChangeListener);
        // 设置弹出菜单
        SettingPopupWindow = new MtitlePopupWindow(this);
        SettingPopupWindow.changeData(Arrays.asList(items));
        SettingPopupWindow.setOnPopupWindowClickListener(settingChangeListener);
	}
	
	// 获取页面效果索引号
	private TransitionEffect getEffect(int no) {
		// TODO Auto-generated method stub
		TransitionEffect effect = TransitionEffect.Standard;
		switch (no) {
		case 0:
			effect=TransitionEffect.Standard;
			break;
		case 1:
			effect=TransitionEffect.Tablet;
			break;
		case 2:
			effect=TransitionEffect.CubeIn;
			break;
		case 3:
			effect=TransitionEffect.CubeOut;
			break;
		case 4:
			effect=TransitionEffect.FlipVertical;
			break;
		case 5:
			effect=TransitionEffect.FlipHorizontal;
			break;
		case 6:
			effect=TransitionEffect.Stack;
			break;
		case 7:
			effect=TransitionEffect.ZoomIn;
			break;
		case 8:
			effect=TransitionEffect.ZoomOut;
			break;
		case 9:
			effect=TransitionEffect.RotateUp;
			break;
		case 10:
			effect=TransitionEffect.RotateDown;
			break;
		case 11:
			effect=TransitionEffect.Accordion;
			break;
		default:
			break;
		}
		Log.v("effect", "="+effect);
		return effect;
	}

	/**
     * 初始化控件
     */
    private void initeViews(){
    	mInform = (TextView) findViewById(R.id.tv_title_inform);
    	mRecruitment = (TextView) findViewById(R.id.tv_title_recruitment);
    	mPractive = (TextView) findViewById(R.id.tv_title_practive);
    	mSession = (TextView) findViewById(R.id.tv_title_session);
    	    	
    	mInform.setOnClickListener(this);
    	mRecruitment.setOnClickListener(this);
    	mPractive.setOnClickListener(this);
    	mSession.setOnClickListener(this);

    	// 设置选中条目属性
    	mSelectedItem = new TextView(this);
    	mSelectedItem.setText(R.string.title_news_category_inform);
    	mSelectedItem.setTextColor(Color.WHITE);
    	mSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
    	mSelectedItem.setGravity(Gravity.CENTER);
    	mSelectedItem.setWidth((getScreenWidth() - DimensionUtility.dip2px(this, 20)) / 4);
    	mSelectedItem.setBackgroundResource(R.drawable.slidebar_top);
    	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
    			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

    	mHeader = (RelativeLayout) findViewById(R.id.layout_title_bar);

    	mHeader.addView(mSelectedItem, param);
    	
    	// 设置屏幕宽度
    	GlobalDataInstance.GetInstance().setScreenWidth(getScreenWidth());
    }

    // 初始化及设置标题切换的效果
    private void setupPagerView(TransitionEffect effect)
    {
    	// 初始化PageViewer
		mpager = (JazzyViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, InformActivity.class);
		list.add(getView("InformActivity", intent));
		Intent intent2 = new Intent(context, RecruitmentActivity.class);
		list.add(getView("RecruitmentActivity", intent2));
		Intent intent3 = new Intent(context, InternshipActivity.class);
		list.add(getView("InternshipActivity", intent3));
		Intent intent4 = new Intent(context, OrganisesActivity.class);
		list.add(getView("OrganisesActivity", intent4));
		mpager.setTransitionEffect(effect);
		mpager.setAdapter(new PagerAdapterExt(list));
		mpager.setCurrentItem(pageNo);
		mJazzy.setPageMargin(5);
		mpager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
	// 初始化及设置标题切换的效果
	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(8);
		mJazzy.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	
	/**
     * 获取屏幕的宽度
     * @return
     */
    private int getScreenWidth(){
    	WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		return screenWidth;
    }
    
    // 改变页面效果的监听器
    OnPopupWindowClickListener effectChangeListener=new OnPopupWindowClickListener()
    {
		@Override
		public void onPopupWindowItemClick(int position) {
			// TODO Auto-generated method stub
			TransitionEffect effect=getEffect(position);
			if (0==setefft){			// 保存标题特效索引号
				//editor.putInt("TitleNo", position);
				mJazzy.setTransitionEffect(effect);
			}
			else						// 保存页面特效索引号
				mpager.setTransitionEffect(effect);
		}
    };
    
    // 改变设置的监听器
    OnPopupWindowClickListener settingChangeListener=new OnPopupWindowClickListener()
    {
		@SuppressLint("RtlHardcoded")
		@Override
		public void onPopupWindowItemClick(int position) {
			// TODO Auto-generated method stub
			// 特效或关于的页面跳转
			switch (position)
			{
			case 0:
				// 弹出设置标题特效子菜单
				seteffectPopupWindow.showAtLocation(findViewById(R.id.setting_btn),
						Gravity.LEFT, getScreenWidth()-seteffectPopupWindow.getWidth(), 2);
				setefft=0;
				break;
			case 1:
				// 弹出设置页面特效子菜单
				seteffectPopupWindow.showAtLocation(findViewById(R.id.setting_btn),
						Gravity.LEFT, getScreenWidth()-seteffectPopupWindow.getWidth(), 2);
				setefft=1;
				break;
			case 2:
				Intent intent = new Intent();
				intent.setClass(MainFrameActivity.this,
							AboutActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
    };

	// 页面适配器
	private class MainAdapter extends PagerAdapter {
		@SuppressLint("RtlHardcoded")
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// 添加线性布局
			LinearLayout mTitleView_Layout = new LinearLayout(
					MainFrameActivity.this);
			mTitleView_Layout.setOrientation(LinearLayout.HORIZONTAL); // 设置线性布局的方向为水平的
			// 添加Title提示
			TextView text = new TextView(MainFrameActivity.this);
			text.setGravity(Gravity.CENTER);		// 设置文本居中
			text.setTextSize(20);
			text.setTextColor(Color.WHITE);
			// 设置文字标题
			switch (position) {
			case 0:
				text.setText(R.string.title_inform); // 事务通知
				break;
			case 1:
				text.setText(R.string.title_recruitment); // 招聘信息
				break;
			case 2:
				text.setText(R.string.title_practive); // 实习信息
				break;
			case 3:
				text.setText(R.string.title_session); // 招聘专场
				break;
			default:
				break;
			}

			text.setPadding(8, 8, 8, 8); // 设置文字边距
			// 添加标题栏
			mTitleView_Layout.addView(text,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.FILL_PARENT);
			mTitleView_Layout.setGravity(Gravity.LEFT);
			container.addView(mTitleView_Layout, LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			mJazzy.setObjectForPosition(mTitleView_Layout, position);
			return mTitleView_Layout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mJazzy.findViewFromObject(position));
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub		
		switch (v.getId()) {
		case R.id.tv_title_inform:					// 点击事务通知时
			pageNo=0;	// 设置当前页面位置为事务通知
			break;
		case R.id.tv_title_recruitment:				// 点击招聘信息时
			pageNo=1;	// 设置当前页面位置为招聘信息
			break;
		case R.id.tv_title_practive:				// 点击实习时
			pageNo=2;	// 设置当前页面位置为实习
			break;
		case R.id.tv_title_session:					// 点击招聘专场信息
			pageNo=3;	// 设置当前页面位置为专场信息
			break;
		default:
			break;
		}
		setTitleSelected();							// 设置标题栏
	}
	
	// 设置标题栏,令其高亮
	public void setTitleSelected()
	{
		mItemWidth = findViewById(R.id.layout).getWidth();
		switch (pageNo)
		{
		case 0:
			//动画滑动
			ImageAnimation.SetImageSlide(mSelectedItem, startX, 0, 0, 0);
			//设置滑动后动画开始位置
			startX = 0;
			//设置选中项显示文字，也就是高亮部分文字
			mSelectedItem.setText(R.string.title_news_category_inform);
			break;
		case 1:
			//动画滑动
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth, 0, 0);
			//设置滑动后动画开始位置
			startX = mItemWidth;
			//设置选中项显示文字，也就是高亮部分文字
			mSelectedItem.setText(R.string.title_news_category_recruitment);
			break;
		case 2:
			//动画滑动
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 2, 0, 0);
			//设置滑动后动画开始位置
			startX = mItemWidth * 2;
			//设置选中项显示文字，也就是高亮部分文字
			mSelectedItem.setText(R.string.title_news_category_practive);
			break;
		case 3:
			//动画滑动
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 3, 0, 0);
			//设置滑动后动画开始位置
			startX = mItemWidth * 3;
			//设置选中项显示文字，也就是高亮部分文字
			mSelectedItem.setText(R.string.title_news_category_session);
			break;
		default:
			break;
		}
		mpager.setCurrentItem(pageNo);						// 设置抬头标题
		mJazzy.setCurrentItem(pageNo);						// 设置页面
	}
	
	/**
	 * 页卡切换监听
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				pageNo=0;	// 设置当前页面位置为事务通知
				break;
			case 1:
				pageNo=1;	// 设置当前页面位置为招聘信息
				break;
			case 2:
				pageNo=2;	// 设置当前页面位置为实习
				break;
			case 3:
				pageNo=3;	// 设置当前页面位置为专场信息
				break;
			}
			MainFrameActivity.this.setTitleSelected();		// 切换主页面的标题
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
}