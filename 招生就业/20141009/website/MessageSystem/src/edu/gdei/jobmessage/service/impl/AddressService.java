/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IAddressDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Address;
import edu.gdei.jobmessage.service.IAddressService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 *
 */
@Service("addressService")
public class AddressService extends AbstractService<Address> implements
		IAddressService {
	
	@Resource(name = "addressDao")
	private IAddressDao addressDao;

	/**
	 * 
	 */
	public AddressService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<Address> getDao() {
		// TODO Auto-generated method stub
		return this.addressDao;
	}

	/**
	 * @return the addressDao
	 */
	public IAddressDao getAddressDao() {
		return addressDao;
	}

	/**
	 * @param addressDao the addressDao to set
	 */
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

}
