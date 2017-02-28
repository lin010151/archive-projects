/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.annotation.Transactional;

import edu.gdei.jobmessage.dao.IUserDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.User;
import edu.gdei.jobmessage.service.IUserService;
import edu.gdei.jobmessage.service.common.AbstractService;
import edu.gdei.jobmessage.util.UserNotFoundException;

/**
 * @author dragonzhu
 *
 */
@Service("userService")
@Transactional
public class UserService extends AbstractService<User> implements
		IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;

	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}
	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	protected IDao<User> getDao() {
		// TODO Auto-generated method stub
		return this.userDao;
	}
	/**
	 * 
	 */
	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#getUser(java.lang.Long)
	 */
	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return this.userDao.getUser(id);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#saveUser(edu.gdei.jobmessage.model.User)
	 */
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return this.userDao.saveUser(user);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#removeUser(java.lang.Long)
	 */
	@Override
	public void removeUser(String id) {
		// TODO Auto-generated method stub
		this.userDao.removeUser(id);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return this.userDao.exists(id);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return this.userDao.getUsers();
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#getUsersByPage(int, int)
	 */
	@Override
	public List<User> getUsersByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return this.userDao.getUsersByPage(page, pageSize);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.service.IUserService#getUserByUsername(java.lang.String)
	 */
	@Override
	public User getUserByUsername(String username) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return this.userDao.getUserByUsername(username);
	}
	@Override
	public List<String> getUsersForNotify(String majorid, String addressid,
			int sex) {
		// TODO Auto-generated method stub
		return this.userDao.getUsersForNotify(majorid, addressid, sex);
	}

}
