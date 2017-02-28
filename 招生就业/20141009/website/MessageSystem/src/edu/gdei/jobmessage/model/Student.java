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
// ע��Entity��ʾ�����ܱ�Hibernate�־û�
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
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	@Column(name = "stuID")
	private String stuID; // id��
	@Column(name = "stuName")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"stuName"
	private String stuName; // ѧ������
	@Column(name = "stuIDCard")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"stuIDCard"
	private String stuIDCard; // ���֤����
	@Column(name = "stuSex")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"stuSex"
	private Integer stuSex; // �Ա�
	// һ��һ��ϵ
	@OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Major.class)
	@JoinColumn(name = "majorID")
	// �����������ʱ���������� referencedColumnName����
	private Major major; // רҵ
	// һ��һ��ϵ
	@OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Address.class)
	@JoinColumn(name = "addressID")
	private Address address; // ��Դ��
	@Column(name = "stuPolitical")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"stuPolitical"
	private Integer stuPolitical; // ������ò
	@Column(name = "stuEmail")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"stuEmail"
	private String stuEmail; // ��������
	// ��¼��Ϣ
	@OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST,CascadeType.REMOVE})// ����Ϊһ��һ��ϵ
	@PrimaryKeyJoinColumn	// һ��һʵ���������ͬ
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
