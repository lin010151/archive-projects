/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.gdei.jobmessage.dao.IVisitDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Visit;
import edu.gdei.jobmessage.service.IVisitService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("visitService")
@Transactional
public class VisitService extends AbstractService<Visit> implements
		IVisitService {
	
	@Resource(name = "visitDao")
	private IVisitDao visitDao;

	/**
	 * 
	 */
	public VisitService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Visit> getDao() {
		// TODO Auto-generated method stub
		return this.visitDao;
	}

	/**
	 * @return the visitDao
	 */
	public IVisitDao getVisitDao() {
		return visitDao;
	}

	/**
	 * @param visitDao the visitDao to set
	 */
	public void setVisitDao(IVisitDao visitDao) {
		this.visitDao = visitDao;
	}

	@Override
	public boolean saveVisit(String stuid, String postid) {
		// TODO Auto-generated method stub
		return this.visitDao.saveVisit(stuid, postid);
	}

}
