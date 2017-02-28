/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IMajorDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Major;
import edu.gdei.jobmessage.service.IMajorService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("majorService")
public class MajorService extends AbstractService<Major> implements
		IMajorService {
	
	@Resource(name = "majorDao")
	private IMajorDao majorDao;

	/**
	 * 
	 */
	public MajorService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Major> getDao() {
		// TODO Auto-generated method stub
		return this.majorDao;
	}

	/**
	 * @return the majorDao
	 */
	public IMajorDao getMajorDao() {
		return majorDao;
	}

	/**
	 * @param majorDao the majorDao to set
	 */
	public void setMajorDao(IMajorDao majorDao) {
		this.majorDao = majorDao;
	}

}
