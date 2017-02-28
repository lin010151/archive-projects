/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.service.IUserService;

/**
 * @author dragonzhu
 * 
 */
public class NotificationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1342206686771828290L;

	private String broadcast; // �Ƿ�㲥
	private String username; // �û���
	private String title; // ����
	private String message; // ���͵���Ϣ
	private String postID; // ���͵ĸ�λ
	private String majorID; // רҵ��
	private String addressID; // ��Դ�غ�
	private String sex; // �Ա�

	private Manager manager; // ����Ա��

	private NotificationManager notificationManager;

	private List<String> list = new ArrayList<String>();
	@Resource(name = "userService")
	private IUserService userService; // �û�����

	/**
	 * @param notificationManager
	 */
	public NotificationAction() {
		super();
		notificationManager=new NotificationManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			String apiKey = Config.getString("apiKey", ""); // ��ȡapiKey
			// ������Ϣ
			if (broadcast.equalsIgnoreCase("Y")) { // �㲥�����б�ҵ��
				notificationManager.sendBroadcast(apiKey, title, message,
						postID);
			} else { // ���͸��ض��ı�ҵ��
				// �ҳ�Ҫ���͵ı�ҵ��
				int stusex=0;
				if (sex.equals("0"))
					stusex=0;
				else if (sex.equals("1"))
					stusex=1;
				else
					stusex=2;
				list = userService.getUsersForNotify(majorID, addressID, stusex);
				for (int i = 0; i < list.size(); i++) {
					username=list.get(i);			// �ҳ�ѧ��
					// ������Ϣ
					notificationManager.sendNotifcationToUser(apiKey, username,
							title, message, postID);
				}
			}
			return "success";
		}

		return "fail";
	}

	/**
	 * @return the broadcast
	 */
	public String getBroadcast() {
		return broadcast;
	}

	/**
	 * @param broadcast
	 *            the broadcast to set
	 */
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the postID
	 */
	public String getPostID() {
		return postID;
	}

	/**
	 * @param postID
	 *            the postID to set
	 */
	public void setPostID(String postID) {
		this.postID = postID;
	}

	/**
	 * @return the majorID
	 */
	public String getMajorID() {
		return majorID;
	}

	/**
	 * @param majorID
	 *            the majorID to set
	 */
	public void setMajorID(String majorID) {
		this.majorID = majorID;
	}

	/**
	 * @return the addressID
	 */
	public String getAddressID() {
		return addressID;
	}

	/**
	 * @param addressID
	 *            the addressID to set
	 */
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

}
