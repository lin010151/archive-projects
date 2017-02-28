/**
 * 
 */
package edu.gdei.jobmessage.service;

import java.util.List;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.User;
import edu.gdei.jobmessage.util.UserNotFoundException;

/**
 * @author dragonzhu
 *
 */
public interface IUserService extends IDao<User> {
	
	public User getUser(String id);

    public User saveUser(User user);

    public void removeUser(String id);

    public boolean exists(String id);

    public List<User> getUsers();
    
    public List<User> getUsersByPage(int page, int pageSize);
    // ����רҵ����Դ�ء��Ա��ѯ�û�����������Ϣ
    public List<String> getUsersForNotify(String majorid, String addressid, int sex);

    public User getUserByUsername(String username) throws UserNotFoundException;
}
