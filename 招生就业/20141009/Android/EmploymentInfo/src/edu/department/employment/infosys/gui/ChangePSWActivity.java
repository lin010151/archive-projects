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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author dragonzhu
 * 
 */
public class ChangePSWActivity extends Activity {

	private String newpsw; 		// 新密码
	private String question;	// 密码提示问题
	private String answer;		// 密码提示答案

	SharedPreferences preferences; // 存储

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
		setContentView(R.layout.changepsw_activity); // 关联UI

		// 查找退出按钮
		Button exitBtn = (Button) findViewById(R.id.changepswexitbtn);
		// 设置退出按钮响应事件
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取启动该Activity之前的Activity对应的Intent
				Intent intent = getIntent();
				intent.putExtra("changeresult", false);
				// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
				ChangePSWActivity.this.setResult(1, intent); // 设置返回1的上一层Activity
				ChangePSWActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});

		// 查找修改按钮
		Button changpswBtn = (Button) findViewById(R.id.changepswbtn);
		// 设置修改密码按钮响应事件
		changpswBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取到文本输入框
				EditText oldpswedt = (EditText) findViewById(R.id.oldpswed);
				EditText newpswedt = (EditText) findViewById(R.id.newpswed);
				EditText confirmpsw = (EditText) findViewById(R.id.confirmed);
				EditText questionedt=(EditText)findViewById(R.id.changequestion);
				EditText answeredt=(EditText)findViewById(R.id.changeanswer);

				// 如果输入的旧密码有误，则不能继续修改密码
				if (!oldpswedt.getText().toString().equals(
						GlobalDataInstance.GetInstance().getLogin()
								.getUserPSW())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangePSWActivity.this);
					// 设置对话框标题
					builder.setTitle("错误提示")
					// 设置对话框图标
							.setIcon(R.drawable.gdei_logo)
							// 设置对话框消息内容
							.setMessage("输入密码有误")
							// 添加确定按钮
							.setPositiveButton("确定", null)
							// 显示
							.create().show();
					oldpswedt.setText(""); // 清空密码
					return;
				}
				
				newpsw = newpswedt.getText().toString();		// 获取新密码
				question=questionedt.getText().toString();		// 获取问题
				answer=answeredt.getText().toString();			// 获取答案
				// 如果新密码与确认密码有误，则不能继续修改密码
				if (!newpsw.equals(confirmpsw.getText().toString())
						|| newpsw.length()<1 
						|| confirmpsw.getText().toString().length()<1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangePSWActivity.this);
					// 设置对话框标题
					builder.setTitle("错误提示")
					// 设置对话框图标
							.setIcon(R.drawable.gdei_logo)
							// 设置对话框消息内容
							.setMessage("输入密码有误")
							// 添加确定按钮
							.setPositiveButton("确定", null)
							// 显示
							.create().show();
					newpswedt.setText(""); // 清空密码
					confirmpsw.setText(""); // 清空密码
					return;
				}
				changePSWByUrlThread(); // 开启线程获取网页数据
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
					// 获取启动该Activity之前的Activity对应的Intent
					Intent intent = getIntent();
					intent.putExtra("changepassword", true);
					// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
					ChangePSWActivity.this.setResult(1, intent);
					ChangePSWActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
		};
	}

	// 利用线程修改用户密码
	protected void changePSWByUrlThread() {
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
					params.add(new BasicNameValuePair("username",
							GlobalDataInstance.GetInstance().getLogin()
									.getUserID()));
					params.add(new BasicNameValuePair("password", newpsw));
					params.add(new BasicNameValuePair("question", question));
					params.add(new BasicNameValuePair("answer", answer));
					params.add(new BasicNameValuePair("what", "changepsw"));
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
						// 生成JSON对象
						JSONObject result = new JSONObject(retSrc);
						boolean success = result.getBoolean("success");
						if (success) // 如果找到用户的话
						{
							// 保存Login在内存中
							GlobalDataInstance.GetInstance().getLogin()
									.setUserPSW(newpsw);

							preferences = getSharedPreferences("Login",
									MODE_WORLD_WRITEABLE);
							SharedPreferences.Editor editor = preferences
									.edit();
							editor.putString("userPSW", newpsw);
							// 提交所有存入的数据
							editor.commit();
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
