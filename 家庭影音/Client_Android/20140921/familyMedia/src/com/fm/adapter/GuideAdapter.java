package com.fm.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


/**引导界面的适配器*/
public class GuideAdapter extends PagerAdapter
{

	private List<View> m_views;
	
	
	public GuideAdapter(List<View> views)
	{
		this.m_views = views;
	}
	
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2)
	{
		((ViewPager) arg0).removeView(m_views.get(arg1));
	}

	
	@Override
	public void finishUpdate(View arg0) {}

	
	@Override
	public int getCount() 
	{
		return m_views.size();
	}

	
	@Override
	public Object instantiateItem(View arg0, int arg1) 
	{
		((ViewPager) arg0).addView(m_views.get(arg1), 0);
		return m_views.get(arg1);
	}

	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == (arg1);
	}

	
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {}

	
	@Override
	public Parcelable saveState()
	{
		return null;
	}

	
	@Override
	public void startUpdate(View arg0) {}

}
