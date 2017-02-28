/**
 * 
 */
package edu.department.employment.infosys.gui;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.model.GlobalDataInstance;
import edu.department.employment.infosys.model.Student;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author TOSHIBA
 * 
 */
public class LoginActivity extends Activity {

	// private Thread thread; // ��ȡ�����߳�

	private String userID; // �û��˺�
	private String userPSW; // �û�����
	// private boolean success; // ��¼�ɹ�

	Document doc; // ���Ի�ȡ��̨���ص�Ӧ��

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

				getDataByUrlThread(); // �����̻߳�ȡ��ҳ����
			}
		});
	}

	// �����̻߳�ȡ��ҳ����
	private void getDataByUrlThread() {
		/** ��ȡ�����б���Ӧ���ӡ� */
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Message msg = new Message();
				// ���ӵ�¼��̨PHP��ҳ
				try {
					Document doc = (Document) Jsoup
							.connect(
									getResources().getString(R.string.php_url)
											+ "/loginTrue.php")
							.data("userID", userID).data("userPSW", userPSW)
							.timeout(3000)
							.post();
					// doc=res.parse();
					// // ��ȡ���صĽ��
					Elements links = doc.getElementsByTag("a");
					if (links.size()>0)					// ��¼�ɹ�,��ѧ����Ϣ��ȡ����
					{
						Student student=new Student();
						student.setStuname(links.get(0).text().toString().trim());	// ѧ������
						student.setIdcard(links.get(1).text().toString().trim());	// ѧ�����֤����
						student.setSex(links.get(2).text().toString().trim());		// ѧ���Ա�
						student.setMajor(links.get(3).text().toString().trim());	// ѧ��רҵ
						student.setNation(links.get(4).text().toString().trim());	// ѧ������
						student.setProvince(links.get(5).text().toString().trim());	// ѧ��ʡ��
						student.setCity(links.get(6).text().toString().trim());		// ѧ������
						student.setStuid(userID);									// ѧ���˺�
						student.setStupsw(userPSW);									// ѧ������
						GlobalDataInstance.GetInstance().setStudent(student);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
