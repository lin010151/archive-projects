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
@Table(name = "visit")
public class Visit  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	@Column(name="ID")
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String ID;				// ����
	@Column(name="stuID")
	private String stuID;			// ѧ��
	@Column(name = "postID")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"postID"
	private String postID;			// ��Ƹ��λ����
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
