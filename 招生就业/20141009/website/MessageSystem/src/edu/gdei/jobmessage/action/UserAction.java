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
	private int pageSize; 	// ÿҳ����
	private Long totalsize; // ������
	
	private String pageNo; 	// ҳ��
	
	private Manager manager;// ����Ա��
	private User user;		// �û�
	
	@Resource(name = "userService")
	private IUserService userService;	// �û�����
	
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
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (pageNo == null) // ���ҳ���Ϊ�գ����session�ж�ȡ
		{
			pageNo = (String) getSession().get("userPage");
			if (null == pageNo) // ���session�ỰҲû�еĻ�������Ϊ1
				pageNo = "1";
		}
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			totalsize = (long) 0; // �����ʼֵ
			totalsize = userService.countTotal(); // ����ѧ��������
			pageSize = 20; // ÿҳ���ֻȡ20��ѧ����Ϣ
			// ����ѧ���б�
			list=userService.findPage(Integer.parseInt(pageNo) - 1, pageSize);
			PresenceManager presenceManager = new PresenceManager();
			// �ж��û��Ƿ�����
			for (User users : list) {
				if (presenceManager.isAvailable(users)) {
	                users.setOnline(true);
	            } else {
	                users.setOnline(false);
	            }
			}
			
			getSession().put("userPage", pageNo); // ����ҳ����Ϣ��session�Ự��
			return "success";
		}
		return "fail";
	}

}
