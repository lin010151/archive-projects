package edu.department.employment.infosys.gui;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.department.employment.infosys.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;

public class DetailNoticeActivity extends Activity {
	private WebView noticewebview;
	private Handler mhandler; // ����һ��Handler����
	String result = ""; // ����һ��������ʾ������ַ���
	String url = "";// ����һ�����մ���һ��url���ַ���

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailnotice_activity);

		// ��ʼ���ؼ�
		noticewebview = (WebView) findViewById(R.id.noticewebView);
		
		// ���ǰһ�����ڴ�������url
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		url = bundle.getString("url");

		// ����һ�����̣߳����ڷ��Ͳ���ȡGET���󣬲�����д��run()�����е���send3()�������Ͳ���ȡ��Ϣ
		new Thread(new Runnable() {
			public void run() {
				send();// ����send()����
				Message m = mhandler.obtainMessage(); // ��ȡһ��Message
				mhandler.sendMessage(m); // ������Ϣ
			}

		}).start();// �����߳�

		// ����һ��Handler��������д��handlerMessage()�����У�������result3��Ϊ��ʱ������������������
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result != null) {
					int a = result.indexOf("������ѯ</a></li>");// �ҳ���һ���±�
					int b = result
							.indexOf("td align=\"center\"><span class=\"pl01\">COPYRIGHT");
					// �ҳ��ڶ����±�
					if (a == -1) {
						StringBuilder sb = new StringBuilder();// �����ַ���
						sb.append("<div>��~~~����ʱ�޷��鿴Ŷ</div");// ��Щ��Ϣ��ȡ�������������ʾ
						noticewebview.loadDataWithBaseURL(null, sb.toString(),
								null, "utf-8", null);// ��webview��ʾ
														// ����~~~����ʱ�޷��鿴Ŷ��
						return;// һ��Ҫ���ж��Ҳ��ҵõ��±�
					}
					if (b == -1) {
						return;// һ��Ҫ���ж��Ҳ��ҵõ��±�
					}

					String s = result.substring(a + 4, b);// ��ȡ��һ���±�͵ڶ����±�֮�������
					WebSettings webSettings = noticewebview.getSettings();// webView:��WebView��ʵ��
																			// 
					webSettings.setBuiltInZoomControls(true);// ����WebView�ɴ����Ŵ���С

					webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);// SINGLE_COLUMN////NARROW_COLUMNS
					webSettings.setTextSize(TextSize.LARGEST);// ���������С
					webSettings.setUseWideViewPort(true);// Ϊ����ʾ����������Ӧ����Ļ��С
					webSettings.setLoadWithOverviewMode(true);// Ϊ����ʾ����������Ӧ����Ļ��С��

					noticewebview.loadDataWithBaseURL(null, s, null, "utf-8", null);// ��ʾ��ȡ������
				}
				super.handleMessage(msg);
			}
		};
	}

	public void send() {
		String target = url;// Ҫ�ύ��Ŀ���ַ
		HttpClient httpclient = new DefaultHttpClient(); // ����HttpClient����
		HttpGet httpRequest = new HttpGet(target); // ����HttpGet���Ӷ���
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);// ִ��HttpClient����
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());// ��ȡ���ص��ַ���
			} else {
				result = "����ʧ�ܣ�";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();// ����쳣��Ϣ
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
