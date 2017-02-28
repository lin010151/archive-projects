/**
 * 
 */
package edu.gdei.jobmessage.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Login;
import edu.gdei.jobmessage.model.Student;
import edu.gdei.jobmessage.service.ILoginService;
import edu.gdei.jobmessage.service.IStudentService;

/**
 * @author dragonzhu
 * 本模块实现远程手机用户的登录、获取密码、修改密码、获取个人信息等四个功能。
 */
public class StudentJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757502519062468948L;

	private String what; // 用户需要的操作

	private String username; // 学生号
	private String password; // 密码
	private String question; // 密码提示问题
	private String answer; // 密码提示答案

	@Resource(name = "studentService")
	private IStudentService studentService; // 学生信息服务
	@Resource(name = "loginService")
	private ILoginService loginService; // 学生信息服务

	private Student student; // 学生信息
	private Login login; // 登录信息

	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		// 查询出当前用户
		if (!username.isEmpty()) {
			// 以下代码从JSON.java中拷过来的
			getResponse().setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = getResponse().getWriter();
			// 将要被返回到客户端的对象
			JSONObject json = new JSONObject();
			if ("login".equals(what)) // 目前用户需要登录的话
			{
				// 查询用户
				student = studentService.findOneByString(username);
				if (null != student) {
					// 核对密码
					if (password.equals(student.getLogin().getUserPSW())) {
						json.accumulate("success", true);
						json.accumulate("student", student);
					} else
						json.accumulate("success", false);
				}
			}
			if ("obtainpsw".equals(what)) // 目前用户需要取回密码的话
			{
				login = loginService.findOneByString(username); // 查找登录信息
				if (login != null && question.equals(login.getQuestion())
						&& answer.equals(login.getAnswer())) {
					json.accumulate("success", true);
					json.accumulate("Login", login);
				} else
					json.accumulate("success", false);
			}
			if ("changepsw".equals(what)) // 目前用户需要修改密码的话
			{

				login = loginService.findOneByString(username); // 查找登录信息
				if (login != null) {
					// 更新登录信息
					login.setUserPSW(password);
					login.setQuestion(question);
					login.setAnswer(answer);
					loginService.update(login);
					json.accumulate("success", true);
				} else
					json.accumulate("success", false);
			}
			if ("info".equals(what))	// 目前用户需要查找自己的信息
			{
				// 查询用户
				student = studentService.findOneByString(username);
				if (student!=null)
				{
					json.accumulate("success", true);
					json.accumulate("student", student);
				}
				else
					json.accumulate("success", false);
			}
			// 返回数据
			out.println(json);
			out.flush();
			out.close();
		}
	}

	/**
	 * @return the what
	 */
	public String getWhat() {
		return what;
	}

	/**
	 * @param what
	 *            the what to set
	 */
	public void setWhat(String what) {
		this.what = what;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
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
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
