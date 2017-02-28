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
@Table(name = "clickcount")
public class ClickCount  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164814096924689557L;
	@Id
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String ID;				// ����
	@Column(name="postClickCount")
	private Integer postClickCount;	// ���ʼ���
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
