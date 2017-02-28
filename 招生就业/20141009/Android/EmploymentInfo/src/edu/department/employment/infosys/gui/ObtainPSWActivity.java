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

	private String userid; // ѧ��֤��
	private String question; // ����
	private String answer; // ��
	private Login login; // ��¼��Ϣ

	private Handler handler; // ��Ϣ������

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requirypsw_activity); // ����UI

		// �����˳���ť
		Button exitBtn = (Button) findViewById(R.id.findbackcancel);
		// �����˳���ť��Ӧ�¼�
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
				Intent intent = getIntent();
				intent.putExtra("changeresult", false);
				// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
				ObtainPSWActivity.this.setResult(2, intent); // ���÷���1����һ��Activity
				ObtainPSWActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});

		// �����޸İ�ť
		Button obtainpswBtn = (Button) findViewById(R.id.findbackok);
		// �����޸����밴ť��Ӧ�¼�
		obtainpswBtn.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ���ı������
				EditText idedt = (EditText) findViewById(R.id.findid);
				EditText questionedt = (EditText) findViewById(R.id.findquestion);
				EditText answeredt = (EditText) findViewById(R.id.findanswer);

				// ��ȡ������ı�
				userid = idedt.getText().toString();
				question = questionedt.getText().toString();
				answer = answeredt.getText().toString();

				if (userid.isEmpty() || question.isEmpty() || answer.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ObtainPSWActivity.this);
					// ���öԻ������
					builder.setTitle("������ʾ")
					// ���öԻ���ͼ��
							.setIcon(R.drawable.gdei_logo)
							// ���öԻ�����Ϣ����
							.setMessage("�����������顣")
							// ���ȷ����ť
							.setPositiveButton("ȷ��", null)
							// ��ʾ
							.create().show();
					return;
				}

				ObtainPSWByUrlThread(); // �����̻߳�ȡ��ҳ����
			}
		});

		// ������Ϣ�����߳�
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
					// ��ʾ�û���Ȼ�󷵻���һ��
					// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
					Intent intent = getIntent();
					intent.putExtra("obtainpassword", true);
					intent.putExtra("psw", login.getUserPSW());
					// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
					ObtainPSWActivity.this.setResult(2, intent);
					ObtainPSWActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
		};
	}

	protected void ObtainPSWByUrlThread() {
		// TODO Auto-generated method stub
		/** �޸����� */
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
					params.add(new BasicNameValuePair("username", userid));
					params.add(new BasicNameValuePair("question", question));
					params.add(new BasicNameValuePair("answer", answer));
					params.add(new BasicNameValuePair("what", "obtainpsw"));

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
						JSONObject result = new JSONObject(retSrc);
						boolean success = result.getBoolean("success");
						if (success) // ����ҵ��û��Ļ�
						{
							// ����JSON����
							//Log.v("success", ""+success);
							result = new JSONObject(retSrc)
									.getJSONObject("Login");
							// ����Login���ڴ���
							login = GlobalDataInstance.GetInstance().getLogin();
							login.setUserPSW(result.getString("userPSW"));
							
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
