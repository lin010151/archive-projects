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
//注解Entity表示该类能被Hibernate持久化
@Table(name = "address")
public class Address  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	@Column(name="addressID")
	private String addressID;		// 地址号
	@Column(name = "addressName")
	// 指定属性对应的数据库表的列为"addressName"
	private String addressName;		// 省份
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
