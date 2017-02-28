/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 * 
 */
public class Company {
	
	private String companyID; 	// 公司号
	private String companyName; // 公司名称
	private String companyIntro;// 公司简介
	private String contact; 	// 联系人
	private String telephone; 	// 联系电话
	private String weburl; 		// 公司网站
	private String type; 		// 公司性质
	private String companyAddress; // 公司地址
	private String companyEmail;// 公司邮箱
	private String postalcode; 	// 公司邮编
	private String fax; 		// 传真
	private String note; 		// 备注
	
	/**
	 * @param companyID
	 * @param companyName
	 * @param companyIntro
	 * @param contact
	 * @param telephone
	 * @param weburl
	 * @param type
	 * @param companyAddress
	 * @param companyEmail
	 * @param postalcode
	 * @param fax
	 * @param note
	 */
	public Company() {
		this.companyID = "";
		this.companyName = "";
		this.companyIntro = "";
		this.contact = "";
		this.telephone = "";
		this.weburl = "";
		this.type = "";
		this.companyAddress = "";
		this.companyEmail = "";
		this.postalcode = "";
		this.fax = "";
		this.note = "";
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
