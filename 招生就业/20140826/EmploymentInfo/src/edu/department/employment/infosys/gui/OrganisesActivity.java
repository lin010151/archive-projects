/**
 * @author Ray
 * 
 */

package edu.department.employment.infosys.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.XListview.XListView;
import edu.department.employment.infosys.XListview.XListView.IXListViewListener;
import edu.department.employment.infosys.utility.ImageUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class OrganisesActivity extends Activity implements IXListViewListener {

	private TabHost mTabHost;

	/**
	 * 校内招聘专场
	 */
	private XListView mInOrganiseListView;
	private ArrayAdapter<String> mInAdapter;
	private ArrayList<String> inNewsTitle;
	private ArrayList<String> inNewsHref;
	private Thread inThread;
	private Handler inHandler;
	private int inPageNo;
	private boolean inRefreshormore; // 用以判断刷新或装载,false为刷新,true为装载
//	private ImageUtil inImgUtil; // 图像处理模块

	/**
	 * 校外招聘专场
	 */
	private XListView mOutOrganiseListView;
	private ArrayAdapter<String> mOutAdapter;
	private ArrayList<String> outNewsTitle;
	private ArrayList<String> outNewsHref;
	private Thread outThread;
	private Handler outHandler;
	private int outPageNo;
	private boolean outRefreshormore; // 用以判断刷新或装载,false为刷新,true为装载
//	private ImageUtil outImgUtil; // 图像处理模块

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organises_activity);

		mTabHost = (TabHost) findViewById(R.id.organisesRootLayout);
		mTabHost.setup();

		// 去掉默认标签样式的深灰色下划线
		mTabHost.getTabWidget().setStripEnabled(false);

		/**
		 * 校内招聘专场
		 */
		inPageNo = 1; // 设置初始页面
		inRefreshormore = false; // 用以判断刷新或装载,false为刷新,true为装载
		inNewsTitle = new ArrayList<String>();
		inNewsHref = new ArrayList<String>();
		getInDataByUrlThread(); // 开启线程获取内部网页数据
		mTabHost.addTab(mTabHost.newTabSpec("校内").setIndicator("校内")
				.setContent(R.id.inOrganisesListView));
		mInOrganiseListView = (XListView) findViewById(R.id.inOrganisesListView);
		mInOrganiseListView.setPullLoadEnable(true);
//		inImgUtil = new ImageUtil(); // 用以判断刷新或装载,false为刷新,true为装载
//		ImageView inImg = (ImageView) inImgUtil.getImageView(this,
//				R.drawable.pic1);
//		mInOrganiseListView.addHeaderView(inImg, null, false);
		// 创建消息处理线程
		inHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					if (!inRefreshormore) { // 刷新
						/** 设置适配器。 */
						mInAdapter = new ArrayAdapter<String>(
								OrganisesActivity.this, R.layout.list_item,
								inNewsTitle);
						mInOrganiseListView.setAdapter(mInAdapter);
					} else { // 装载更多
						mInAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// 设置拉动监听。
		mInOrganiseListView.setXListViewListener(this);
		/** 设置新闻列表单行点击的监听。 */
		mInOrganiseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // 排除掉“下拉加载更多”行的监听事件,抬头图片与刷新提示占据了2行，故下标索引需要减掉2
					String url_position = inNewsHref.get(position - 2);
					Intent intent = new Intent();
					intent.setClass(OrganisesActivity.this,
							DetailNoticeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("url", url_position);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		/**
		 * 校外招聘专场
		 */
		outPageNo = 1; // 设置初始页面
		outRefreshormore = false; // 用以判断刷新或装载,false为刷新,true为装载
		outNewsTitle = new ArrayList<String>();
		outNewsHref = new ArrayList<String>();
		getOutDataByUrlThread(); // 开启线程获取内部网页数据
		mTabHost.addTab(mTabHost.newTabSpec("校外").setIndicator("校外")
				.setContent(R.id.outOrganisesListView));
		mOutOrganiseListView = (XListView) findViewById(R.id.outOrganisesListView);
		mOutOrganiseListView.setPullLoadEnable(true);
//		outImgUtil = new ImageUtil(); // 用以判断刷新或装载,false为刷新,true为装载
//		ImageView outImg = (ImageView) outImgUtil.getImageView(this,
//				R.drawable.pic1);
//		mOutOrganiseListView.addHeaderView(outImg, null, false);
		// 创建消息处理线程
		outHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!outRefreshormore) { // 刷新
						/** 设置适配器。 */
						mOutAdapter = new ArrayAdapter<String>(
								OrganisesActivity.this, R.layout.list_item,
								outNewsTitle);
						mOutOrganiseListView.setAdapter(mOutAdapter);
					} else { // 装载更多
						mOutAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// 设置拉动监听。
		mOutOrganiseListView.setXListViewListener(this);
		/** 设置新闻列表单行点击的监听。 */
		mOutOrganiseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // 排除掉“下拉加载更多”行的监听事件,抬头图片与刷新提示占据了2行，故下标索引需要减掉2
					String url_position = outNewsHref.get(position - 2);
					Intent intent = new Intent();
					intent.setClass(OrganisesActivity.this,
							DetailNoticeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("url", url_position);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		// 设置当前选中的tab
		mTabHost.setCurrentTab(0);

		// 初始化设置标签背景
		updateTabBackground(mTabHost);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				updateTabBackground(mTabHost);
			}
		});
	}

	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		// 设置时间格式
		SimpleDateFormat formatter = new SimpleDateFormat(
				" yyyy年MM月dd日   HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String curtime = formatter.format(curDate);
		if (mTabHost.getCurrentTab() == 0) {
			mInOrganiseListView.stopRefresh();
			mInOrganiseListView.stopLoadMore();
			// 此处要获取时间。
			mInOrganiseListView.setRefreshTime(curtime);
		} else {
			mOutOrganiseListView.stopRefresh();
			mOutOrganiseListView.stopLoadMore();
			// 此处要获取时间。
			mOutOrganiseListView.setRefreshTime(curtime);
		}

	}

	/** 下拉刷新时的后台操作。 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (mTabHost.getCurrentTab() == 0) {
			inHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// 清除数组内容
					inNewsTitle.clear();
					inNewsHref.clear();
					inPageNo = 1; // 设置页面为第一页
					getInDataByUrlThread(); // 获取数据
					inRefreshormore = false; // 设置当前为刷新页面
				}
			}, 2000);
		} else {
			outHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// 清除数组内容
					outNewsTitle.clear();
					outNewsHref.clear();
					outPageNo = 1; // 设置页面为第一页
					getOutDataByUrlThread(); // 获取数据
					outRefreshormore = false; // 设置当前为刷新页面
				}
			}, 2000);
		}

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (mTabHost.getCurrentTab() == 0) {
			inHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					inPageNo++; // 设置下一页
					getInDataByUrlThread(); // 获取网页数据
					inRefreshormore = true; // 设置当前为装载更多页面
				}
			}, 2000);
		} else {
			outHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					outPageNo++; // 设置下一页
					getOutDataByUrlThread(); // 获取网页数据
					outRefreshormore = true; // 设置当前为装载更多页面
				}
			}, 2000);
		}

	}

	/** 获取内部新闻列表及对应链接。 */
	private void getInDataByUrlThread() {
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// 装载当前网页
					document = Jsoup.connect(
							getResources().getString(R.string.inOrganises_url)
									+ Integer.toString(inPageNo)).get();
					// 获取所有包含"class"与"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							inNewsTitle.add(link.text().toString().trim());
							// 保存链接
							inNewsHref.add(getResources().getString(
									R.string.website_url)
									+ link.attr("href"));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 发送消息更新视图
				msg.what = 0x123;
				msg.obj = null;
				inHandler.sendMessage(msg);
			}
		});
		inThread.start();
	}

	/** 获取外部新闻列表及对应链接。 */
	private void getOutDataByUrlThread() {
		outThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// 装载当前网页
					document = Jsoup.connect(
							getResources().getString(R.string.outOrganises_url)
									+ Integer.toString(outPageNo)).get();
					// 获取所有包含"class"与"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							outNewsTitle.add(link.text().toString().trim());
							// 保存链接
							outNewsHref.add(getResources().getString(
									R.string.website_url)
									+ link.attr("href"));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 发送消息更新视图
				msg.what = 0x123;
				msg.obj = null;
				outHandler.sendMessage(msg);
			}
		});
		outThread.start();
	}

	/** Tab选中与未选中时的样式 */
	private void updateTabBackground(TabHost mTabHost) {
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

			final TextView tv = (TextView) mTabHost.getTabWidget()
					.getChildAt(i).findViewById(android.R.id.title);
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(17);
			tv.setTextColor(Color.parseColor("#000000"));

			mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 50; // 设置Tab高度

			View mTab = mTabHost.getTabWidget().getChildAt(i);

			if (mTabHost.getCurrentTab() == i) {
				// 选中后的效果
				mTab.setBackgroundResource(R.drawable.slidebar_bottom);
			} else {
				// 没选中的效果
				mTab.setBackgroundResource(R.drawable.bg_header_top);
			}
		}
	}
}