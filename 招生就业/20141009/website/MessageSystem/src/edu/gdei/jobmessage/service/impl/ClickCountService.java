/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IClickCountDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.ClickCount;
import edu.gdei.jobmessage.service.IClickCountService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("clickcountService")
public class ClickCountService extends AbstractService<ClickCount> implements
		IClickCountService {
	
	@Resource(name = "clickcountDao")
	private IClickCountDao clickcountDao;

	/**
	 * 
	 */
	public ClickCountService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<ClickCount> getDao() {
		// TODO Auto-generated method stub
		return this.clickcountDao;
	}

	/**
	 * @return the clickcountDao
	 */
	public IClickCountDao getClickcountDao() {
		return clickcountDao;
	}

	/**
	 * @param clickcountDao the clickcountDao to set
	 */
	public void setClickcountDao(IClickCountDao clickcountDao) {
		this.clickcountDao = clickcountDao;
	}

	@Override
	public void saveClick(String id) {
		// TODO Auto-generated method stub
		this.clickcountDao.saveClick(id);
	}
}
