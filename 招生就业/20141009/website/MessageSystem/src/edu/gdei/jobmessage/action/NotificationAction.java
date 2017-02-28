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

	private String broadcast; // 是否广播
	private String username; // 用户名
	private String title; // 主题
	private String message; // 推送的消息
	private String postID; // 推送的岗位
	private String majorID; // 专业号
	private String addressID; // 生源地号
	private String sex; // 性别

	private Manager manager; // 管理员号

	private NotificationManager notificationManager;

	private List<String> list = new ArrayList<String>();
	@Resource(name = "userService")
	private IUserService userService; // 用户服务

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
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			String apiKey = Config.getString("apiKey", ""); // 获取apiKey
			// 推送消息
			if (broadcast.equalsIgnoreCase("Y")) { // 广播给所有毕业生
				notificationManager.sendBroadcast(apiKey, title, message,
						postID);
			} else { // 推送给特定的毕业生
				// 找出要推送的毕业生
				int stusex=0;
				if (sex.equals("0"))
					stusex=0;
				else if (sex.equals("1"))
					stusex=1;
				else
					stusex=2;
				list = userService.getUsersForNotify(majorID, addressID, stusex);
				for (int i = 0; i < list.size(); i++) {
					username=list.get(i);			// 找出学号
					// 推送信息
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
