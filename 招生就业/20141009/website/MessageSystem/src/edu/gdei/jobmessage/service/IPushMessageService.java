/**
 * 
 */
package edu.gdei.jobmessage.service;

import java.util.List;

import net.sf.json.JSONObject;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.PushMessage;
import edu.gdei.jobmessage.util.PushMessageNotFoundException;

/**
 * @author dragonzhu
 *
 */
public interface IPushMessageService extends IDao<PushMessage> {

	public PushMessage getPushMessage(String id);
	
	public PushMessage savePushMessage(String username, JSONObject msg);
	
	public void removeMessage(String id);
	
	public boolean exists(String id);
	
	public List<PushMessage> getPushMessage();

    public PushMessage getPushMessageByUsername(String username) throws PushMessageNotFoundException;
    
    public List<PushMessage> getPushMessageListByUsername(String username) throws PushMessageNotFoundException;
}
