/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Major;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.service.IMajorService;

/**
 * @author dragonzhu
 * 
 */
public class MajorAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long totalsize; // 总数量
	private Manager manager; // 管理员信息

	@Resource(name = "majorService")
	private IMajorService majorService; // 生源地服务

	private List<Major> list = new ArrayList<Major>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// 获取用户登录信息
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			totalsize = (long) 0; // 赋予初始值
			totalsize = majorService.countTotal(); // 返回学生的数量
			// 返回学生列表
			list = majorService.findAll();

			return "success"; // 重定向
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
	 * @param manager
	 *            the manager to set
	 */
	public final void setManager(Manager manager) {
		this.manager = manager;
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
	 * @return the list
	 */
	public List<Major> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Major> list) {
		this.list = list;
	}

}
