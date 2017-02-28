/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.xmpp.packet.Presence;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.action.vo.SessionVO;
import edu.gdei.jobmessage.model.Manager;

/**
 * @author dragonzhu
 *
 */
public class SessionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1473902979260872214L;
	private Manager manager;// ����Ա��
	

    private List<SessionVO> voList = new ArrayList<SessionVO>();
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
	 * @return the voList
	 */
	public List<SessionVO> getVoList() {
		return voList;
	}
	/**
	 * @param voList the voList to set
	 */
	public void setVoList(List<SessionVO> voList) {
		this.voList = voList;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			ClientSession[] sessions = new ClientSession[0];
	        sessions = SessionManager.getInstance().getSessions().toArray(sessions);

	        for (ClientSession sess : sessions) {
	            SessionVO vo = new SessionVO();
	            vo.setUsername(sess.getUsername());
	            vo.setResource(sess.getAddress().getResource());
	            // Status
	            if (sess.getStatus() == Session.STATUS_CONNECTED) {
	                vo.setStatus("������");
	            } else if (sess.getStatus() == Session.STATUS_AUTHENTICATED) {
	                vo.setStatus("����Ȩ");
	            } else if (sess.getStatus() == Session.STATUS_CLOSED) {
	                vo.setStatus("�ѹر�");
	            } else {
	                vo.setStatus("δ֪���");
	            }
	            // Presence
	            if (!sess.getPresence().isAvailable()) {
	                vo.setPresence("Offline");
	            } else {
	                Presence.Show show = sess.getPresence().getShow();
	                if (show == null) {
	                    vo.setPresence("Online");
	                } else if (show == Presence.Show.away) {
	                    vo.setPresence("Away");
	                } else if (show == Presence.Show.chat) {
	                    vo.setPresence("Chat");
	                } else if (show == Presence.Show.dnd) {
	                    vo.setPresence("Do Not Disturb");
	                } else if (show == Presence.Show.xa) {
	                    vo.setPresence("eXtended Away");
	                } else {
	                    vo.setPresence("Unknown");
	                }
	            }
	            vo.setClientIP(sess.getHostAddress());
	            vo.setCreatedDate(sess.getCreationDate());
	            voList.add(vo);
	        }
	        return "success";
		}
		return "fail";
	}

}
