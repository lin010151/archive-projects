/**
 * 
 */
package edu.gdei.jobmessage.dao;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Visit;

/**
 * @author dragonzhu
 *
 */
public interface IVisitDao extends IDao<Visit> {
	public boolean saveVisit(String stuid, String postid);
}
