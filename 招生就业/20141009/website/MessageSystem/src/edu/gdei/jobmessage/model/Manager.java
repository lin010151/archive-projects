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
 * @author TOSHIBA1
 *
 */
@Entity
@Table(name = "manager")
//ע��Entity��ʾ�����ܱ�Hibernate�־û�
public class Manager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id									// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	@Column(name="managerID")
	private String managerID;
	
	@Column(name="managerName")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"managerName"
	private String managerName;

	@Column(name="managerIDCard")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"managerIDCard"
	private String managerIDCard;

	@Column(name="managerSex")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"managerSex"
	private Integer managerSex;

	@Column(name="managerPhone")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"managerPhone"
	private String managerPhone;
	
	@Column(name="managerEmail")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"managerEmail"
	private String managerEmail;

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
	 * @return the managerName
	 */
	public String getManagerName() {
		return managerName;
	}

	/**
	 * @param managerName the managerName to set
	 */
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	/**
	 * @return the managerIDCard
	 */
	public String getManagerIDCard() {
		return managerIDCard;
	}

	/**
	 * @param managerIDCard the managerIDCard to set
	 */
	public void setManagerIDCard(String managerIDCard) {
		this.managerIDCard = managerIDCard;
	}

	/**
	 * @return the managerSex
	 */
	public Integer getManagerSex() {
		return managerSex;
	}

	/**
	 * @param managerSex the managerSex to set
	 */
	public void setManagerSex(Integer managerSex) {
		this.managerSex = managerSex;
	}

	/**
	 * @return the managerPhone
	 */
	public String getManagerPhone() {
		return managerPhone;
	}

	/**
	 * @param managerPhone the managerPhone to set
	 */
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	/**
	 * @return the managerEmail
	 */
	public String getManagerEmail() {
		return managerEmail;
	}

	/**
	 * @param managerEmail the managerEmail to set
	 */
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

}
