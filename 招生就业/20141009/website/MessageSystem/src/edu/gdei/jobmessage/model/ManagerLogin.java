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
//注解Entity表示该类能被Hibernate持久化
@Table(name = "managerlogin")
public class ManagerLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id									// 指定该列为主键，尽量不要使用int等原始数据类型
	//采用数据库自增方式生成主键
	//@GeneratedValue(strategy=GenerationType.TABLE)
	@GeneratedValue
	private String managerID;			// 管理员号
	@Column(name="managerPSW")			// 指定属性对应的数据库表的列为"userPSW"
	private String managerPSW;			// 密码
	@Column(name="question")			// 指定属性对应的数据库表的列为"question"
	private String question;			// 忘记密码问题
	@Column(name="answer")				// 指定属性对应的数据库表的列为"answer"
	private String answer;				// 忘记密码答案
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
