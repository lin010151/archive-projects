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

	private String newpsw; 		// ������
	private String question;	// ������ʾ����
	private String answer;		// ������ʾ��

	SharedPreferences preferences; // �洢

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
		setContentView(R.layout.changepsw_activity); // ����UI

		// �����˳���ť
		Button exitBtn = (Button) findViewById(R.id.changepswexitbtn);
		// �����˳���ť��Ӧ�¼�
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
				Intent intent = getIntent();
				intent.putExtra("changeresult", false);
				// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
				ChangePSWActivity.this.setResult(1, intent); // ���÷���1����һ��Activity
				ChangePSWActivity.this.finish();
				overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
			}
		});

		// �����޸İ�ť
		Button changpswBtn = (Button) findViewById(R.id.changepswbtn);
		// �����޸����밴ť��Ӧ�¼�
		changpswBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ȡ���ı������
				EditText oldpswedt = (EditText) findViewById(R.id.oldpswed);
				EditText newpswedt = (EditText) findViewById(R.id.newpswed);
				EditText confirmpsw = (EditText) findViewById(R.id.confirmed);
				EditText questionedt=(EditText)findViewById(R.id.changequestion);
				EditText answeredt=(EditText)findViewById(R.id.changeanswer);

				// �������ľ������������ܼ����޸�����
				if (!oldpswedt.getText().toString().equals(
						GlobalDataInstance.GetInstance().getLogin()
								.getUserPSW())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangePSWActivity.this);
					// ���öԻ������
					builder.setTitle("������ʾ")
					// ���öԻ���ͼ��
							.setIcon(R.drawable.gdei_logo)
							// ���öԻ�����Ϣ����
							.setMessage("������������")
							// ���ȷ����ť
							.setPositiveButton("ȷ��", null)
							// ��ʾ
							.create().show();
					oldpswedt.setText(""); // �������
					return;
				}
				
				newpsw = newpswedt.getText().toString();		// ��ȡ������
				question=questionedt.getText().toString();		// ��ȡ����
				answer=answeredt.getText().toString();			// ��ȡ��
				// �����������ȷ�������������ܼ����޸�����
				if (!newpsw.equals(confirmpsw.getText().toString())
						|| newpsw.length()<1 
						|| confirmpsw.getText().toString().length()<1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangePSWActivity.this);
					// ���öԻ������
					builder.setTitle("������ʾ")
					// ���öԻ���ͼ��
							.setIcon(R.drawable.gdei_logo)
							// ���öԻ�����Ϣ����
							.setMessage("������������")
							// ���ȷ����ť
							.setPositiveButton("ȷ��", null)
							// ��ʾ
							.create().show();
					newpswedt.setText(""); // �������
					confirmpsw.setText(""); // �������
					return;
				}
				changePSWByUrlThread(); // �����̻߳�ȡ��ҳ����
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
					// ��ȡ������Activity֮ǰ��Activity��Ӧ��Intent
					Intent intent = getIntent();
					intent.putExtra("changepassword", true);
					// ���ø�LoginActivity�Ľ���룬�����ý���֮���˻ص�Activity
					ChangePSWActivity.this.setResult(1, intent);
					ChangePSWActivity.this.finish();
					overridePendingTransition(R.anim.in_drop, R.anim.out_fade);
				}
			}
		};
	}

	// �����߳��޸��û�����
	protected void changePSWByUrlThread() {
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
					params.add(new BasicNameValuePair("username",
							GlobalDataInstance.GetInstance().getLogin()
									.getUserID()));
					params.add(new BasicNameValuePair("password", newpsw));
					params.add(new BasicNameValuePair("question", question));
					params.add(new BasicNameValuePair("answer", answer));
					params.add(new BasicNameValuePair("what", "changepsw"));
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
						if (success) // ����ҵ��û��Ļ�
						{
							// ����Login���ڴ���
							GlobalDataInstance.GetInstance().getLogin()
									.setUserPSW(newpsw);

							preferences = getSharedPreferences("Login",
									MODE_WORLD_WRITEABLE);
							SharedPreferences.Editor editor = preferences
									.edit();
							editor.putString("userPSW", newpsw);
							// �ύ���д��������
							editor.commit();
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
