package com.fm.activity;

import com.fm.other.UserData;
import com.fm.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


/**引导窗口（第一次打开该应用时的引导界面）*/
public final class GuideActivity extends Activity implements OnGestureListener 
{
	private GestureDetector detector;
	private ViewFlipper flipper;
	ImageView[]			iamges = new ImageView[4];
	private int			m_curPage = 1;		//当前页
	private final int 	m_maxPage = 4;		//总页数
	/**立即体验按钮*/
	private Button		btnExperience;
	

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		//初始化控件
		
		
		iamges[0]=(ImageView) findViewById(R.id.imageview1);
		iamges[1]=(ImageView) findViewById(R.id.imageview2);
		iamges[2]=(ImageView) findViewById(R.id.imageview3);
		iamges[3]=(ImageView) findViewById(R.id.imageview4);
		
		detector = new GestureDetector(this);
		
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);
		
		flipper.addView(addTextView("", 1));
		flipper.addView(addTextView("", 2));
		flipper.addView(addTextView("", 3));
		View view = View.inflate(this, R.layout.view_guide, null);
		flipper.addView(view);
		
		btnExperience = (Button) view.findViewById(R.id.btnExperience);
		btnExperience.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				//跳转到登陆界面
                Intent intent = new Intent(GuideActivity.this, ConnectActivity.class);  
                startActivity(intent);  
                overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
                finish();  
				//标记，不再出现引导界面
                UserData.markFirstRun(GuideActivity.this);
			}
		});
		
		//flipper.addView(addView());
		
	}

	private View addImageView(int id) 
	{
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		return iv;
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return this.detector.onTouchEvent(event); 
	}

	
	@Override
	public boolean onDown(MotionEvent e) 
	{
		return false;
	}

	
	@Override
	public void onShowPress(MotionEvent e) {}

	
	@Override
	public boolean onSingleTapUp(MotionEvent e) 
	{
		return false;
	}

	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) 
	{	
		return false;
	}

	
	@Override
	public void onLongPress(MotionEvent e) {}

	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		if (e1.getX() - e2.getX() > 120) 	//手指向左滑动
		{
			if (m_curPage < m_maxPage)
			{
				m_curPage++;
				setPointImage(m_curPage);
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_left));
				this.flipper.showNext();
			}
			return true;
		} 
		else if (e1.getX() - e2.getX() < -120) 	//手指向右滑动
		{
			if (m_curPage > 1)
			{
				m_curPage--;
				setPointImage(m_curPage);
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_left));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_right));
				this.flipper.showPrevious();
			}
			return true;
		}
		return false;
		
	}
	
	
	/**设置小圆点的图片*/
	private void setPointImage(int curPage)
	{
		
		for (int j = 0; j < 4; j++)
		{
			if (j != curPage - 1)
			{
				iamges[j].setImageResource(R.drawable.small_point);
			}
			else
			{
				iamges[j].setImageResource(R.drawable.big_point);
			}
		}
	}

	
	private View addTextView(String text, int i)
    {
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setGravity(1);
		LinearLayout output = new LinearLayout(this);
		output.setOrientation(LinearLayout.VERTICAL);
		Drawable dabg;
		switch (i) 
		{
		case 0:
	
		case 1:
			dabg = this.getResources().getDrawable(R.drawable.guide1);
			output.setBackgroundDrawable(dabg);
			break;
		case 2:
			dabg = this.getResources().getDrawable(R.drawable.guide2);
			output.setBackgroundDrawable(dabg);
			break;
		default:
			dabg = this.getResources().getDrawable(R.drawable.guide3);
			output.setBackgroundDrawable(dabg);
			break;
		}
		output.addView(tv);
		return output;
	}
	
}
