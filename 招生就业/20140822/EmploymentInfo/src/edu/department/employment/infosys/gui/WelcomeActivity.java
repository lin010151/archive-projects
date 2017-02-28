/**
 * 
 */
package edu.department.employment.infosys.gui;

import edu.department.employment.infosys.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author dragonzhu
 * 
 */
public class WelcomeActivity extends Activity {
	/** Called when the activity is first created. */

	private static final int send_msg_code = 1;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == send_msg_code) {
				RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.welcomerootlayout);
				TextView txt = new TextView(WelcomeActivity.this);
				txt.setText("\n "
								+ "                   �㶫�ڶ�ʦ��ѧԺ\n               ������ҵ����Ϣ����ϵͳ"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"
								+ "\n"

								+ "                            ������ҵ��\n                          �������ѧϵ\n             ����רҵ����ʵ����������\n   CDIO���̽���(������̿���)ʵ����\n                            ���� ����");
				txt.setTextSize(18);
				txt.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
				txt.setTextColor(Color.parseColor("#000000"));
				mainLayout.addView(txt);

				// setContentView(R.layout.main);
				Log.v("abc", "abc");

				Intent intent = new Intent(WelcomeActivity.this,
						MainFrameActivity.class);
				startActivity(intent);

				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

				finish();
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.welcome_activity);

		// 
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
					Message message = handler.obtainMessage();
					message.what = 1;
					handler.sendMessage(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
