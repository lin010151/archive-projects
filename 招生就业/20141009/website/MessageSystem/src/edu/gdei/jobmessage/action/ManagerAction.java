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
	
	private String username;				// 账号
	private String password;				// 密码
	private String users;					// 用户列表
	private String login;					// 获取登录信息
	private String updatelogin;				// 更新登录信息
	private String find;					// 查找一个管理员以进行修改
	private String update;					// 更新管理员信息
	private String delete;					// 删除一个管理员信息
	private String insert;					// 添加一个管理员信息
	private String logout;					// 退出登录
	private String managerID;				// 管理员账号
	
	private ManagerLogin managerlogin;		// 管理员登陆信息
	private Manager manager;				// 管理员信息
	
	@Resource(name="managerService")
	private IManagerService managerService;	// 管理员服务
	
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
		if ("1".equals(login))						// 是否要获取登录信息
		{
			manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
			if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
			{
				managerlogin=managerService.findLogin(manager.getManagerID());
				return "login";
			}
		}
		if ("1".equals(logout))						// 是否退出
		{
			getSession().clear();					// 清除会话
			return "index";
		}
		if ("1".equals(update))						// 是否要更新管理员信息
		{
			managerService.update(manager);			// 更新
			// 返回
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(delete))						// 是否要删除一个管理员
		{
			managerService.deleteManager(managerID);
			// 返回
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(insert))						// 是否插入一个管理员
		{
			managerService.saveManager(manager);
			// 返回
			list=managerService.findAll();
			return "managers";
		}
		if ("1".equals(find))						// 是否要找出一个管理员，以进行修改
		{
			manager=managerService.findOneByString(managerID);
			return "updatemanager";
		}
		if ("1".equals(users))						// 是否要获取所有管理员信息
		{
			manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
			if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
			{
				list=managerService.findAll();
				return "managers";
			}
		}
		if ("1".equals(updatelogin))				// 如果更新登录信息
		{
			managerService.updateLogin(managerlogin);
			return "login";
		}
		else										// 用户需要登录的话
		{
			// 获取用户登录信息
			manager = managerService.findOneByLogin(username, password);

			if (null != manager) { // 登录成功的话
				getSession().put("manager", manager); // 保存登录信息在session会话中
				return "success"; // 重定向
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
