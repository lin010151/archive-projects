/**
 * 
 */
package edu.gdei.jobmessage.service;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.ClickCount;

/**
 * @author dragonzhu
 *
 */
public interface IClickCountService extends IDao<ClickCount> {
	public void saveClick(String id);
}
