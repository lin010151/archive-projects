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

	// private Thread thread; // ��ȡ�����߳�

	private String userID; // �û��˺�
	private String userPSW; // �û�����

	private Login login; // ��¼��Ϣ
	private Student student; // ѧ����Ϣ

	private boolean rememberpsw; // ��ס����

	SharedPreferences preferences; // �洢
	
	private Handler handler;	// ��Ϣ������

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity); // ����UI

		// ���ҵ�¼��ť
		Button loginBtn = (Button) findViewById(R.id.login);
		// ���õ�¼��ť��Ӧ�¼�
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ���ı������
				EditText userET = (EditText) findViewById(R.id.InUserID);
				EditText pswET = (EditText) findViewById(R.id.InUserPSW);
				// ��ȡ��������˺�����
				userID = userET.getText().toString().trim();
				userPSW = pswET.getText().toString().trim();

				// ���Ҽ�ס���븴ѡ��
				CheckBox ckbtn = (CheckBox) findViewById(R.id.chkRemenberPwd);
				rememberpsw = ckbtn.isChecked(); // ��ȡ�û��Ƿ��ѡ��ס���븴ѡ��

				getDataByUrlThread(); // �����̻߳�ȡ��ҳ����
			}
		});
		
		// �����˳���ť
		Button exitBtn = (Button)findViewById(R.id.exit);
		// �����˳���ť��Ӧ�¼�
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
				Intent intent = getIntent();
				intent.putExtra("loginresult", false);
				// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
				LoginActivity.this.setResult(0, intent);
				LoginActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});
		
		// ������Ϣ�����߳�
		handler = new Handler() {

			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
					Intent intent = getIntent();
					intent.putExtra("loginresult", true);
					// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
					LoginActivity.this.setResult(0, intent);
					LoginActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
			
		};
	}

	// �����̻߳�ȡ��ҳ����
	@SuppressLint("NewApi")
	private void getDataByUrlThread() {
		/** ��ȡ��¼���� */
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// ���ӵ�¼��̨
					HttpClient httpclient = new DefaultHttpClient(); // �����µĿͻ���
					// ��ַuri
					String uri = getResources().getString(R.string.web_url)
							+ "/studentjson";
					HttpPost httppost = new HttpPost(uri);
					// ����HTTP POST�������������NameValuePair����
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", userID));
					params.add(new BasicNameValuePair("password", userPSW));
					params.add(new BasicNameValuePair("what", "login"));
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
						// ����JSON����
						JSONObject result = new JSONObject(retSrc);
						boolean success = result.getBoolean("success");
						if (success) {
							// ȡ��ѧ����Ϣ
							result=result.getJSONObject("student");
							JSONObject loginres=result.getJSONObject("login");
							// ����Login���ڴ���
							login = new Login();
							login.setUserID(loginres.getString("userID"));
							login.setUserPSW(loginres.getString("userPSW"));
							login.setQuestion(loginres.getString("question"));
							login.setAnswer(loginres.getString("answer"));
							GlobalDataInstance.GetInstance().setLogin(login);
							Log.v("pass", login.getUserPSW());

							// ����ѧ����Ϣ���ڴ���
							student = new Student();
							student.setStuid(result.getString("stuID"));
							student.setStuname(result.getString("stuName"));
							if (result.getString("stuSex").equals("0"))
								student.setSex("��");
							else
								student.setSex("Ů");
							student.setIdcard(result.getString("stuIDCard"));
							if (result.getString("stuPolitical").equals("0"))
								student.setPolitical("�й���������Ա");
							else if (result.getString("stuPolitical").equals("1"))
								student.setPolitical("�й�Ԥ����Ա");
							else if (result.getString("stuPolitical").equals("2"))
								student.setPolitical("������Ա");
							else
								student.setPolitical("Ⱥ��");
							student.setEmail(result.getString("stuEmail"));
							JSONObject tmp=result.getJSONObject("major");
							student.setMajor(tmp.getString("majorName"));
							tmp=result.getJSONObject("address");
							student.setAddress(tmp.getString("addressName"));
							GlobalDataInstance.GetInstance()
									.setStudent(student);

							if (rememberpsw) // ����û�ѡ���˼�ס����Ļ�
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
								// �ύ���д��������
								editor.commit();
							}
							Message msg = new Message();
							// ������Ϣ������ͼ
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
