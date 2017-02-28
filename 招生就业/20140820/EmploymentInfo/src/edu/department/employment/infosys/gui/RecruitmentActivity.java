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
	
	private XListView mInRecruitListView;	// 内部招聘信息列表
	private XListView mOutRecruitListView;	// 外部招聘信息列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruitment_activity);
		
		// 设置内部招聘信息页面
    	mInRecruitListView = (XListView) findViewById(R.id.inrecruitlistView);
    	mInRecruitListView.setPullLoadEnable(true);
    	// 设置外部招聘信息页面
    	mOutRecruitListView = (XListView) findViewById(R.id.outrecruitlistView);
    	mOutRecruitListView.setPullLoadEnable(true);
	}
}
