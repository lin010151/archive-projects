package edu.department.employment.infosys.gui;

import edu.department.employment.infosys.R;
import android.app.Activity;
// import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	    //内部信息推送界面的跳转
	       Button mLoginItem=(Button) findViewById(R.id.btnLogin2);
	       // mLoginItem.setOnClickListener(Listener);
	        mLoginItem.setOnClickListener(new OnClickListener()
	        {
			public void onClick(View v) 
			{
				//Intent intent = new Intent();
				//intent.setClass(Login.this, Interior.class);
				//startActivity(intent);
			}
	});
	}
}

