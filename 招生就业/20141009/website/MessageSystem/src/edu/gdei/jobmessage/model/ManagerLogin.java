/**
 * 
 */
package edu.gdei.jobmessage.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dragonzhu
 *
 */
@Entity
//ע��Entity��ʾ�����ܱ�Hibernate�־û�
@Table(name = "managerlogin")
public class ManagerLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id									// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	//�������ݿ�������ʽ��������
	//@GeneratedValue(strategy=GenerationType.TABLE)
	@GeneratedValue
	private String managerID;			// ����Ա��
	@Column(name="managerPSW")			// ָ�����Զ�Ӧ�����ݿ�����Ϊ"userPSW"
	private String managerPSW;			// ����
	@Column(name="question")			// ָ�����Զ�Ӧ�����ݿ�����Ϊ"question"
	private String question;			// ������������
	@Column(name="answer")				// ָ�����Զ�Ӧ�����ݿ�����Ϊ"answer"
	private String answer;				// ���������
	/**
	 * @return the managerID
	 */
	public String getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID the managerID to set
	 */
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	/**
	 * @return the managerPSW
	 */
	public String getManagerPSW() {
		return managerPSW;
	}
	/**
	 * @param managerPSW the managerPSW to set
	 */
	public void setManagerPSW(String managerPSW) {
		this.managerPSW = managerPSW;
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
