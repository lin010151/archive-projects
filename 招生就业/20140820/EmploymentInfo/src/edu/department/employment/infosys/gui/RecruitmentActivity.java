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
public class RecruitmentActivity extends Activity {
	
	private XListView mInRecruitListView;	// �ڲ���Ƹ��Ϣ�б�
	private XListView mOutRecruitListView;	// �ⲿ��Ƹ��Ϣ�б�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruitment_activity);
		
		// �����ڲ���Ƹ��Ϣҳ��
    	mInRecruitListView = (XListView) findViewById(R.id.inrecruitlistView);
    	mInRecruitListView.setPullLoadEnable(true);
    	// �����ⲿ��Ƹ��Ϣҳ��
    	mOutRecruitListView = (XListView) findViewById(R.id.outrecruitlistView);
    	mOutRecruitListView.setPullLoadEnable(true);
	}
}
