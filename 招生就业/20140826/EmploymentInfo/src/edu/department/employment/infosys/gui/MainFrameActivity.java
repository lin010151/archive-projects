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

	private JazzyViewPager mJazzy;			// ������ҳ���л�Ч��
	private JazzyViewPager mpager;			// ҳ����ͼ
	private int pageNo;						// ҳ������

	private TextView mSelectedItem = null;	// ѡ�е���Ϣ��Ŀ	
	private RelativeLayout mHeader = null;	// ͷ����Ϣ��Ŀ��Layout	
	private int mItemWidth = 0;				// ��Ϣ������ÿ������Ŀ��	
	private int startX = 0;					// ��Ŀ�����ƶ���ʼλ��
	
	// ��Ϣ����
	private TextView mInform = null;
	private TextView mRecruitment = null;
	private TextView mPractive = null;
	private TextView mSession = null;
	
	private Context context = null;			// ������
	private LocalActivityManager manager = null;	// ����Activity������
	
	//���õĳ�ʼ��
	private MtitlePopupWindow SettingPopupWindow;
	private MtitlePopupWindow seteffectPopupWindow;
	private String [] items = {"����Ч��", "ҳ��Ч��", "����"};
	private String [] effectitems={"��׼", "���", "��������", "��������", "��ֱ��ת", 
			"ˮƽ��ת", "�ѵ�", "��С", "�Ŵ�", "��ת����", "��ת����", "�۵�"};
	private int setefft;							// ������Ч����һ����Ŀ

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);		// ���������沼��xml�ļ�
		
		context = MainFrameActivity.this;			// ��ȡ������		
		pageNo=0;									// ��ʼ��ҳ������
		setefft=0;									// ������Ч����һ����Ŀ

		// viewpager���ض��activity
		// LocalActivityManager���ǹ���activity��
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		
        initeViews();								// ��ʼ���ؼ�������Ч��
        
        // ���ù���Ч�� 		 
     	setupJazziness(TransitionEffect.Standard);	// װ�ؼ����ñ����л���Ч��
     	setupPagerView(TransitionEffect.Standard);	// װ�ؼ�����ҳ���л���Ч��
        
		//���õ���ת
        Button mSettingbtn = (Button) findViewById(R.id.setting_btn);
        mSettingbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingPopupWindow.showAsDropDown(v);
			}
		});

        // ���ñ���Ч�������˵�
        seteffectPopupWindow=new MtitlePopupWindow(this);
        seteffectPopupWindow.changeData(Arrays.asList(effectitems));
        seteffectPopupWindow.setOnPopupWindowClickListener(effectChangeListener);
        // ���õ����˵�
        SettingPopupWindow = new MtitlePopupWindow(this);
        SettingPopupWindow.changeData(Arrays.asList(items));
        SettingPopupWindow.setOnPopupWindowClickListener(settingChangeListener);
	}
	
	// ��ȡҳ��Ч��������
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
     * ��ʼ���ؼ�
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

    	// ����ѡ����Ŀ����
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
    	
    	// ������Ļ���
    	GlobalDataInstance.GetInstance().setScreenWidth(getScreenWidth());
    }

    // ��ʼ�������ñ����л���Ч��
    private void setupPagerView(TransitionEffect effect)
    {
    	// ��ʼ��PageViewer
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
    
	// ��ʼ�������ñ����л���Ч��
	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(8);
		mJazzy.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	/**
	 * ͨ��activity��ȡ��ͼ
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	
	/**
     * ��ȡ��Ļ�Ŀ��
     * @return
     */
    private int getScreenWidth(){
    	WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		return screenWidth;
    }
    
    // �ı�ҳ��Ч���ļ�����
    OnPopupWindowClickListener effectChangeListener=new OnPopupWindowClickListener()
    {
		@Override
		public void onPopupWindowItemClick(int position) {
			// TODO Auto-generated method stub
			TransitionEffect effect=getEffect(position);
			if (0==setefft){			// ���������Ч������
				//editor.putInt("TitleNo", position);
				mJazzy.setTransitionEffect(effect);
			}
			else						// ����ҳ����Ч������
				mpager.setTransitionEffect(effect);
		}
    };
    
    // �ı����õļ�����
    OnPopupWindowClickListener settingChangeListener=new OnPopupWindowClickListener()
    {
		@SuppressLint("RtlHardcoded")
		@Override
		public void onPopupWindowItemClick(int position) {
			// TODO Auto-generated method stub
			// ��Ч����ڵ�ҳ����ת
			switch (position)
			{
			case 0:
				// �������ñ�����Ч�Ӳ˵�
				seteffectPopupWindow.showAtLocation(findViewById(R.id.setting_btn),
						Gravity.LEFT, getScreenWidth()-seteffectPopupWindow.getWidth(), 2);
				setefft=0;
				break;
			case 1:
				// ��������ҳ����Ч�Ӳ˵�
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

	// ҳ��������
	private class MainAdapter extends PagerAdapter {
		@SuppressLint("RtlHardcoded")
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// ������Բ���
			LinearLayout mTitleView_Layout = new LinearLayout(
					MainFrameActivity.this);
			mTitleView_Layout.setOrientation(LinearLayout.HORIZONTAL); // �������Բ��ֵķ���Ϊˮƽ��
			// ���Title��ʾ
			TextView text = new TextView(MainFrameActivity.this);
			text.setGravity(Gravity.CENTER);		// �����ı�����
			text.setTextSize(20);
			text.setTextColor(Color.WHITE);
			// �������ֱ���
			switch (position) {
			case 0:
				text.setText(R.string.title_inform); // ����֪ͨ
				break;
			case 1:
				text.setText(R.string.title_recruitment); // ��Ƹ��Ϣ
				break;
			case 2:
				text.setText(R.string.title_practive); // ʵϰ��Ϣ
				break;
			case 3:
				text.setText(R.string.title_session); // ��Ƹר��
				break;
			default:
				break;
			}

			text.setPadding(8, 8, 8, 8); // �������ֱ߾�
			// ��ӱ�����
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
		case R.id.tv_title_inform:					// �������֪ͨʱ
			pageNo=0;	// ���õ�ǰҳ��λ��Ϊ����֪ͨ
			break;
		case R.id.tv_title_recruitment:				// �����Ƹ��Ϣʱ
			pageNo=1;	// ���õ�ǰҳ��λ��Ϊ��Ƹ��Ϣ
			break;
		case R.id.tv_title_practive:				// ���ʵϰʱ
			pageNo=2;	// ���õ�ǰҳ��λ��Ϊʵϰ
			break;
		case R.id.tv_title_session:					// �����Ƹר����Ϣ
			pageNo=3;	// ���õ�ǰҳ��λ��Ϊר����Ϣ
			break;
		default:
			break;
		}
		setTitleSelected();							// ���ñ�����
	}
	
	// ���ñ�����,�������
	public void setTitleSelected()
	{
		mItemWidth = findViewById(R.id.layout).getWidth();
		switch (pageNo)
		{
		case 0:
			//��������
			ImageAnimation.SetImageSlide(mSelectedItem, startX, 0, 0, 0);
			//���û����󶯻���ʼλ��
			startX = 0;
			//����ѡ������ʾ���֣�Ҳ���Ǹ�����������
			mSelectedItem.setText(R.string.title_news_category_inform);
			break;
		case 1:
			//��������
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth, 0, 0);
			//���û����󶯻���ʼλ��
			startX = mItemWidth;
			//����ѡ������ʾ���֣�Ҳ���Ǹ�����������
			mSelectedItem.setText(R.string.title_news_category_recruitment);
			break;
		case 2:
			//��������
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 2, 0, 0);
			//���û����󶯻���ʼλ��
			startX = mItemWidth * 2;
			//����ѡ������ʾ���֣�Ҳ���Ǹ�����������
			mSelectedItem.setText(R.string.title_news_category_practive);
			break;
		case 3:
			//��������
			ImageAnimation.SetImageSlide(mSelectedItem, startX, mItemWidth * 3, 0, 0);
			//���û����󶯻���ʼλ��
			startX = mItemWidth * 3;
			//����ѡ������ʾ���֣�Ҳ���Ǹ�����������
			mSelectedItem.setText(R.string.title_news_category_session);
			break;
		default:
			break;
		}
		mpager.setCurrentItem(pageNo);						// ����̧ͷ����
		mJazzy.setCurrentItem(pageNo);						// ����ҳ��
	}
	
	/**
	 * ҳ���л�����
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				pageNo=0;	// ���õ�ǰҳ��λ��Ϊ����֪ͨ
				break;
			case 1:
				pageNo=1;	// ���õ�ǰҳ��λ��Ϊ��Ƹ��Ϣ
				break;
			case 2:
				pageNo=2;	// ���õ�ǰҳ��λ��Ϊʵϰ
				break;
			case 3:
				pageNo=3;	// ���õ�ǰҳ��λ��Ϊר����Ϣ
				break;
			}
			MainFrameActivity.this.setTitleSelected();		// �л���ҳ��ı���
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
}