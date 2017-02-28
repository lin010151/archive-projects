/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;
import edu.gdei.jobmessage.dao.IPushMessageDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.PushMessage;
import edu.gdei.jobmessage.util.PushMessageNotFoundException;
import edu.gdei.jobmessage.util.UuidUtil;

/**
 * @author dragonzhu
 * 
 */
@Repository("pushmessageDao")
public class PushMessageDao extends AbstractHibernateDao<PushMessage> implements
		IPushMessageDao {

	/**
	 * 
	 */
	public PushMessageDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(PushMessage.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IPushMessageDao#getPushMessage(java.lang.Long)
	 */
	@Override
	public PushMessage getPushMessage(String id) {
		// TODO Auto-generated method stub
		return findOneByString(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IPushMessageDao#savePushMessage(java.lang.String,
	 * net.sf.json.JSONObject)
	 */
	@SuppressWarnings("static-access")
	@Override
	public PushMessage savePushMessage(String username, JSONObject msg) {
		// TODO Auto-generated method stub
		PushMessage pushmsg = new PushMessage();
        UuidUtil uuidUtil=new UuidUtil();		// Éú³ÉÖ÷¼üID
        pushmsg.setId(uuidUtil.getCode());
		pushmsg.setUsername(username);
		pushmsg.setMessage(msg.toString());
		create(pushmsg);
		return pushmsg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IPushMessageDao#removeMessage(java.lang.Long)
	 */
	@Override
	public void removeMessage(String id) {
		// TODO Auto-generated method stub
		deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IPushMessageDao#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return findOneByString(id) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IPushMessageDao#getPushMessage()
	 */
	@Override
	public List<PushMessage> getPushMessage() {
		// TODO Auto-generated method stub
		return findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IPushMessageDao#getPushMessageByUsername(java
	 * .lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PushMessage getPushMessageByUsername(String username)
			throws PushMessageNotFoundException {
		// TODO Auto-generated method stub
		List<PushMessage> pushmsg = getCurrentSession().createQuery(
				"from PushMessage p where username='" + username+"'").list();
		if (pushmsg == null || pushmsg.isEmpty()) {
			throw new PushMessageNotFoundException("PushMessage '" + pushmsg
					+ "' not found");
		} else {
			return (PushMessage) pushmsg.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IPushMessageDao#getPushMessageListByUsername(
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PushMessage> getPushMessageListByUsername(String username)
			throws PushMessageNotFoundException {
		// TODO Auto-generated method stub
		List<PushMessage> pushmsg = getCurrentSession().createQuery(
				"from PushMessage p where username='" + username+"'").list();
		if (pushmsg == null || pushmsg.isEmpty()) {
			throw new PushMessageNotFoundException("PushMessage '" + pushmsg
					+ "' not found");
		} else {
			return pushmsg;
		}
	}

}
