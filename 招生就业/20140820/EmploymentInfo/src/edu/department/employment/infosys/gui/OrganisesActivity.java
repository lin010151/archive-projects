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

	private XListView mInOrganiseListView;	// У����Ƹר���б�
	private XListView mOutOrganiseListView;	// У����Ƹר���б�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organises_activity);

    	// ����У����Ƹר����Ϣҳ��
    	mInOrganiseListView=(XListView) findViewById(R.id.inorganiseslistView);
    	mInOrganiseListView.setPullLoadEnable(true);
    	// ����У����Ƹר����Ϣҳ��
    	mOutOrganiseListView=(XListView) findViewById(R.id.outorganiseslistView);
    	mOutOrganiseListView.setPullLoadEnable(true);
	}
}
