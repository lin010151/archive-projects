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
@Table(name = "major")
public class Major implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id									// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	@Column(name="majorID")
	private String majorID;				// רҵID
	@Column(name="majorName")			// ָ�����Զ�Ӧ�����ݿ�����Ϊ"majorName"
	private String majorName;			// רҵ����
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
