/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import edu.gdei.jobmessage.dao.IPushMessageDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.PushMessage;
import edu.gdei.jobmessage.service.IPushMessageService;
import edu.gdei.jobmessage.service.common.AbstractService;
import edu.gdei.jobmessage.util.PushMessageNotFoundException;

/**
 * @author dragonzhu
 *
 */
@Service("pushmessageService")
public class PushMessageService extends AbstractService<PushMessage> implements
		IPushMessageService {

	@Resource(name = "pushmessageDao")
	private IPushMessageDao pushmessageDao;
	
	@Override
	public PushMessage getPushMessage(String id) {
		// TODO Auto-generated method stub
		return this.pushmessageDao.getPushMessage(id);
	}

	@Override
	public PushMessage savePushMessage(String username, JSONObject msg) {
		// TODO Auto-generated method stub
		return this.pushmessageDao.savePushMessage(username, msg);
	}

	@Override
	public void removeMessage(String id) {
		// TODO Auto-generated method stub
		this.pushmessageDao.removeMessage(id);
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return this.pushmessageDao.exists(id);
	}

	@Override
	public List<PushMessage> getPushMessage() {
		// TODO Auto-generated method stub
		return this.pushmessageDao.getPushMessage();
	}

	@Override
	public PushMessage getPushMessageByUsername(String username)
			throws PushMessageNotFoundException {
		// TODO Auto-generated method stub
		return this.pushmessageDao.getPushMessageByUsername(username);
	}

	@Override
	public List<PushMessage> getPushMessageListByUsername(String username)
			throws PushMessageNotFoundException {
		// TODO Auto-generated method stub
		return this.pushmessageDao.getPushMessageListByUsername(username);
	}

	@Override
	protected IDao<PushMessage> getDao() {
		// TODO Auto-generated method stub
		return this.pushmessageDao;
	}

	/**
	 * @return the pushmessageDao
	 */
	public IPushMessageDao getPushmessageDao() {
		return pushmessageDao;
	}

	/**
	 * @param pushmessageDao the pushmessageDao to set
	 */
	public void setPushmessageDao(IPushMessageDao pushmessageDao) {
		this.pushmessageDao = pushmessageDao;
	}

}
