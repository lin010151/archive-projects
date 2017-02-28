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
@Table(name = "major")
public class Major implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id									// 指定该列为主键，尽量不要使用int等原始数据类型
	@Column(name="majorID")
	private String majorID;				// 专业ID
	@Column(name="majorName")			// 指定属性对应的数据库表的列为"majorName"
	private String majorName;			// 专业名称
	/**
	 * 
	 */
	public Major() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the majorID
	 */
	public String getMajorID() {
		return majorID;
	}
	/**
	 * @param majorID the majorID to set
	 */
	public void setMajorID(String majorID) {
		this.majorID = majorID;
	}
	/**
	 * @return the majorName
	 */
	public String getMajorName() {
		return majorName;
	}
	/**
	 * @param majorName the majorName to set
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
