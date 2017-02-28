/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IManagerDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;

/**
 * @author dragonzhu
 *
 */
@Repository("managerDao")
public class ManagerDao extends AbstractHibernateDao<Manager> implements IManagerDao {

	public ManagerDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Manager.class);
	}

	@Override
	public Manager findOneByLogin(String id, String psw) {
		// TODO Auto-generated method stub
		ManagerLogin login=(ManagerLogin) getCurrentSession().get(ManagerLogin.class, id);
		if (null!=psw && psw.equals(login.getManagerPSW()))
			return (Manager) getCurrentSession().get(Manager.class, id);
		return null;
	}

	@Override
	public ManagerLogin findLogin(String id) {
		// TODO Auto-generated method stub
		return (ManagerLogin) getCurrentSession().get(ManagerLogin.class, id);
	}

	@Override
	public void updateLogin(ManagerLogin login) {
		// TODO Auto-generated method stub
		getCurrentSession().update(login);
	}

	@Override
	public void saveManager(Manager manager) {
		// TODO Auto-generated method stub
		create(manager);					// 先保存管理员信息
		// 保存管理员的登录信息(注：此处不是原子操作，可能会出现只保存管理员，而没保存登录信息的情况。)
		ManagerLogin login=new ManagerLogin();
		login.setManagerID(manager.getManagerID());
		login.setManagerPSW(manager.getManagerIDCard());
		getCurrentSession().saveOrUpdate(login);
	}

	@Override
	public void deleteManager(String id) {
		// TODO Auto-generated method stub
		deleteById(id);						// 先删除管理员信息
		// 再删除登录信息(注：此处不是原子操作，可能会出现只保存管理员，而没保存登录信息的情况。)
		ManagerLogin login=null;
		login=(ManagerLogin) getCurrentSession().get(ManagerLogin.class, id);
		if (null != login)
			getCurrentSession().delete(login);
	}
}