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
import edu.department.employment.infosys.model.Student;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * @author dragonzhu
 * 
 */
public class StudentInfoActivity extends Activity {

	private Student student;

	private Handler handler; // 消息处理器

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentinfo_activity); // 关联UI

		// 获取学生信息
		student = GlobalDataInstance.GetInstance().getStudent();

		if (student.getStuid().isEmpty()) {
			getDataByUrlThread(); // 开启线程获取网页数据
		} else
			setStudentInfo();
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
					setStudentInfo();
				}
			}
		};
	}

	private void getDataByUrlThread() {
		// TODO Auto-generated method stub
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
					params.add(new BasicNameValuePair("username",
							GlobalDataInstance.GetInstance().getLogin().getUserID()));
					params.add(new BasicNameValuePair("what", "info"));

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
							result = new JSONObject(retSrc)
									.getJSONObject("student");
							// 保存Student在内存中
							student.setStuid(result.getString("stuID"));	// 获取学号
							student.setStuname(result.getString("stuName"));// 获取姓名
							if (result.getString("stuSex").equals("0"))		// 获取性别
								student.setSex("男");
							else
								student.setSex("女");
							student.setIdcard(result.getString("stuIDCard"));// 获取身份证号
							if (result.getString("stuPolitical").equals("0"))// 获取政治面貌
								student.setPolitical("中国共产党党员");
							else if (result.getString("stuPolitical").equals("1"))
								student.setPolitical("中共预备党员");
							else if (result.getString("stuPolitical").equals("2"))
								student.setPolitical("共青团员");
							else
								student.setPolitical("群众");
							student.setEmail(result.getString("stuEmail"));	// 获取邮箱地址
							JSONObject tmp=result.getJSONObject("major");	// 获取专业
							student.setMajor(tmp.getString("majorName"));
							tmp=result.getJSONObject("address");			// 获取生源地
							student.setAddress(tmp.getString("addressName"));
							GlobalDataInstance.GetInstance()
									.setStudent(student);

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

	private void setStudentInfo() {
		// TODO Auto-generated method stub
		// 查找TextView
		TextView id = (TextView) findViewById(R.id.idinfo);
		id.setText(student.getStuid());

		// 查找TextView
		TextView name = (TextView) findViewById(R.id.nameinfo);
		name.setText(student.getStuname());

		// 查找TextView
		TextView sex = (TextView) findViewById(R.id.sexinfo);
		sex.setText(student.getSex());

		// 查找TextView
		TextView idcard = (TextView) findViewById(R.id.idcardinfo);
		idcard.setText(student.getIdcard());

		// 查找TextView
		TextView polity = (TextView) findViewById(R.id.polityinfo);
		polity.setText(student.getPolitical());

		// 查找TextView
		TextView major = (TextView) findViewById(R.id.majorinfo);
		major.setText(student.getMajor());

		// 查找TextView
		TextView addr = (TextView) findViewById(R.id.addrinfo);
		addr.setText(student.getAddress());
	}

}
