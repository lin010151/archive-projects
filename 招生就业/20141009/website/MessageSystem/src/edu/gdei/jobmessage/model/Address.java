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
//ע��Entity��ʾ�����ܱ�Hibernate�־û�
@Table(name = "address")
public class Address  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	@Column(name="addressID")
	private String addressID;		// ��ַ��
	@Column(name = "addressName")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"addressName"
	private String addressName;		// ʡ��
	/**
	 * 
	 */
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the addressID
	 */
	public String getAddressID() {
		return addressID;
	}
	/**
	 * @param addressID the addressID to set
	 */
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	/**
	 * @return the addressName
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * @param addressName the addressName to set
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
}
