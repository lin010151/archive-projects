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

	private XListView mInformListView; // ��ҵʵϰ�б�
	private ArrayAdapter<String> madapter;

	private ArrayList<String> NewsTitle;
	private ArrayList<String> NewsHref;

	private Thread thread;
	private Handler handler;

	private int pageNo;
	private boolean refreshormore; // �����ж�ˢ�»�װ��,falseΪˢ��,trueΪװ��

	private ImageUtil imgUtil; // ͼ����ģ��

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inform_activity);

		pageNo = 1; // ���ó�ʼҳ��
		refreshormore = false;

		NewsTitle = new ArrayList<String>();
		NewsHref = new ArrayList<String>();

		getDataByUrlThread(); // �����̻߳�ȡ��ҳ����

		// ���þ�ҵʵϰҳ��
		mInformListView = (XListView) findViewById(R.id.informListView);
		// �����������أ�����ֻ��ͨ����������ظ��ࡱ�����ء�
		mInformListView.setPullLoadEnable(true);

		imgUtil = new ImageUtil();
		ImageView img = (ImageView) imgUtil.getImageView(this,
				R.drawable.inform);
		imgUtil.recycle();
		mInformListView.addHeaderView(img, null, false);

		// ������Ϣ�����߳�
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (!refreshormore) { // ˢ��
						/** ������������ */
						madapter = new ArrayAdapter<String>(
								InformActivity.this, R.layout.list_item,
								NewsTitle);
						mInformListView.setAdapter(madapter);
					} else { // װ�ظ���
						madapter.notifyDataSetChanged();
					}
					onLoad();
				}
			}
		};

		// ��������������
		mInformListView.setXListViewListener(this);
		/** ���������б��е���ļ����� */
		mInformListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				if (position > 1) { // �ų������������ظ��ࡱ�еļ����¼�,̧ͷͼƬ��ˢ����ʾռ����2�У����±�������Ҫ����2
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
		// ����ʱ���ʽ
		SimpleDateFormat formatter = new SimpleDateFormat(
				" yyyy��MM��dd��   HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String curtime = formatter.format(curDate);
		mInformListView.stopRefresh();
		mInformListView.stopLoadMore();
		// �˴�Ҫ��ȡʱ�䡣
		mInformListView.setRefreshTime(curtime);
	}

	/** ����ˢ��ʱ�ĺ�̨������ */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// �����������
				NewsTitle.clear();
				NewsHref.clear();
				pageNo = 1; // ����ҳ��Ϊ��һҳ
				getDataByUrlThread(); // ��ȡ����
				refreshormore = false; // ���õ�ǰΪˢ��ҳ��
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pageNo++; // ������һҳ
				getDataByUrlThread(); // ��ȡ��ҳ����
				refreshormore = true; // ���õ�ǰΪװ�ظ���ҳ��
			}
		}, 2000);
	}

	// �����̻߳�ȡ��ҳ����
	private void getDataByUrlThread() {
		/** ��ȡ�����б���Ӧ���ӡ� */
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Document document;
				try {
					// װ�ص�ǰ��ҳ
					// TODO ��string.xml���url
					document = Jsoup.connect(
							getResources().getString(R.string.inform_url)
									+ Integer.toString(pageNo)).get();
					// ��ȡ���а���"class"��"ablue02"
					Elements ListDiv = document.getElementsByAttributeValue(
							"class", "ablue02");
					for (Element element : ListDiv) {
						Elements links = element.getElementsByTag("a");
						for (Element link : links) {
							NewsTitle.add(link.text().toString().trim());
							// ��������
							NewsHref.add(getResources().getString(
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
				handler.sendMessage(msg);
			}
		});
		thread.start();
	}
}
