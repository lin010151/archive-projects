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
	private Handler mhandler; // 声明一个Handler对象
	String result = ""; // 声明一个代表显示结果的字符串
	String url = "";// 声明一个接收从上一个url的字符串

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailnotice_activity);

		// 初始化控件
		noticewebview = (WebView) findViewById(R.id.noticewebView);
		
		// 获得前一个窗口传过来的url
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		url = bundle.getString("url");

		// 创建一个新线程，用于发送并获取GET请求，并在重写的run()方法中调用send3()方法发送并读取信息
		new Thread(new Runnable() {
			public void run() {
				send();// 调用send()方法
				Message m = mhandler.obtainMessage(); // 获取一个Message
				mhandler.sendMessage(m); // 发送消息
			}

		}).start();// 开启线程

		// 创建一个Handler对象，在重写的handlerMessage()方法中，当变量result3不为空时，对其作出其他处理
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (result != null) {
					int a = result.indexOf("档案查询</a></li>");// 找出第一个下标
					int b = result
							.indexOf("td align=\"center\"><span class=\"pl01\">COPYRIGHT");
					// 找出第二个下标
					if (a == -1) {
						StringBuilder sb = new StringBuilder();// 处理字符串
						sb.append("<div>亲~~~您暂时无法查看哦</div");// 有些消息截取不到，就设个提示
						noticewebview.loadDataWithBaseURL(null, sb.toString(),
								null, "utf-8", null);// 在webview显示
														// “亲~~~您暂时无法查看哦”
						return;// 一定要先判断找不找得到下标
					}
					if (b == -1) {
						return;// 一定要先判断找不找得到下标
					}

					String s = result.substring(a + 4, b);// 截取第一个下标和第二个下标之间的内容
					WebSettings webSettings = noticewebview.getSettings();// webView:类WebView的实例
																			// 
					webSettings.setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小

					webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);// SINGLE_COLUMN////NARROW_COLUMNS
					webSettings.setTextSize(TextSize.LARGEST);// 调整字体大小
					webSettings.setUseWideViewPort(true);// 为了显示的内容能适应于屏幕大小
					webSettings.setLoadWithOverviewMode(true);// 为了显示的内容能适应于屏幕大小，

					noticewebview.loadDataWithBaseURL(null, s, null, "utf-8", null);// 显示截取的内容
				}
				super.handleMessage(msg);
			}
		};
	}

	public void send() {
		String target = url;// 要提交的目标地址
		HttpClient httpclient = new DefaultHttpClient(); // 创建HttpClient对象
		HttpGet httpRequest = new HttpGet(target); // 创建HttpGet连接对象
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpRequest);// 执行HttpClient请求
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());// 获取返回的字符串
			} else {
				result = "请求失败！";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();// 输出异常消息
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
