/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.ILoginDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Login;
import edu.gdei.jobmessage.service.ILoginService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("loginService")
public class LoginService extends AbstractService<Login> implements
		ILoginService {
	
	@Resource(name = "loginDao")
	private ILoginDao loginDao;

	/**
	 * 
	 */
	public LoginService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Login> getDao() {
		// TODO Auto-generated method stub
		return this.loginDao;
	}

	/**
	 * @return the loginDao
	 */
	public ILoginDao getLoginDao() {
		return loginDao;
	}

	/**
	 * @param loginDao the loginDao to set
	 */
	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

}
