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

	// private Thread thread; // 读取数据线程

	private String userID; // 用户账号
	private String userPSW; // 用户密码
	// private boolean success; // 登录成功

	Document doc; // 用以获取后台发回的应答

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

				getDataByUrlThread(); // 开启线程获取网页数据
			}
		});
	}

	// 利用线程获取网页数据
	private void getDataByUrlThread() {
		/** 获取新闻列表及对应链接。 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Message msg = new Message();
				// 连接登录后台PHP网页
				try {
					Document doc = (Document) Jsoup
							.connect(
									getResources().getString(R.string.php_url)
											+ "/loginTrue.php")
							.data("userID", userID).data("userPSW", userPSW)
							.timeout(3000)
							.post();
					// doc=res.parse();
					// // 获取返回的结果
					Elements links = doc.getElementsByTag("a");
					if (links.size()>0)					// 登录成功,把学生信息读取出来
					{
						Student student=new Student();
						student.setStuname(links.get(0).text().toString().trim());	// 学生名字
						student.setIdcard(links.get(1).text().toString().trim());	// 学生身份证号码
						student.setSex(links.get(2).text().toString().trim());		// 学生性别
						student.setMajor(links.get(3).text().toString().trim());	// 学生专业
						student.setNation(links.get(4).text().toString().trim());	// 学生民族
						student.setProvince(links.get(5).text().toString().trim());	// 学生省份
						student.setCity(links.get(6).text().toString().trim());		// 学生城市
						student.setStuid(userID);									// 学生账号
						student.setStupsw(userPSW);									// 学生密码
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
