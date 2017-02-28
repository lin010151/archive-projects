/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 *
 */
public class Login {
	
	private String userID;	// 学生号(主键)
	private String userPSW;	// 用户密码
	private String question;// 忘记密码的问题
	private String answer;	// 忘记密码的答案
	
	/**
	 * @return the userID
	 */
	public final String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public final void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the userPSW
	 */
	public final String getUserPSW() {
		return userPSW;
	}
	/**
	 * @param userPSW the userPSW to set
	 */
	public final void setUserPSW(String userPSW) {
		this.userPSW = userPSW;
	}
	/**
	 * @return the question
	 */
	public final String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public final void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public final String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public final void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
