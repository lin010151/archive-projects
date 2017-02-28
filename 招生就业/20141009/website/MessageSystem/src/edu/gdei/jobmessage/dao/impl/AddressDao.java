/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IAddressDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Address;

/**
 * @author dragonzhu
 *
 */
@Repository("addressDao")
public class AddressDao extends AbstractHibernateDao<Address> implements
		IAddressDao {

	/**
	 * 
	 */
	public AddressDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Address.class);
	}

}
