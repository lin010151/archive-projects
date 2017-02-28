/**
 * 
 */
package edu.gdei.jobmessage.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dragonzhu
 *
 */
@Entity
//ע��Entity��ʾ�����ܱ�Hibernate�־û�
@Table(name = "login")
public class Login  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7205847352072498767L;
	@Id									// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String userID;				// ѧ����
	@Column(name="userPSW")				// ָ�����Զ�Ӧ�����ݿ�����Ϊ"userPSW"
	private String userPSW;				// ����
	@Column(name="question")			// ָ�����Զ�Ӧ�����ݿ�����Ϊ"question"
	private String question;			// ������������
	@Column(name="answer")				// ָ�����Զ�Ӧ�����ݿ�����Ϊ"answer"
	private String answer;				// ���������
	
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the userPSW
	 */
	public String getUserPSW() {
		return userPSW;
	}
	/**
	 * @param userPSW the userPSW to set
	 */
	public void setUserPSW(String userPSW) {
		this.userPSW = userPSW;
	}
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}	
}