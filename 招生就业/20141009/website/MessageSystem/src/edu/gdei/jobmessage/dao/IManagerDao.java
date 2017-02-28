package edu.gdei.jobmessage.dao;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;

public interface IManagerDao extends IDao<Manager>{
	//让所有的DAO都实现基本的操作接口IDao
    //除了实现IDao基本操作之外，特定的DAO要实现其他操作可以在对应的接口DAO中定义方法，
    //此处ManagerDao的接口IManagerDao不需要实现其他方法
	public Manager findOneByLogin(final String id, final String psw);
	
	// 获取登录信息
	public ManagerLogin findLogin(final String id);
	
	// 更新登录信息
	public void updateLogin(ManagerLogin login);
	
	// 插入管理员信息
	public void saveManager(Manager manager);
	
	// 删除管理员信息
	public void deleteManager(String id);
	
}
