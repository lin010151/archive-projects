/**
 * @author Ray
 * �ڲ����ⲿ��Ϣ��ʱһ�£���������ҵ���������ˣ������޸ļ���
 * ��Ҳ���ܻ��ģ�
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
	 * �ڲ���Ƹ��Ϣ
	 */
	private XListView mInRecruitListView;
	private ArrayAdapter<String> mInAdapter;
	private ArrayList<String> inNewsTitle;
	private ArrayList<String> inNewsHref;
	private Thread inThread;
	private Handler inHandler;
	private int inPageNo;
	private boolean inRefreshormore; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
	private boolean inaddheadimg; // �Ƿ���ӱ���ͼƬ

	/**
	 * �ⲿ��Ƹ��Ϣ
	 */
	private XListView mOutRecruitListView;
	private ArrayAdapter<String> mOutAdapter;
	private ArrayList<String> outNewsTitle;
	private ArrayList<String> outNewsHref;
	private Thread outThread;
	private Handler outHandler;
	private int outPageNo;
	private boolean outRefreshormore; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
	private boolean outaddheadimg; // �Ƿ���ӱ���ͼƬ

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruitment_activity);

		mTabHost = (TabHost) findViewById(R.id.recruitmentRootLayout);
		mTabHost.setup();

		// ȥ��Ĭ�ϱ�ǩ��ʽ�����ɫ�»���
		mTabHost.getTabWidget().setStripEnabled(false);

		/**
		 * �ڲ���Ƹ��Ϣ
		 */
		inPageNo = 0; // ���ó�ʼҳ��
		inRefreshormore = false; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
		inaddheadimg = true;
		outaddheadimg = true;

		inNewsTitle = new ArrayList<String>();
		inNewsHref = new ArrayList<String>();

		getInDataByUrlThreadLogin(); // ��һ�ε�¼ʱ�����̻߳�ȡ�ڲ���ҳ����

		mTabHost.addTab(mTabHost.newTabSpec("�ڲ�").setIndicator("�ڲ�")
				.setContent(R.id.inRecruitmentListView));
		mInRecruitListView = (XListView) findViewById(R.id.inRecruitmentListView);
		mInRecruitListView.setPullLoadEnable(true);
		// ������Ϣ�����߳�
		inHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					if (!inRefreshormore) { // ˢ��
						if (inNewsTitle.size() > 0) {
							/** ������������ */
							mInAdapter = new ArrayAdapter<String>(
									RecruitmentActivity.this,
									R.layout.list_item, inNewsTitle);
							mInRecruitListView.setAdapter(mInAdapter);

							if (inaddheadimg) {
								// ��ӱ���ͼƬ
								ImageView inImg = (ImageView) ImageUtil
										.getInstance().getImageView(
												RecruitmentActivity.this,
												R.drawable.inrecruitment);
								mInRecruitListView.addHeaderView(inImg, null,
										false);
								inaddheadimg = false;
							}
						}
					} else { // װ�ظ���
						mInAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// ��������������
		mInRecruitListView.setXListViewListener(this);
		/** ���������б��е���ļ����� */
		mInRecruitListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // �ų������������ظ��ࡱ�еļ����¼�,̧ͷͼƬ��ˢ����ʾռ����2�У����±�������Ҫ����2
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
		 * �ⲿ��Ƹ��Ϣ
		 */
		outPageNo = 1; // ���ó�ʼҳ��
		outRefreshormore = false; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
		outNewsTitle = new ArrayList<String>();
		outNewsHref = new ArrayList<String>();
		getOutDataByUrlThread(); // �����̻߳�ȡ�ⲿ��ҳ����
		mTabHost.addTab(mTabHost.newTabSpec("�ⲿ").setIndicator("�ⲿ")
				.setContent(R.id.outRecruitmentListView));
		mOutRecruitListView = (XListView) findViewById(R.id.outRecruitmentListView);
		mOutRecruitListView.setPullLoadEnable(true);

		// ������Ϣ�����߳�
		outHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!outRefreshormore) { // ˢ��
						if (outNewsTitle.size() > 0) {
							/** ������������ */
							mOutAdapter = new ArrayAdapter<String>(
									RecruitmentActivity.this,
									R.layout.list_item, outNewsTitle);
							mOutRecruitListView.setAdapter(mOutAdapter);

							if (outaddheadimg) {
								// ��ӱ���ͼƬ
								ImageView outImg = (ImageView) ImageUtil
										.getInstance().getImageView(
												RecruitmentActivity.this,
												R.drawable.outrecruitment);
								mOutRecruitListView.addHeaderView(outImg, null,
										false);
								outaddheadimg = false;
							}
						}
					} else { // װ�ظ���
						mOutAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// ��������������
		mOutRecruitListView.setXListViewListener(this);
		/** ���������б��е���ļ����� */
		mOutRecruitListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // �ų������������ظ��ࡱ�еļ����¼�,̧ͷͼƬ��ˢ����ʾռ����2�У����±�������Ҫ����2
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

		// ���õ�ǰѡ�е�tab
		mTabHost.setCurrentTab(0);

		// ��ʼ�����ñ�ǩ����
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
		// ����ʱ���ʽ
		SimpleDateFormat formatter = new SimpleDateFormat(
				" yyyy��MM��dd��   HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String curtime = formatter.format(curDate);
		if (mTabHost.getCurrentTab() == 0) {
			mInRecruitListView.stopRefresh();
			mInRecruitListView.stopLoadMore();
			// �˴�Ҫ��ȡʱ�䡣
			mInRecruitListView.setRefreshTime(curtime);
		} else {
			mOutRecruitListView.stopRefresh();
			mOutRecruitListView.stopLoadMore();
			// �˴�Ҫ��ȡʱ�䡣
			mOutRecruitListView.setRefreshTime(curtime);
		}

	}

	/** ����ˢ��ʱ�ĺ�̨������ */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (mTabHost.getCurrentTab() == 0) {
			inHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// �����������
					inNewsTitle.clear();
					inNewsHref.clear();
					inPageNo = 0; // ����ҳ��Ϊ��һҳ
					getInDataByUrlThread(); // ��ȡ����
					inRefreshormore = false; // ���õ�ǰΪˢ��ҳ��
				}
			}, 2000);
		} else {
			outHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// �����������
					outNewsTitle.clear();
					outNewsHref.clear();
					outPageNo = 1; // ����ҳ��Ϊ��һҳ
					getOutDataByUrlThread(); // ��ȡ����
					outRefreshormore = false; // ���õ�ǰΪˢ��ҳ��
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
					inPageNo++; // ������һҳ
					getInDataByUrlThread(); // ��ȡ��ҳ����
					inRefreshormore = true; // ���õ�ǰΪװ�ظ���ҳ��
				}
			}, 2000);
		} else {
			outHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					outPageNo++; // ������һҳ
					getOutDataByUrlThread(); // ��ȡ��ҳ����
					outRefreshormore = true; // ���õ�ǰΪװ�ظ���ҳ��
				}
			}, 2000);
		}

	}

	/** �ȴ��û���¼��ȡ�ڲ������б���Ӧ���ӡ� */
	private void getInDataByUrlThreadLogin() {
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ����û��ѵ�¼�����ȡ����
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

	/** ��ȡ�ڲ������б���Ӧ���ӡ� */
	private void getInDataByUrlThread() {
		if (!GlobalDataInstance.GetInstance().isLoginsuccess())
			return;
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ����û��ѵ�¼�����ȡ����
				try {
					// ���ӵ�¼��̨
					HttpClient httpclient = new DefaultHttpClient(); // �����µĿͻ���
					// ��ַuri
					String uri = getResources().getString(R.string.web_url)
							+ "/postjson";
					HttpPost httppost = new HttpPost(uri);
					// ����HTTP POST�������������NameValuePair����
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userid", GlobalDataInstance
							.GetInstance().getLogin().getUserID()));
					params.add(new BasicNameValuePair("pageNo", Integer
							.toString(inPageNo)));
					params.add(new BasicNameValuePair("what", "companyinfo"));
					
					// �󶨵�����Entry
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = httpclient.execute(httppost);
					// ����״̬�룬����ɹ���������
					int code = httpResponse.getStatusLine().getStatusCode();
					if (code == 200) {
						// �õ�Ӧ����ַ�������Ҳ��һ��JSON��ʽ���������
						String retSrc = EntityUtils.toString(httpResponse
								.getEntity());
						JSONArray result = new JSONArray(retSrc);
							int size = result.length();
							for (int i = 0; i < size; i++) {
								// ȡ��һ��JSON����
								JSONObject temp = result.getJSONObject(i);
								inNewsTitle.add(temp.getString("companyinfo"));
								inNewsHref.add(temp.getString("id"));
							}
							// ������Ϣ������ͼ
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

	/** ��ȡ�ⲿ�����б���Ӧ���ӡ� */
	private void getOutDataByUrlThread() {
		outThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// װ�ص�ǰ��ҳ
					document = Jsoup.connect(
							getResources().getString(
									R.string.outRecruitment_url)
									+ Integer.toString(outPageNo)).get();
					// ��ȡ���а���"class"��"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							outNewsTitle.add(link.text().toString().trim());
							// ��������
							outNewsHref.add(getResources().getString(
									R.string.website_url)
									+ link.attr("href"));
						}
					}
					// ������Ϣ������ͼ
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

	/** Tabѡ����δѡ��ʱ����ʽ */
	private void updateTabBackground(TabHost mTabHost) {
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

			final TextView tv = (TextView) mTabHost.getTabWidget()
					.getChildAt(i).findViewById(android.R.id.title);
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(17);
			tv.setTextColor(Color.parseColor("#000000"));

			mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 50; // ����Tab�߶�

			View mTab = mTabHost.getTabWidget().getChildAt(i);

			if (mTabHost.getCurrentTab() == i) {
				// ѡ�к��Ч��
				mTab.setBackgroundResource(R.drawable.slidebar_bottom);
			} else {
				// ûѡ�е�Ч��
				mTab.setBackgroundResource(R.drawable.bg_header_top);
			}
		}
	}

}