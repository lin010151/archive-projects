/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 * 
 */
public class Student {

	private String stuid; // 用户ID号
	private String stupsw; // 用户密码
	private String stuname; // 用户姓名
	private String idcard; // 身份证号码
	private String sex; // 性别
	private String political; // 政治面貌
	private String major; // 专业
	private String address; // 生源地
	private String email; // 电子邮件

	/**
	 * @param stuid
	 * @param stupsw
	 * @param stuname
	 * @param idcard
	 * @param sex
	 * @param major
	 * @param address
	 * @param email
	 */
	public Student() {
		this.stuid = "";
		this.stupsw = "";
		this.stuname = "";
		this.idcard = "";
		this.sex = "";
		this.major = "";
		this.address = "";
		this.email = "";
	}

	/**
	 * @return the stuid
	 */
	public String getStuid() {
		return stuid;
	}

	/**
	 * @param stuid
	 *            the stuid to set
	 */
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	/**
	 * @return the stupsw
	 */
	public String getStupsw() {
		return stupsw;
	}

	/**
	 * @param stupsw
	 *            the stupsw to set
	 */
	public void setStupsw(String stupsw) {
		this.stupsw = stupsw;
	}

	/**
	 * @return the stuname
	 */
	public String getStuname() {
		return stuname;
	}

	/**
	 * @param stuname
	 *            the stuname to set
	 */
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	/**
	 * @return the idcard
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * @param idcard
	 *            the idcard to set
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major
	 *            the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the political
	 */
	public final String getPolitical() {
		return political;
	}

	/**
	 * @param political
	 *            the political to set
	 */
	public final void setPolitical(String political) {
		this.political = political;
	}

	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public final void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}
}
