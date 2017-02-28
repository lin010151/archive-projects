/**
 * 
 */
package edu.gdei.jobmessage.model;

import java.io.Serializable;




import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author dragonzhu
 * 
 */
@Entity
@Table(name = "student")
// 注解Entity表示该类能被Hibernate持久化
public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1572534380664333541L;
	/**
	 * 
	 */
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	@Column(name = "stuID")
	private String stuID; // id号
	@Column(name = "stuName")
	// 指定属性对应的数据库表的列为"stuName"
	private String stuName; // 学生姓名
	@Column(name = "stuIDCard")
	// 指定属性对应的数据库表的列为"stuIDCard"
	private String stuIDCard; // 身份证号码
	@Column(name = "stuSex")
	// 指定属性对应的数据库表的列为"stuSex"
	private Integer stuSex; // 性别
	// 一对一关系
	@OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Major.class)
	@JoinColumn(name = "majorID")
	// 主键不做外键时，必须设置 referencedColumnName属性
	private Major major; // 专业
	// 一对一关系
	@OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Address.class)
	@JoinColumn(name = "addressID")
	private Address address; // 生源地
	@Column(name = "stuPolitical")
	// 指定属性对应的数据库表的列为"stuPolitical"
	private Integer stuPolitical; // 政治面貌
	@Column(name = "stuEmail")
	// 指定属性对应的数据库表的列为"stuEmail"
	private String stuEmail; // 电子邮箱
	// 登录信息
	@OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST,CascadeType.REMOVE})// 声明为一对一关系
	@PrimaryKeyJoinColumn	// 一对一实体的主键相同
	private Login login;
	/**
	 * @return the stuID
	 */
	public String getStuID() {
		return stuID;
	}
	/**
	 * @param stuID the stuID to set
	 */
	public void setStuID(String stuID) {
		this.stuID = stuID;
	}
	/**
	 * @return the stuName
	 */
	public String getStuName() {
		return stuName;
	}
	/**
	 * @param stuName the stuName to set
	 */
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	/**
	 * @return the stuIDCard
	 */
	public String getStuIDCard() {
		return stuIDCard;
	}
	/**
	 * @param stuIDCard the stuIDCard to set
	 */
	public void setStuIDCard(String stuIDCard) {
		this.stuIDCard = stuIDCard;
	}
	/**
	 * @return the stuSex
	 */
	public Integer getStuSex() {
		return stuSex;
	}
	/**
	 * @param stuSex the stuSex to set
	 */
	public void setStuSex(Integer stuSex) {
		this.stuSex = stuSex;
	}
	/**
	 * @return the major
	 */
	public Major getMajor() {
		return major;
	}
	/**
	 * @param major the major to set
	 */
	public void setMajor(Major major) {
		this.major = major;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the stuPolitical
	 */
	public Integer getStuPolitical() {
		return stuPolitical;
	}
	/**
	 * @param stuPolitical the stuPolitical to set
	 */
	public void setStuPolitical(Integer stuPolitical) {
		this.stuPolitical = stuPolitical;
	}
	/**
	 * @return the stuEmail
	 */
	public String getStuEmail() {
		return stuEmail;
	}
	/**
	 * @param stuEmail the stuEmail to set
	 */
	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}
	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}
}
