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
// ע��Entity��ʾ�����ܱ�Hibernate�־û�
@Table(name = "company")
public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7958916606950054994L;
	@Id
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String companyID; 	// ��˾��
	@Column(name = "companyName")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"companyName"
	private String companyName; // ��˾����
	@Column(name = "companyIntro")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"companyIntro"
	private String companyIntro; // ��˾���
	@Column(name = "contact")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"contact"
	private String contact; 	// ��ϵ��
	@Column(name = "telephone")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"telephone"
	private String telephone; 	// ��ϵ�绰
	@Column(name = "weburl")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"weburl"
	private String weburl; 		// ��˾��վ
	@Column(name = "type")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"type"
	private String type; 		// ��˾����
	@Column(name = "companyAddress")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"companyAddress"
	private String companyAddress; // ��˾��ַ
	@Column(name = "companyEmail")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"companyEmail"
	private String companyEmail; // ��˾����
	@Column(name = "postalcode")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"postalcode"
	private String postalcode; 	// ��˾�ʱ�
	@Column(name = "fax")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"fax"
	private String fax; 		// ����
	@Column(name = "note")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"note"
	private String note; 		// ��ע
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
