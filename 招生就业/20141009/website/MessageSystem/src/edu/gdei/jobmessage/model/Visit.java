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
@Table(name = "visit")
public class Visit  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	@Column(name="ID")
	// 指定该列为主键，尽量不要使用int等原始数据类型
	private String ID;				// 主键
	@Column(name="stuID")
	private String stuID;			// 学号
	@Column(name = "postID")
	// 指定属性对应的数据库表的列为"postID"
	private String postID;			// 招聘岗位主键
	/**
	 * 
	 */
	public Visit() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
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
	 * @return the postID
	 */
	public String getPostID() {
		return postID;
	}
	/**
	 * @param postID the postID to set
	 */
	public void setPostID(String postID) {
		this.postID = postID;
	}
}
