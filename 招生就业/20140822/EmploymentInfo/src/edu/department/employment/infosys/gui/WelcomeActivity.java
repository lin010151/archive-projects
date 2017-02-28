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
								+ "                   广东第二师范学院\n               招生就业处信息推送系统"
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

								+ "                            招生就业处\n                          计算机科学系\n             工科专业工程实践教育中心\n   CDIO工程教育(软件工程开发)实验室\n                            联合 监制");
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
