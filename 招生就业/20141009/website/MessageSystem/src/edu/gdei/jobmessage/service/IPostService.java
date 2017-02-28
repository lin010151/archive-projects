/**
 * 
 */
package edu.gdei.jobmessage.service;

import java.util.List;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Post;

/**
 * @author dragonzhu
 *
 */
public interface IPostService extends IDao<Post> {
	public List<Post> findAllOfCompany(String companyid);
}
