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
	private int pageSize; // ÿҳ����
	private Long totalsize; // ������

	private String update; // �Ƿ���µ�λ ��Ϣ
	private String delete; // �Ƿ�ɾ����λ��Ϣ
	private String insert; // �Ƿ���뵥λ��Ϣ
	private String find;   // �Ƿ���Ҳ��޸�
	private String pageNo; // ҳ��
	private String companyID;// ��ҵ��

	private Manager manager; 	// ����Ա��
	private Company company;	// ��λ��Ϣ

	@Resource(name = "companyService")
	private ICompanyService companyService; // ��λ��Ϣ����

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
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (pageNo == null) // ���ҳ���Ϊ�գ����session�ж�ȡ
		{
			pageNo = (String) getSession().get("comPage");
			if (null == pageNo) // ���session�ỰҲû�еĻ�������Ϊ1
				pageNo = "1";
		}
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			if ("1".equals(find))					// ����һ����λ�������޸�
			{
				company=companyService.findOneByString(companyID);
				if (null!=company)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(insert))					// ��ӵ�λ��Ϣ
			{
				UuidUtil uuidUtil=new UuidUtil();		// ��������ID
				company.setCompanyID(uuidUtil.getCode());
				companyService.create(company);
			}
			if ("1".equals(delete))					// ɾ����λ��Ϣ
			{
//				studentService.deleteById(stuID);
				company=companyService.findOneByString(companyID);
				companyService.delete(company);
			}
			if ("1".equals(update))					// ���µ�λ��Ϣ
			{
				companyService.update(company);
			}
			
			totalsize = (long) 0; // �����ʼֵ
			totalsize = companyService.countTotal(); // ���ص�λ������
			pageSize = 20; // ÿҳ���ֻȡ20����λ��Ϣ
			// ���ص�λ�б�
			list = companyService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("comPage", pageNo); // ����ҳ����Ϣ��session�Ự��
			return "success";
		}

		return "fail";
	}

}
