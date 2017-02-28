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
 * ��ģ��ʵ��Զ���ֻ��û��ĵ�¼����ȡ���롢�޸����롢��ȡ������Ϣ���ĸ����ܡ�
 */
public class StudentJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757502519062468948L;

	private String what; // �û���Ҫ�Ĳ���

	private String username; // ѧ����
	private String password; // ����
	private String question; // ������ʾ����
	private String answer; // ������ʾ��

	@Resource(name = "studentService")
	private IStudentService studentService; // ѧ����Ϣ����
	@Resource(name = "loginService")
	private ILoginService loginService; // ѧ����Ϣ����

	private Student student; // ѧ����Ϣ
	private Login login; // ��¼��Ϣ

	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		// ��ѯ����ǰ�û�
		if (!username.isEmpty()) {
			// ���´����JSON.java�п�������
			getResponse().setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = getResponse().getWriter();
			// ��Ҫ�����ص��ͻ��˵Ķ���
			JSONObject json = new JSONObject();
			if ("login".equals(what)) // Ŀǰ�û���Ҫ��¼�Ļ�
			{
				// ��ѯ�û�
				student = studentService.findOneByString(username);
				if (null != student) {
					// �˶�����
					if (password.equals(student.getLogin().getUserPSW())) {
						json.accumulate("success", true);
						json.accumulate("student", student);
					} else
						json.accumulate("success", false);
				}
			}
			if ("obtainpsw".equals(what)) // Ŀǰ�û���Ҫȡ������Ļ�
			{
				login = loginService.findOneByString(username); // ���ҵ�¼��Ϣ
				if (login != null && question.equals(login.getQuestion())
						&& answer.equals(login.getAnswer())) {
					json.accumulate("success", true);
					json.accumulate("Login", login);
				} else
					json.accumulate("success", false);
			}
			if ("changepsw".equals(what)) // Ŀǰ�û���Ҫ�޸�����Ļ�
			{

				login = loginService.findOneByString(username); // ���ҵ�¼��Ϣ
				if (login != null) {
					// ���µ�¼��Ϣ
					login.setUserPSW(password);
					login.setQuestion(question);
					login.setAnswer(answer);
					loginService.update(login);
					json.accumulate("success", true);
				} else
					json.accumulate("success", false);
			}
			if ("info".equals(what))	// Ŀǰ�û���Ҫ�����Լ�����Ϣ
			{
				// ��ѯ�û�
				student = studentService.findOneByString(username);
				if (student!=null)
				{
					json.accumulate("success", true);
					json.accumulate("student", student);
				}
				else
					json.accumulate("success", false);
			}
			// ��������
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
