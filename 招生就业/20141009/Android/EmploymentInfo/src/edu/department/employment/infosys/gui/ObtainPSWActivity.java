/**
 * 
 */
package edu.department.employment.infosys.gui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.model.GlobalDataInstance;
import edu.department.employment.infosys.model.Login;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author dragonzhu
 * 
 */
@SuppressLint("NewApi")
public class ObtainPSWActivity extends Activity {

	private String userid; // 学生证号
	private String question; // 问题
	private String answer; // 答案
	private Login login; // 登录信息

	private Handler handler; // 消息处理器

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requirypsw_activity); // 关联UI

		// 查找退出按钮
		Button exitBtn = (Button) findViewById(R.id.findbackcancel);
		// 设置退出按钮响应事件
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取启动该Activity之前的Activity对应的Intent
				Intent intent = getIntent();
				intent.putExtra("changeresult", false);
				// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
				ObtainPSWActivity.this.setResult(2, intent); // 设置返回1的上一层Activity
				ObtainPSWActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});

		// 查找修改按钮
		Button obtainpswBtn = (Button) findViewById(R.id.findbackok);
		// 设置修改密码按钮响应事件
		obtainpswBtn.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取到文本输入框
				EditText idedt = (EditText) findViewById(R.id.findid);
				EditText questionedt = (EditText) findViewById(R.id.findquestion);
				EditText answeredt = (EditText) findViewById(R.id.findanswer);

				// 获取输入的文本
				userid = idedt.getText().toString();
				question = questionedt.getText().toString();
				answer = answeredt.getText().toString();

				if (userid.isEmpty() || question.isEmpty() || answer.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ObtainPSWActivity.this);
					// 设置对话框标题
					builder.setTitle("错误提示")
					// 设置对话框图标
							.setIcon(R.drawable.gdei_logo)
							// 设置对话框消息内容
							.setMessage("输入有误，请检查。")
							// 添加确定按钮
							.setPositiveButton("确定", null)
							// 显示
							.create().show();
					return;
				}

				ObtainPSWByUrlThread(); // 开启线程获取网页数据
			}
		});

		// 创建消息处理线程
		handler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					// 提示用户，然后返回上一层
					// 获取启动该Activity之前的Activity对应的Intent
					Intent intent = getIntent();
					intent.putExtra("obtainpassword", true);
					intent.putExtra("psw", login.getUserPSW());
					// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
					ObtainPSWActivity.this.setResult(2, intent);
					ObtainPSWActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
		};
	}

	protected void ObtainPSWByUrlThread() {
		// TODO Auto-generated method stub
		/** 修改密码 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// 连接登录后台
					HttpClient httpclient = new DefaultHttpClient(); // 创建新的客户端
					// 网址uri
					String uri = getResources().getString(R.string.web_url)
							+ "/studentjson";
					HttpPost httppost = new HttpPost(uri);
					// 设置HTTP POST请求参数必须用NameValuePair对象
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", userid));
					params.add(new BasicNameValuePair("question", question));
					params.add(new BasicNameValuePair("answer", answer));
					params.add(new BasicNameValuePair("what", "obtainpsw"));

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
						JSONObject result = new JSONObject(retSrc);
						boolean success = result.getBoolean("success");
						if (success) // 如果找到用户的话
						{
							// 生成JSON对象
							//Log.v("success", ""+success);
							result = new JSONObject(retSrc)
									.getJSONObject("Login");
							// 保存Login在内存中
							login = GlobalDataInstance.GetInstance().getLogin();
							login.setUserPSW(result.getString("userPSW"));
							
							Message msg = new Message();
							// 发送消息更新视图
							msg.what = 0x123;
							msg.obj = null;
							handler.sendMessage(msg);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

		}).start();
	}
}
