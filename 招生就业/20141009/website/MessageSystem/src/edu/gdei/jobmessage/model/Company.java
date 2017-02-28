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
// 注解Entity表示该类能被Hibernate持久化
@Table(name = "company")
public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7958916606950054994L;
	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	private String companyID; 	// 公司号
	@Column(name = "companyName")
	// 指定属性对应的数据库表的列为"companyName"
	private String companyName; // 公司名称
	@Column(name = "companyIntro")
	// 指定属性对应的数据库表的列为"companyIntro"
	private String companyIntro; // 公司简介
	@Column(name = "contact")
	// 指定属性对应的数据库表的列为"contact"
	private String contact; 	// 联系人
	@Column(name = "telephone")
	// 指定属性对应的数据库表的列为"telephone"
	private String telephone; 	// 联系电话
	@Column(name = "weburl")
	// 指定属性对应的数据库表的列为"weburl"
	private String weburl; 		// 公司网站
	@Column(name = "type")
	// 指定属性对应的数据库表的列为"type"
	private String type; 		// 公司性质
	@Column(name = "companyAddress")
	// 指定属性对应的数据库表的列为"companyAddress"
	private String companyAddress; // 公司地址
	@Column(name = "companyEmail")
	// 指定属性对应的数据库表的列为"companyEmail"
	private String companyEmail; // 公司邮箱
	@Column(name = "postalcode")
	// 指定属性对应的数据库表的列为"postalcode"
	private String postalcode; 	// 公司邮编
	@Column(name = "fax")
	// 指定属性对应的数据库表的列为"fax"
	private String fax; 		// 传真
	@Column(name = "note")
	// 指定属性对应的数据库表的列为"note"
	private String note; 		// 备注
	/**
	 * 
	 */
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the companyID
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * @param companyID the companyID to set
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyIntro
	 */
	public String getCompanyIntro() {
		return companyIntro;
	}
	/**
	 * @param companyIntro the companyIntro to set
	 */
	public void setCompanyIntro(String companyIntro) {
		this.companyIntro = companyIntro;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the weburl
	 */
	public String getWeburl() {
		return weburl;
	}
	/**
	 * @param weburl the weburl to set
	 */
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the companyEmail
	 */
	public String getCompanyEmail() {
		return companyEmail;
	}
	/**
	 * @param companyEmail the companyEmail to set
	 */
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	/**
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}
	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
