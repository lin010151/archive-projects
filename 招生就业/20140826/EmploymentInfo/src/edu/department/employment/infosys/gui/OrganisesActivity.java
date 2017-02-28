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
	 * У����Ƹר��
	 */
	private XListView mInOrganiseListView;
	private ArrayAdapter<String> mInAdapter;
	private ArrayList<String> inNewsTitle;
	private ArrayList<String> inNewsHref;
	private Thread inThread;
	private Handler inHandler;
	private int inPageNo;
	private boolean inRefreshormore; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
//	private ImageUtil inImgUtil; // ͼ����ģ��

	/**
	 * У����Ƹר��
	 */
	private XListView mOutOrganiseListView;
	private ArrayAdapter<String> mOutAdapter;
	private ArrayList<String> outNewsTitle;
	private ArrayList<String> outNewsHref;
	private Thread outThread;
	private Handler outHandler;
	private int outPageNo;
	private boolean outRefreshormore; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
//	private ImageUtil outImgUtil; // ͼ����ģ��

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organises_activity);

		mTabHost = (TabHost) findViewById(R.id.organisesRootLayout);
		mTabHost.setup();

		// ȥ��Ĭ�ϱ�ǩ��ʽ�����ɫ�»���
		mTabHost.getTabWidget().setStripEnabled(false);

		/**
		 * У����Ƹר��
		 */
		inPageNo = 1; // ���ó�ʼҳ��
		inRefreshormore = false; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
		inNewsTitle = new ArrayList<String>();
		inNewsHref = new ArrayList<String>();
		getInDataByUrlThread(); // �����̻߳�ȡ�ڲ���ҳ����
		mTabHost.addTab(mTabHost.newTabSpec("У��").setIndicator("У��")
				.setContent(R.id.inOrganisesListView));
		mInOrganiseListView = (XListView) findViewById(R.id.inOrganisesListView);
		mInOrganiseListView.setPullLoadEnable(true);
//		inImgUtil = new ImageUtil(); // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
//		ImageView inImg = (ImageView) inImgUtil.getImageView(this,
//				R.drawable.pic1);
//		mInOrganiseListView.addHeaderView(inImg, null, false);
		// ������Ϣ�����߳�
		inHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					if (!inRefreshormore) { // ˢ��
						/** ������������ */
						mInAdapter = new ArrayAdapter<String>(
								OrganisesActivity.this, R.layout.list_item,
								inNewsTitle);
						mInOrganiseListView.setAdapter(mInAdapter);
					} else { // װ�ظ���
						mInAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// ��������������
		mInOrganiseListView.setXListViewListener(this);
		/** ���������б��е���ļ����� */
		mInOrganiseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // �ų������������ظ��ࡱ�еļ����¼�,̧ͷͼƬ��ˢ����ʾռ����2�У����±�������Ҫ����2
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
		 * У����Ƹר��
		 */
		outPageNo = 1; // ���ó�ʼҳ��
		outRefreshormore = false; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
		outNewsTitle = new ArrayList<String>();
		outNewsHref = new ArrayList<String>();
		getOutDataByUrlThread(); // �����̻߳�ȡ�ڲ���ҳ����
		mTabHost.addTab(mTabHost.newTabSpec("У��").setIndicator("У��")
				.setContent(R.id.outOrganisesListView));
		mOutOrganiseListView = (XListView) findViewById(R.id.outOrganisesListView);
		mOutOrganiseListView.setPullLoadEnable(true);
//		outImgUtil = new ImageUtil(); // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��
//		ImageView outImg = (ImageView) outImgUtil.getImageView(this,
//				R.drawable.pic1);
//		mOutOrganiseListView.addHeaderView(outImg, null, false);
		// ������Ϣ�����߳�
		outHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!outRefreshormore) { // ˢ��
						/** ������������ */
						mOutAdapter = new ArrayAdapter<String>(
								OrganisesActivity.this, R.layout.list_item,
								outNewsTitle);
						mOutOrganiseListView.setAdapter(mOutAdapter);
					} else { // װ�ظ���
						mOutAdapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};
		// ��������������
		mOutOrganiseListView.setXListViewListener(this);
		/** ���������б��е���ļ����� */
		mOutOrganiseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // �ų������������ظ��ࡱ�еļ����¼�,̧ͷͼƬ��ˢ����ʾռ����2�У����±�������Ҫ����2
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
			mInOrganiseListView.stopRefresh();
			mInOrganiseListView.stopLoadMore();
			// �˴�Ҫ��ȡʱ�䡣
			mInOrganiseListView.setRefreshTime(curtime);
		} else {
			mOutOrganiseListView.stopRefresh();
			mOutOrganiseListView.stopLoadMore();
			// �˴�Ҫ��ȡʱ�䡣
			mOutOrganiseListView.setRefreshTime(curtime);
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
					inPageNo = 1; // ����ҳ��Ϊ��һҳ
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

	/** ��ȡ�ڲ������б���Ӧ���ӡ� */
	private void getInDataByUrlThread() {
		inThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// װ�ص�ǰ��ҳ
					document = Jsoup.connect(
							getResources().getString(R.string.inOrganises_url)
									+ Integer.toString(inPageNo)).get();
					// ��ȡ���а���"class"��"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							inNewsTitle.add(link.text().toString().trim());
							// ��������
							inNewsHref.add(getResources().getString(
									R.string.website_url)
									+ link.attr("href"));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				// ������Ϣ������ͼ
				msg.what = 0x123;
				msg.obj = null;
				inHandler.sendMessage(msg);
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
							getResources().getString(R.string.outOrganises_url)
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
				} catch (IOException e) {
					e.printStackTrace();
				}
				// ������Ϣ������ͼ
				msg.what = 0x123;
				msg.obj = null;
				outHandler.sendMessage(msg);
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