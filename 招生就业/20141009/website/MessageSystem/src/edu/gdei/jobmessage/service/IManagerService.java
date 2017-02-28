package edu.gdei.jobmessage.service;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;

public interface IManagerService extends IDao<Manager> {
	
	// 通过登录查找用户
	Manager findOneByLogin(final String id, final String psw);
	
	// 获取登录信息
	public ManagerLogin findLogin(final String id);
	
	// 更新登录信息
	public void updateLogin(ManagerLogin login);	

	// 插入管理员信息
	public void saveManager(Manager manager);
	
	// 删除管理员信息
	public void deleteManager(String id);
}