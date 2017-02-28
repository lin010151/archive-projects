/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.androidpn.server.xmpp.presence.PresenceManager;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.User;
import edu.gdei.jobmessage.service.IUserService;

/**
 * @author dragonzhu
 *
 */
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 623330808555482193L;
	private int pageSize; 	// 每页数量
	private Long totalsize; // 总数量
	
	private String pageNo; 	// 页码
	
	private Manager manager;// 管理员号
	private User user;		// 用户
	
	@Resource(name = "userService")
	private IUserService userService;	// 用户服务
	
	private List<User> list=new ArrayList<User>();

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalsize
	 */
	public Long getTotalsize() {
		return totalsize;
	}

	/**
	 * @param totalsize the totalsize to set
	 */
	public void setTotalsize(Long totalsize) {
		this.totalsize = totalsize;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the list
	 */
	public List<User> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<User> list) {
		this.list = list;
	}

	/**
	 * @return the manager
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (pageNo == null) // 如果页面号为空，则从session中读取
		{
			pageNo = (String) getSession().get("userPage");
			if (null == pageNo) // 如果session会话也没有的话，则设为1
				pageNo = "1";
		}
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			totalsize = (long) 0; // 赋予初始值
			totalsize = userService.countTotal(); // 返回学生的数量
			pageSize = 20; // 每页最多只取20个学生信息
			// 返回学生列表
			list=userService.findPage(Integer.parseInt(pageNo) - 1, pageSize);
			PresenceManager presenceManager = new PresenceManager();
			// 判断用户是否在线
			for (User users : list) {
				if (presenceManager.isAvailable(users)) {
	                users.setOnline(true);
	            } else {
	                users.setOnline(false);
	            }
			}
			
			getSession().put("userPage", pageNo); // 保存页码信息在session会话中
			return "success";
		}
		return "fail";
	}

}
