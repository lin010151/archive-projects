/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.ICompanyDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Company;

/**
 * @author dragonzhu
 *
 */
@Repository("companyDao")
public class CompanyDao extends AbstractHibernateDao<Company> implements
		ICompanyDao {

	/**
	 * 
	 */
	public CompanyDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Company.class);
	}

}
