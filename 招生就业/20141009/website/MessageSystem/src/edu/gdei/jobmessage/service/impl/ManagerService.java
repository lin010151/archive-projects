/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IManagerDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;
import edu.gdei.jobmessage.service.IManagerService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 * 
 */
@Service("managerService")
public class ManagerService extends AbstractService<Manager> implements
		IManagerService {

	@Resource(name = "managerDao")
	private IManagerDao managerDao;

	public ManagerService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the managerDao
	 */
	public IManagerDao getManagerDao() {
		return this.managerDao;
	}

	/**
	 * @param managerDao
	 *            the managerDao to set
	 */
	public void setManagerDao(IManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	@Override
	protected IDao<Manager> getDao() {
		// TODO Auto-generated method stub
		return this.managerDao;
	}

	@Override
	public Manager findOneByLogin(String id, String psw) {
		// TODO Auto-generated method stub
		return getManagerDao().findOneByLogin(id, psw);
	}

	@Override
	public ManagerLogin findLogin(String id) {
		// TODO Auto-generated method stub
		return getManagerDao().findLogin(id);
	}

	@Override
	public void updateLogin(ManagerLogin login) {
		// TODO Auto-generated method stub
		this.managerDao.updateLogin(login);
	}

	@Override
	public void saveManager(Manager manager) {
		// TODO Auto-generated method stub
		this.managerDao.saveManager(manager);
	}

	@Override
	public void deleteManager(String id) {
		// TODO Auto-generated method stub
		this.managerDao.deleteManager(id);
	}
}
