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
	
	private XListView mIntershipListView;	// ʵϰ��Ϣ�б�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internship_activity);

    	// ����ʵϰ��Ϣҳ��
    	mIntershipListView=(XListView) findViewById(R.id.intershiplistView);
    	mIntershipListView.setPullLoadEnable(true);
	}
}
