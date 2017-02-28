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
//注解Entity表示该类能被Hibernate持久化
@Table(name = "login")
public class Login  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7205847352072498767L;
	@Id									// 指定该列为主键，尽量不要使用int等原始数据类型
	private String userID;				// 学生号
	@Column(name="userPSW")				// 指定属性对应的数据库表的列为"userPSW"
	private String userPSW;				// 密码
	@Column(name="question")			// 指定属性对应的数据库表的列为"question"
	private String question;			// 忘记密码问题
	@Column(name="answer")				// 指定属性对应的数据库表的列为"answer"
	private String answer;				// 忘记密码答案
	
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