/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Company;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.service.ICompanyService;
import edu.gdei.jobmessage.util.UuidUtil;

/**
 * @author dragonzhu
 * 
 */
public class CompanyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize; // 每页数量
	private Long totalsize; // 总数量

	private String update; // 是否更新单位 信息
	private String delete; // 是否删除单位信息
	private String insert; // 是否插入单位信息
	private String find;   // 是否查找并修改
	private String pageNo; // 页码
	private String companyID;// 企业号

	private Manager manager; 	// 管理员号
	private Company company;	// 单位信息

	@Resource(name = "companyService")
	private ICompanyService companyService; // 单位信息服务

	private List<Company> list = new ArrayList<Company>();

	/**
	 * @return the totalsize
	 */
	public Long getTotalsize() {
		return totalsize;
	}

	/**
	 * @param totalsize
	 *            the totalsize to set
	 */
	public void setTotalsize(Long totalsize) {
		this.totalsize = totalsize;
	}

	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
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
	 * @param delete
	 *            the delete to set
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
	 * @param insert
	 *            the insert to set
	 */
	public void setInsert(String insert) {
		this.insert = insert;
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
	 * @return the companyID
	 */
	public String getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID the companyID to set
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the companyService
	 */
	public ICompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * @param companyService the companyService to set
	 */
	public void setCompanyService(ICompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the list
	 */
	public List<Company> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Company> list) {
		this.list = list;
	}

	/**
	 * @return the manager
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (pageNo == null) // 如果页面号为空，则从session中读取
		{
			pageNo = (String) getSession().get("comPage");
			if (null == pageNo) // 如果session会话也没有的话，则设为1
				pageNo = "1";
		}
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			if ("1".equals(find))					// 查找一个单位并进行修改
			{
				company=companyService.findOneByString(companyID);
				if (null!=company)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(insert))					// 添加单位信息
			{
				UuidUtil uuidUtil=new UuidUtil();		// 生成主键ID
				company.setCompanyID(uuidUtil.getCode());
				companyService.create(company);
			}
			if ("1".equals(delete))					// 删除单位信息
			{
//				studentService.deleteById(stuID);
				company=companyService.findOneByString(companyID);
				companyService.delete(company);
			}
			if ("1".equals(update))					// 更新单位信息
			{
				companyService.update(company);
			}
			
			totalsize = (long) 0; // 赋予初始值
			totalsize = companyService.countTotal(); // 返回单位的数量
			pageSize = 20; // 每页最多只取20个单位信息
			// 返回单位列表
			list = companyService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("comPage", pageNo); // 保存页码信息在session会话中
			return "success";
		}

		return "fail";
	}

}
