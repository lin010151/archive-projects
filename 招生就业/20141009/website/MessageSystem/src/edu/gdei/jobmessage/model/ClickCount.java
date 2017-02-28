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
@Table(name = "clickcount")
public class ClickCount  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	private String ID;				// 主键
	@Column(name="postClickCount")
	private Integer postClickCount;	// 访问计数
	/**
	 * 
	 */
	public ClickCount() {
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
	 * @return the postClickCount
	 */
	public Integer getPostClickCount() {
		return postClickCount;
	}
	/**
	 * @param postClickCount the postClickCount to set
	 */
	public void setPostClickCount(Integer postClickCount) {
		this.postClickCount = postClickCount;
	}
}
