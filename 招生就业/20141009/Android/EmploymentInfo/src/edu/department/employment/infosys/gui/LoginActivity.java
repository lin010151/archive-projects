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
import edu.department.employment.infosys.model.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * @author TOSHIBA
 * 
 */
public class LoginActivity extends Activity {

	// private Thread thread; // 读取数据线程

	private String userID; // 用户账号
	private String userPSW; // 用户密码

	private Login login; // 登录信息
	private Student student; // 学生信息

	private boolean rememberpsw; // 记住密码

	SharedPreferences preferences; // 存储
	
	private Handler handler;	// 消息处理器

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity); // 关联UI

		// 查找登录按钮
		Button loginBtn = (Button) findViewById(R.id.login);
		// 设置登录按钮响应事件
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取到文本输入框
				EditText userET = (EditText) findViewById(R.id.InUserID);
				EditText pswET = (EditText) findViewById(R.id.InUserPSW);
				// 获取到输入的账号密码
				userID = userET.getText().toString().trim();
				userPSW = pswET.getText().toString().trim();

				// 查找记住密码复选框
				CheckBox ckbtn = (CheckBox) findViewById(R.id.chkRemenberPwd);
				rememberpsw = ckbtn.isChecked(); // 获取用户是否点选记住密码复选框

				getDataByUrlThread(); // 开启线程获取网页数据
			}
		});
		
		// 查找退出按钮
		Button exitBtn = (Button)findViewById(R.id.exit);
		// 设置退出按钮响应事件
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 获取启动该Activity之前的Activity对应的Intent
				Intent intent = getIntent();
				intent.putExtra("loginresult", false);
				// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
				LoginActivity.this.setResult(0, intent);
				LoginActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});
		
		// 创建消息处理线程
		handler = new Handler() {

			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					// 获取启动该Activity之前的Activity对应的Intent
					Intent intent = getIntent();
					intent.putExtra("loginresult", true);
					// 设置该LoginActivity的结果码，并设置结束之后退回的Activity
					LoginActivity.this.setResult(0, intent);
					LoginActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
			
		};
	}

	// 利用线程获取网页数据
	@SuppressLint("NewApi")
	private void getDataByUrlThread() {
		/** 获取登录数据 */
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
					params.add(new BasicNameValuePair("username", userID));
					params.add(new BasicNameValuePair("password", userPSW));
					params.add(new BasicNameValuePair("what", "login"));
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
						if (success) {
							// 取出学生信息
							result=result.getJSONObject("student");
							JSONObject loginres=result.getJSONObject("login");
							// 保存Login在内存中
							login = new Login();
							login.setUserID(loginres.getString("userID"));
							login.setUserPSW(loginres.getString("userPSW"));
							login.setQuestion(loginres.getString("question"));
							login.setAnswer(loginres.getString("answer"));
							GlobalDataInstance.GetInstance().setLogin(login);
							Log.v("pass", login.getUserPSW());

							// 保存学生信息在内存中
							student = new Student();
							student.setStuid(result.getString("stuID"));
							student.setStuname(result.getString("stuName"));
							if (result.getString("stuSex").equals("0"))
								student.setSex("男");
							else
								student.setSex("女");
							student.setIdcard(result.getString("stuIDCard"));
							if (result.getString("stuPolitical").equals("0"))
								student.setPolitical("中国共产党党员");
							else if (result.getString("stuPolitical").equals("1"))
								student.setPolitical("中共预备党员");
							else if (result.getString("stuPolitical").equals("2"))
								student.setPolitical("共青团员");
							else
								student.setPolitical("群众");
							student.setEmail(result.getString("stuEmail"));
							JSONObject tmp=result.getJSONObject("major");
							student.setMajor(tmp.getString("majorName"));
							tmp=result.getJSONObject("address");
							student.setAddress(tmp.getString("addressName"));
							GlobalDataInstance.GetInstance()
									.setStudent(student);

							if (rememberpsw) // 如果用户选择了记住密码的话
							{
								preferences = getSharedPreferences("Login",
										MODE_WORLD_WRITEABLE);
								SharedPreferences.Editor editor = preferences
										.edit();
								editor.putString("userID", login.getUserID());
								editor.putString("userPSW", login.getUserPSW());
								editor.putString("question",
										login.getQuestion());
								editor.putString("answer", login.getAnswer());
								editor.putString("name", student.getStuname());
								editor.putString("email", student.getEmail());
								// 提交所有存入的数据
								editor.commit();
							}
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
