/**
 * @author Ray
 * 内部与外部消息暂时一致，等招生就业处有内容了，稍作修改即可
 * （也可能会大改）
 */

package edu.department.employment.infosys.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.XListview.XListView;
import edu.department.employment.infosys.XListview.XListView.IXListViewListener;
import edu.department.employment.infosys.model.GlobalDataInstance;
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

public class RecruitmentActivity extends Activity implements IXListViewListener {

	private TabHost mTabHost;

	/**
	 * 内部招聘信息
	 */
	private XListView mInRecruitListView;
	private ArrayAdapter<String> mInAdapter;
	private ArrayList<String> inNewsTitle;
	private ArrayList<String> inNewsHref;
	private Thread inThread;
	private Handler inHandler;
	private int inPageNo;
	private boolean inRefreshormore; // 用以判断刷新或装载,false为刷新,true为装载
	private boolean inaddheadimg; // 是否添加标题图片

	/**
	 * 外部招聘信息
	 */
	private XListView mOutRecruitListView;
	private ArrayAdapter<String> mOutAdapter;
	private ArrayList<String> outNewsTitle;
	private ArrayList<String> outNewsHref;
	private Thread outThread;
	private Handler outHandler;
	private int outPageNo;
	private boolean outRefreshormore; // 用以判断刷新或装载,false为刷新,true为装载
	private boolean outaddheadimg; // 是否添加标题图片

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruitment_activity);

		mTabHost = (TabHost) findViewById(R.id.recruitmentRootLayout);
		mTabHost.setup();

		// 去掉默认标签样式的深灰色下划线
		mTabHost.getTabWidget().setStripEnabled(false);

		/**
		 * 内部招聘信息
		 */
		inPageNo = 0; // 设置初始页面
		inRefreshormore = false; // 用以判断刷新或装载,false为刷新,true为装载
		inaddheadimg = true;
		outaddheadimg = true;

		inNewsTitle = new ArrayList<String>();
		inNewsHref = new ArrayList<String>();

		getInDataByUrlThreadLogin(); // 第一次登录时开启线程获取内部网页数据

		mTabHost.addTab(mTabHost.newTabSpec("内部").setIndicator("内部")
				.setContent(R.id.inRecruitmentListView));
		mInRecruitListView = (XListView) findViewById(R.id.inRecruitmentListView);
		mInRecruitListView.setPullLoadEnable(true);
		// 创建消息处理线程
		inHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					if (!inRefreshormore) { // 刷新
						if (inNewsTitle.size() > 0) {
							/** 设置适配器。 */
							mInAdapter = new ArrayAdapter<String>(
									RecruitmentActivity.this,
									R.layout.list_item, inNewsTitle);
							mInRecruitListView.setAdapter(mInAdapter);

							if (inaddheadimg) {
								// 添加标题图片
								ImageView inImg = (ImageView) ImageUtil
										.getInstance().getImageView(
												RecruitmentActivity.this,
												R.drawable.inrecruitment);
								mInRecruitListView.addHeaderView(inImg, null,
										false);
								inaddheadimg = false;
							}
						}
					} else { // 装载更多
						mInAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// 设置拉动监听。
		mInRecruitListView.setXListViewListener(this);
		/** 设置新闻列表单行点击的监听。 */
		mInRecruitListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // 排除掉“下拉加载更多”行的监听事件,抬头图片与刷新提示占据了2行，故下标索引需要减掉2
					String postid = inNewsHref.get(position - 2);
					Intent intent = new Intent();
					intent.setClass(RecruitmentActivity.this,
							DetailRecruitinfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("id", postid);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		/**
		 * 外部招聘信息
		 */
		outPageNo = 1; // 设置初始页面
		outRefreshormore = false; // 用以判断刷新或装载,false为刷新,true为装载
		outNewsTitle = new ArrayList<String>();
		outNewsHref = new ArrayList<String>();
		getOutDataByUrlThread(); // 开启线程获取外部网页数据
		mTabHost.addTab(mTabHost.newTabSpec("外部").setIndicator("外部")
				.setContent(R.id.outRecruitmentListView));
		mOutRecruitListView = (XListView) findViewById(R.id.outRecruitmentListView);
		mOutRecruitListView.setPullLoadEnable(true);

		// 创建消息处理线程
		outHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!outRefreshormore) { // 刷新
						if (outNewsTitle.size() > 0) {
							/** 设置适配器。 */
							mOutAdapter = new ArrayAdapter<String>(
									RecruitmentActivity.this,
									R.layout.list_item, outNewsTitle);
							mOutRecruitListView.setAdapter(mOutAdapter);

							if (outaddheadimg) {
								// 添加标题图片
								ImageView outImg = (ImageView) ImageUtil
										.getInstance().getImageView(
												RecruitmentActivity.this,
												R.drawable.outrecruitment);
								mOutRecruitListView.addHeaderView(outImg, null,
										false);
								outaddheadimg = false;
							}
						}
					} else { // 装载更多
						mOutAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// 设置拉动监听。
		mOutRecruitListView.setXListViewListener(this);
		/** 设置新闻列表单行点击的监听。 */
		mOutRecruitListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // 排除掉“下拉加载更多”行的监听事件,抬头图片与刷新提示占据了2行，故下标索引需要减掉2
					String url_position = outNewsHref.get(position - 2);
					Intent intent = new Intent();
					intent.setClass(RecruitmentActivity.this,
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
			mInRecruitListView.stopRefresh();
			mInRecruitListView.stopLoadMore();
			// 此处要获取时间。
			mInRecruitListView.setRefreshTime(curtime);
		} else {
			mOutRecruitListView.stopRefresh();
			mOutRecruitListView.stopLoadMore();
			// 此处要获取时间。
			mOutRecruitListView.setRefreshTime(curtime);
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
					inPageNo = 0; // 设置页面为第一页
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

	/** 等待用户登录获取内部新闻列表及对应链接。 */
	private void getInDataByUrlThreadLogin() {
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 如果用户已登录，则获取数据
				synchronized (GlobalDataInstance.GetInstance().getLoginlock()) {
					try {
						GlobalDataInstance.GetInstance().getLoginlock().wait();
						getInDataByUrlThread();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		inThread.start();
	}

	/** 获取内部新闻列表及对应链接。 */
	private void getInDataByUrlThread() {
		if (!GlobalDataInstance.GetInstance().isLoginsuccess())
			return;
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 如果用户已登录，则获取数据
				try {
					// 连接登录后台
					HttpClient httpclient = new DefaultHttpClient(); // 创建新的客户端
					// 网址uri
					String uri = getResources().getString(R.string.web_url)
							+ "/postjson";
					HttpPost httppost = new HttpPost(uri);
					// 设置HTTP POST请求参数必须用NameValuePair对象
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userid", GlobalDataInstance
							.GetInstance().getLogin().getUserID()));
					params.add(new BasicNameValuePair("pageNo", Integer
							.toString(inPageNo)));
					params.add(new BasicNameValuePair("what", "companyinfo"));
					
					// 绑定到请求Entry
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = httpclient.execute(httppost);
					// 检验状态码，如果成功接收数据
					int code = httpResponse.getStatusLine().getStatusCode();
					if (code == 200) {
						// 得到应答的字符串，这也是一个JSON格式保存的数据
						String retSrc = EntityUtils.toString(httpResponse
								.getEntity());
						JSONArray result = new JSONArray(retSrc);
							int size = result.length();
							for (int i = 0; i < size; i++) {
								// 取出一个JSON对象
								JSONObject temp = result.getJSONObject(i);
								inNewsTitle.add(temp.getString("companyinfo"));
								inNewsHref.add(temp.getString("id"));
							}
							// 发送消息更新视图
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = null;
							inHandler.sendMessage(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
							getResources().getString(
									R.string.outRecruitment_url)
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
					// 发送消息更新视图
					msg.what = 0x123;
					msg.obj = null;
					outHandler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
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