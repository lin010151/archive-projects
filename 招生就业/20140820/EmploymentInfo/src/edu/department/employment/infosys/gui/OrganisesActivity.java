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
public class OrganisesActivity extends Activity {

	private XListView mInOrganiseListView;	// 校内招聘专场列表
	private XListView mOutOrganiseListView;	// 校外招聘专场列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organises_activity);

    	// 设置校内招聘专场信息页面
    	mInOrganiseListView=(XListView) findViewById(R.id.inorganiseslistView);
    	mInOrganiseListView.setPullLoadEnable(true);
    	// 设置校外招聘专场信息页面
    	mOutOrganiseListView=(XListView) findViewById(R.id.outorganiseslistView);
    	mOutOrganiseListView.setPullLoadEnable(true);
	}
}
