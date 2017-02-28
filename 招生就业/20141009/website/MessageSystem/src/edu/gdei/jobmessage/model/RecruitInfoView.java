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
// 注解Entity表示该类能被Hibernate持久化
@Table(name = "recruitinfo_view")
public class RecruitInfoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8354543622885891462L;
	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	private String id; // id号
	@Column(name = "comname")
	// 指定属性对应的数据库表的列为"comname"
	private String comname; // 公司名字
	@Temporal(TemporalType.DATE)
	@Column(name = "releasetime")
	// 指定属性对应的数据库表的列为"releasetime"
	private Date releasetime; // 时间
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
