/**
 * 
 */
package edu.department.employment.infosys.gui;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.XListview.XListView;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author dragonzhu
 *
 */
public class InternshipActivity extends Activity {
	
	private XListView mIntershipListView;	// 实习信息列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internship_activity);

    	// 设置实习信息页面
    	mIntershipListView=(XListView) findViewById(R.id.intershiplistView);
    	mIntershipListView.setPullLoadEnable(true);
	}
}
