package edu.department.employment.infosys.gui;

import edu.department.employment.infosys.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						MainFrameActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				finish();

			}
		}, 2000);
	}
}
