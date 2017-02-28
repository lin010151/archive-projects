/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IPostDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Post;

/**
 * @author dragonzhu
 * 
 */
@Repository("postDao")
public class PostDao extends AbstractHibernateDao<Post> implements IPostDao {

	public PostDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Post.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> findAllOfCompany(String companyid) {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery(
				"from Post where companyID=" + companyid
						+ " order by releaseTime desc").list();
	}

}
