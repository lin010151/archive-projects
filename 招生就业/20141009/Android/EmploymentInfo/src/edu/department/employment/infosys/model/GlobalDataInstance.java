/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 * 
 */
public class GlobalDataInstance {

	private static GlobalDataInstance instance; // ����

	private int screenWidth; // ��Ļ���
	private Student student; // �����û�ID
	private Login login; // �����¼��Ϣ
	private boolean loginsuccess;

	private static volatile LoginLock loginlock; // ��¼��

	private GlobalDataInstance() {
		student = new Student();
		loginlock = new LoginLock();
		loginsuccess = false;
	}

	// ���ص���
	public static GlobalDataInstance GetInstance() {
		if (null == instance) {
			instance = new GlobalDataInstance();
		}
		return instance;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth
	 *            the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the login
	 */
	public final Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public final void setLogin(Login login) {
		this.login = login;
	}

	public class LoginLock {
		boolean lock = false;

		/**
		 * @return the lock
		 */
		public final boolean isLock() {
			return lock;
		}

		/**
		 * @param lock
		 *            the lock to set
		 */
		public final void setLock(boolean lock) {
			this.lock = lock;
		}
	}

	/**
	 * @return the loginlock
	 */
	public final LoginLock getLoginlock() {
		return loginlock;
	}

	/**
	 * @param loginlock
	 *            the loginlock to set
	 */
	public final void setLoginlock(LoginLock loginlock) {
		GlobalDataInstance.loginlock = loginlock;
	}

	/**
	 * @return the loginsuccess
	 */
	public final boolean isLoginsuccess() {
		return loginsuccess;
	}

	/**
	 * @param loginsuccess
	 *            the loginsuccess to set
	 */
	public final void setLoginsuccess(boolean loginsuccess) {
		this.loginsuccess = loginsuccess;
	}
}
