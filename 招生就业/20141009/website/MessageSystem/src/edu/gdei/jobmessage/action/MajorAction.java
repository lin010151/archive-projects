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
	private Long totalsize; // ������
	private Manager manager; // ����Ա��Ϣ

	@Resource(name = "majorService")
	private IMajorService majorService; // ��Դ�ط���

	private List<Major> list = new ArrayList<Major>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// ��ȡ�û���¼��Ϣ
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			totalsize = (long) 0; // �����ʼֵ
			totalsize = majorService.countTotal(); // ����ѧ��������
			// ����ѧ���б�
			list = majorService.findAll();

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
