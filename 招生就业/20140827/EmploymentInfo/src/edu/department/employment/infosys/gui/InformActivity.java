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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

/**
 * @author dragonzhu & Raylin
 * 
 */
public class InformActivity extends Activity implements IXListViewListener {

	private XListView mInformListView; // 就业实习列表
	private ArrayAdapter<String> madapter;

	private ArrayList<String> NewsTitle;
	private ArrayList<String> NewsHref;

	private Thread thread;
	private Handler handler;

	private int pageNo;
	private boolean refreshormore; // 用以判断刷新或装载,false为刷新,true为装载

	private ImageUtil imgUtil; // 图像处理模块

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inform_activity);

		pageNo = 1; // 设置初始页面
		refreshormore = false;

		NewsTitle = new ArrayList<String>();
		NewsHref = new ArrayList<String>();

		getDataByUrlThread(); // 开启线程获取网页数据

		// 设置就业实习页面
		mInformListView = (XListView) findViewById(R.id.informListView);
		// 启用上拉加载，否则只能通过点击“加载更多”来加载。
		mInformListView.setPullLoadEnable(true);

		imgUtil = new ImageUtil();
		ImageView img = (ImageView) imgUtil.getImageView(this,
				R.drawable.inform);
		imgUtil.recycle();
		mInformListView.addHeaderView(img, null, false);

		// 创建消息处理线程
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!refreshormore) { // 刷新
						/** 设置适配器。 */
						madapter = new ArrayAdapter<String>(
								InformActivity.this, R.layout.list_item,
								NewsTitle);
						mInformListView.setAdapter(madapter);
					} else { // 装载更多
						madapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};

		// 设置拉动监听。
		mInformListView.setXListViewListener(this);
		/** 设置新闻列表单行点击的监听。 */
		mInformListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // 排除掉“下拉加载更多”行的监听事件,抬头图片与刷新提示占据了2行，故下标索引需要减掉2
					String url_position = NewsHref.get(position - 2);
					Intent intent = new Intent();
					intent.setClass(InformActivity.this,
							DetailNoticeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("url", url_position);
					intent.putExtras(bundle);
					startActivity(intent);
				}
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
		mInformListView.stopRefresh();
		mInformListView.stopLoadMore();
		// 此处要获取时间。
		mInformListView.setRefreshTime(curtime);
	}

	/** 下拉刷新时的后台操作。 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// 清除数组内容
				NewsTitle.clear();
				NewsHref.clear();
				pageNo = 1; // 设置页面为第一页
				getDataByUrlThread(); // 获取数据
				refreshormore = false; // 设置当前为刷新页面
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pageNo++; // 设置下一页
				getDataByUrlThread(); // 获取网页数据
				refreshormore = true; // 设置当前为装载更多页面
			}
		}, 2000);
	}

	// 利用线程获取网页数据
	private void getDataByUrlThread() {
		/** 获取新闻列表及对应链接。 */
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// 装载当前网页
					// TODO 在string.xml添加url
					document = Jsoup.connect(
							getResources().getString(R.string.inform_url)
									+ Integer.toString(pageNo)).get();
					// 获取所有包含"class"与"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							NewsTitle.add(link.text().toString().trim());
							// 保存链接
							NewsHref.add(getResources().getString(
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
				handler.sendMessage(msg);
			}
		});
		thread.start();
	}
}
