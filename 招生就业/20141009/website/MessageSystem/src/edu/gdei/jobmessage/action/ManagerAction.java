/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;
import edu.gdei.jobmessage.service.IManagerService;

/**
 * @author dragonzhu
 * 
 */
public class ManagerAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;				// �˺�
	private String password;				// ����
	private String users;					// �û��б�
	private String login;					// ��ȡ��¼��Ϣ
	private String updatelogin;				// ���µ�¼��Ϣ
	private String find;					// ����һ������Ա�Խ����޸�
	private String update;					// ���¹���Ա��Ϣ
	private String delete;					// ɾ��һ������Ա��Ϣ
	private String insert;					// ���һ������Ա��Ϣ
	private String logout;					// �˳���¼
	private String managerID;				// ����Ա�˺�
	
	private ManagerLogin managerlogin;		// ����Ա��½��Ϣ
	private Manager manager;				// ����Ա��Ϣ
	
	@Resource(name="managerService")
	private IManagerService managerService;	// ����Ա����
	
	private List<Manager> list=new ArrayList<Manager>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if ("1".equals(login))						// �Ƿ�Ҫ��ȡ��¼��Ϣ
		{
			manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
			if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
			{
				managerlogin=managerService.findLogin(manager.getManagerID());
				return "login";
			}
		}
		if ("1".equals(logout))						// �Ƿ��˳�
		{
			getSession().clear();					// ����Ự
			return "index";
		}
		if ("1".equals(update))						// �Ƿ�Ҫ���¹���Ա��Ϣ
		{
			managerService.update(manager);			// ����
			// ����
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(delete))						// �Ƿ�Ҫɾ��һ������Ա
		{
			managerService.deleteManager(managerID);
			// ����
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(insert))						// �Ƿ����һ������Ա
		{
			managerService.saveManager(manager);
			// ����
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(find))						// �Ƿ�Ҫ�ҳ�һ������Ա���Խ����޸�
		{
			manager=managerService.findOneByString(managerID);
			return "updatemanager";
		}
		if ("1".equals(users))						// �Ƿ�Ҫ��ȡ���й���Ա��Ϣ
		{
			manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
			if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
			{
				list=managerService.findAll();
				return "managers";
			}
		}
		if ("1".equals(updatelogin))				// ������µ�¼��Ϣ
		{
			managerService.updateLogin(managerlogin);
			return "login";
		}
		else										// �û���Ҫ��¼�Ļ�
		{
			// ��ȡ�û���¼��Ϣ
			manager = managerService.findOneByLogin(username, password);

			if (null != manager) { // ��¼�ɹ��Ļ�
				getSession().put("manager", manager); // �����¼��Ϣ��session�Ự��
				return "success"; // �ض���
			}
		}
		return "fail";
	}

	/**
	 * @return the manager
	 */
	public final Manager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public final void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the managerlogin
	 */
	public ManagerLogin getManagerlogin() {
		return managerlogin;
	}

	/**
	 * @param managerlogin the managerlogin to set
	 */
	public void setManagerlogin(ManagerLogin managerlogin) {
		this.managerlogin = managerlogin;
	}

	/**
	 * @return the users
	 */
	public String getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(String users) {
		this.users = users;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the updatelogin
	 */
	public String getUpdatelogin() {
		return updatelogin;
	}

	/**
	 * @param updatelogin the updatelogin to set
	 */
	public void setUpdatelogin(String updatelogin) {
		this.updatelogin = updatelogin;
	}

	/**
	 * @return the list
	 */
	public List<Manager> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Manager> list) {
		this.list = list;
	}

	/**
	 * @return the find
	 */
	public String getFind() {
		return find;
	}

	/**
	 * @param find the find to set
	 */
	public void setFind(String find) {
		this.find = find;
	}

	/**
	 * @return the managerID
	 */
	public String getManagerID() {
		return managerID;
	}

	/**
	 * @param managerID the managerID to set
	 */
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

	/**
	 * @param update the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}

	/**
	 * @return the delete
	 */
	public String getDelete() {
		return delete;
	}

	/**
	 * @param delete the delete to set
	 */
	public void setDelete(String delete) {
		this.delete = delete;
	}

	/**
	 * @return the insert
	 */
	public String getInsert() {
		return insert;
	}

	/**
	 * @param insert the insert to set
	 */
	public void setInsert(String insert) {
		this.insert = insert;
	}

	/**
	 * @return the logout
	 */
	public String getLogout() {
		return logout;
	}

	/**
	 * @param logout the logout to set
	 */
	public void setLogout(String logout) {
		this.logout = logout;
	}
}
