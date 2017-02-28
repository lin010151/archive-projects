/**
 * 
 */
package edu.gdei.jobmessage.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author dragonzhu
 * 
 */
@Entity
// ע��Entity��ʾ�����ܱ�Hibernate�־û�
@Table(name = "recruitinfo_view")
public class RecruitInfoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8354543622885891462L;
	@Id
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String id; // id��
	@Column(name = "comname")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"comname"
	private String comname; // ��˾����
	@Temporal(TemporalType.DATE)
	@Column(name = "releasetime")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"releasetime"
	private Date releasetime; // ʱ��
	@Transient
	private String companyinfo;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the comname
	 */
	public String getComname() {
		return comname;
	}

	/**
	 * @param comname
	 *            the comname to set
	 */
	public void setComname(String comname) {
		this.comname = comname;
	}

	/**
	 * @return the releasetime
	 */
	public Date getReleasetime() {
		return releasetime;
	}

	/**
	 * @param releasetime
	 *            the releasetime to set
	 */
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}

	/**
	 * @return the companyinfo
	 */
	public String getCompanyinfo() {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (null != releasetime)
			companyinfo = comname + "(" + fmt.format(releasetime) + ")";
		else
			companyinfo = comname;
		return companyinfo;
	}

	/**
	 * @param companyinfo
	 *            the companyinfo to set
	 */
	public void setCompanyinfo(String companyinfo) {
		this.companyinfo = companyinfo;
	}
}
