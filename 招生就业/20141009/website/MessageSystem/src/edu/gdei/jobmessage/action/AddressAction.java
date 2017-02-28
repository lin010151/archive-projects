/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Address;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.service.IAddressService;

/**
 * @author dragonzhu
 * 
 */
public class AddressAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize; // 每页数量
	private Long totalsize; // 总数量

	private String pageNo; // 页码

	private Manager manager; // 管理员信息

	@Resource(name = "addressService")
	private IAddressService addressService; // 生源地服务

	private List<Address> list = new ArrayList<Address>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// 获取用户登录信息
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (pageNo == null) // 如果页面号为空，则从session中读取
		{
			pageNo = (String) getSession().get("addrPage");
			if (null == pageNo) // 如果session会话也没有的话，则设为1
				pageNo = "1";
		}
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			totalsize = (long) 0; // 赋予初始值
			totalsize = addressService.countTotal(); // 返回学生的数量
			pageSize = 20; // 每页最多只取20个学生信息
			// 返回学生列表
			list = addressService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("addrPage", pageNo); // 保存页码信息在session会话中
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
	public List<Address> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Address> list) {
		this.list = list;
	}
}
