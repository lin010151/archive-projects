/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IMajorDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Major;

/**
 * @author dragonzhu
 *
 */
@Repository("majorDao")
public class MajorDao extends AbstractHibernateDao<Major> implements
		IMajorDao {

	/**
	 * 
	 */
	public MajorDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Major.class);
	}

}
