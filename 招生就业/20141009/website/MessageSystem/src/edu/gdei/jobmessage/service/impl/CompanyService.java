/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.ICompanyDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Company;
import edu.gdei.jobmessage.service.ICompanyService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("companyService")
public class CompanyService extends AbstractService<Company> implements
		ICompanyService {
	
	@Resource(name = "companyDao")
	private ICompanyDao companyDao;

	/**
	 * 
	 */
	public CompanyService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Company> getDao() {
		// TODO Auto-generated method stub
		return this.companyDao;
	}

	/**
	 * @return the companyDao
	 */
	public ICompanyDao getCompanyDao() {
		return companyDao;
	}

	/**
	 * @param companyDao the companyDao to set
	 */
	public void setCompanyDao(ICompanyDao companyDao) {
		this.companyDao = companyDao;
	}
}
