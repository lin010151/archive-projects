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

	private Handler handler; // ��Ϣ������

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
		setContentView(R.layout.studentinfo_activity); // ����UI

		// ��ȡѧ����Ϣ
		student = GlobalDataInstance.GetInstance().getStudent();

		if (student.getStuid().isEmpty()) {
			getDataByUrlThread(); // �����̻߳�ȡ��ҳ����
		} else
			setStudentInfo();
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
					setStudentInfo();
				}
			}
		};
	}

	private void getDataByUrlThread() {
		// TODO Auto-generated method stub
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
					params.add(new BasicNameValuePair("username",
							GlobalDataInstance.GetInstance().getLogin().getUserID()));
					params.add(new BasicNameValuePair("what", "info"));

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
							result = new JSONObject(retSrc)
									.getJSONObject("student");
							// ����Student���ڴ���
							student.setStuid(result.getString("stuID"));	// ��ȡѧ��
							student.setStuname(result.getString("stuName"));// ��ȡ����
							if (result.getString("stuSex").equals("0"))		// ��ȡ�Ա�
								student.setSex("��");
							else
								student.setSex("Ů");
							student.setIdcard(result.getString("stuIDCard"));// ��ȡ���֤��
							if (result.getString("stuPolitical").equals("0"))// ��ȡ������ò
								student.setPolitical("�й���������Ա");
							else if (result.getString("stuPolitical").equals("1"))
								student.setPolitical("�й�Ԥ����Ա");
							else if (result.getString("stuPolitical").equals("2"))
								student.setPolitical("������Ա");
							else
								student.setPolitical("Ⱥ��");
							student.setEmail(result.getString("stuEmail"));	// ��ȡ�����ַ
							JSONObject tmp=result.getJSONObject("major");	// ��ȡרҵ
							student.setMajor(tmp.getString("majorName"));
							tmp=result.getJSONObject("address");			// ��ȡ��Դ��
							student.setAddress(tmp.getString("addressName"));
							GlobalDataInstance.GetInstance()
									.setStudent(student);

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

	private void setStudentInfo() {
		// TODO Auto-generated method stub
		// ����TextView
		TextView id = (TextView) findViewById(R.id.idinfo);
		id.setText(student.getStuid());

		// ����TextView
		TextView name = (TextView) findViewById(R.id.nameinfo);
		name.setText(student.getStuname());

		// ����TextView
		TextView sex = (TextView) findViewById(R.id.sexinfo);
		sex.setText(student.getSex());

		// ����TextView
		TextView idcard = (TextView) findViewById(R.id.idcardinfo);
		idcard.setText(student.getIdcard());

		// ����TextView
		TextView polity = (TextView) findViewById(R.id.polityinfo);
		polity.setText(student.getPolitical());

		// ����TextView
		TextView major = (TextView) findViewById(R.id.majorinfo);
		major.setText(student.getMajor());

		// ����TextView
		TextView addr = (TextView) findViewById(R.id.addrinfo);
		addr.setText(student.getAddress());
	}

}
