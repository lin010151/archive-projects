/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 *
 */
public class Student {

	private String stuid;				// �û�ID��
	private String stupsw;				// �û�����
	private String stuname;				// �û�����
	private String idcard;				// ���֤����
	private String sex;					// �Ա�
	private String major;				// רҵ
	private String nation;				// ����
	private String province;			// ʡ��
	private String city;				// ����
	/**
	 * @param stuid
	 * @param stupsw
	 * @param stuname
	 * @param idcard
	 * @param sex
	 * @param major
	 * @param nation
	 * @param province
	 * @param city
	 */
	public Student() {
		this.stuid = "";
		this.stupsw = "";
		this.stuname = "";
		this.idcard = "";
		this.sex = "";
		this.major = "";
		this.nation = "";
		this.province = "";
		this.city = "";
	}
	/**
	 * @return the stuid
	 */
	public String getStuid() {
		return stuid;
	}
	/**
	 * @param stuid the stuid to set
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
	 * @param stupsw the stupsw to set
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
	 * @param stuname the stuname to set
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
	 * @param idcard the idcard to set
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
	 * @param sex the sex to set
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
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
}
