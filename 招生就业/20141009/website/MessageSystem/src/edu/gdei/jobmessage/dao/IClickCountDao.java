/**
 * 
 */
package edu.gdei.jobmessage.dao;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.ClickCount;

/**
 * @author dragonzhu
 *
 */
public interface IClickCountDao extends IDao<ClickCount> {
	public void saveClick(String id);
}
