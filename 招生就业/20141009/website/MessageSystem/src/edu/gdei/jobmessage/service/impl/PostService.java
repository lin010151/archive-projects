/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IPostDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Post;
import edu.gdei.jobmessage.service.IPostService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 * 
 */
@Service("postService")
public class PostService extends AbstractService<Post> implements
		IPostService {

	@Resource(name = "postDao")
	private IPostDao postDao;

	public PostService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Post> getDao() {
		// TODO Auto-generated method stub
		return this.postDao;
	}

	/**
	 * @return the postDao
	 */
	public IPostDao getPostDao() {
		return postDao;
	}

	/**
	 * @param postDao the postDao to set
	 */
	public void setPostDao(IPostDao postDao) {
		this.postDao = postDao;
	}

	@Override
	public List<Post> findAllOfCompany(String companyid) {
		// TODO Auto-generated method stub
		return this.postDao.findAllOfCompany(companyid);
	}
}
