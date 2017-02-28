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
	private int pageSize; // ÿҳ����
	private Long totalsize; // ������

	private String pageNo; // ҳ��

	private Manager manager; // ����Ա��Ϣ

	@Resource(name = "addressService")
	private IAddressService addressService; // ��Դ�ط���

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
		// ��ȡ�û���¼��Ϣ
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (pageNo == null) // ���ҳ���Ϊ�գ����session�ж�ȡ
		{
			pageNo = (String) getSession().get("addrPage");
			if (null == pageNo) // ���session�ỰҲû�еĻ�������Ϊ1
				pageNo = "1";
		}
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			totalsize = (long) 0; // �����ʼֵ
			totalsize = addressService.countTotal(); // ����ѧ��������
			pageSize = 20; // ÿҳ���ֻȡ20��ѧ����Ϣ
			// ����ѧ���б�
			list = addressService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("addrPage", pageNo); // ����ҳ����Ϣ��session�Ự��
			return "success"; // �ض���
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
