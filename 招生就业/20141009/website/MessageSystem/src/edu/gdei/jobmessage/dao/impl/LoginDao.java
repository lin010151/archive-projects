/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.ILoginDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Login;

/**
 * @author dragonzhu
 *
 */
@Repository("loginDao")
public class LoginDao extends AbstractHibernateDao<Login> implements
		ILoginDao {

	/**
	 * 
	 */
	public LoginDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Login.class);
	}

}
