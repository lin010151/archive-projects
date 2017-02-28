/**
 * 
 */
package edu.gdei.jobmessage.service;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Visit;

/**
 * @author dragonzhu
 *
 */
public interface IVisitService extends IDao<Visit> {
	public boolean saveVisit(String stuid, String postid);
}
