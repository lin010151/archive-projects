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
@Table(name = "post")
public class Post implements Serializable {
	/**
	 * 
	 */
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1739970399094097471L;
	@Id
	// 指定该列为主键，尽量不要使用int等原始数据类型
	private String postID; // 职位号
	private Company company; // 公司号
	@Column(name = "postName")
	// 指定属性对应的数据库表的列为"postName"
	private String postName; // 职位名称
	@Column(name = "education")
	// 指定属性对应的数据库表的列为"education"
	private String education; // 教育程度
	@Column(name = "recruitNum")
	// 指定属性对应的数据库表的列为"recruitNum"
	private Integer recruitNum; // 招聘人数
	@Temporal(TemporalType.DATE)
	@Column(name = "releaseTime")
	// 指定属性对应的数据库表的列为"releaseTime"
	private Date releaseTime; // 时间
	@Column(name = "jobsCategory")
	// 指定属性对应的数据库表的列为"releaseTime"
	private String jobsCategory; // 工作类别
	@Column(name = "jobsAddress")
	// 指定属性对应的数据库表的列为"releaseTime"
	private String jobsAddress; // 工作地点
	@Column(name = "postRequirements")
	// 指定属性对应的数据库表的列为"postRequirements"
	private String postRequirements; // 工作要求
	@Column(name = "salary")
	// 指定属性对应的数据库表的列为"salary"
	private Integer salary; // 薪水
	@Column(name = "major")
	// 指定属性对应的数据库表的列为"major"
	private String major; // 专业要求
	@Column(name = "relatefile")
	// 指定属性对应的数据库表的列为"relatefile"
	private String relatefile; // 相关文件
	@Column(name = "note")
	// 指定属性对应的数据库表的列为"note"
	private String note; // 相关文件
	@Transient
	private String postinfo;
	@Transient
	private String recruitDate;

	/**
	 * @return the postID
	 */
	public final String getPostID() {
		return postID;
	}

	/**
	 * @param postID
	 *            the postID to set
	 */
	public final void setPostID(String postID) {
		this.postID = postID;
	}

	/**
	 * @return the company
	 */
	public final Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public final void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the postName
	 */
	public final String getPostName() {
		return postName;
	}

	/**
	 * @param postName
	 *            the postName to set
	 */
	public final void setPostName(String postName) {
		this.postName = postName;
	}

	/**
	 * @return the education
	 */
	public final String getEducation() {
		return education;
	}

	/**
	 * @param education
	 *            the education to set
	 */
	public final void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the releaseTime
	 */
	public final Date getReleaseTime() {
		return releaseTime;
	}

	/**
	 * @param releaseTime
	 *            the releaseTime to set
	 */
	public final void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * @return the jobsCategory
	 */
	public final String getJobsCategory() {
		return jobsCategory;
	}

	/**
	 * @param jobsCategory
	 *            the jobsCategory to set
	 */
	public final void setJobsCategory(String jobsCategory) {
		this.jobsCategory = jobsCategory;
	}

	/**
	 * @return the jobsAddress
	 */
	public final String getJobsAddress() {
		return jobsAddress;
	}

	/**
	 * @param jobsAddress
	 *            the jobsAddress to set
	 */
	public final void setJobsAddress(String jobsAddress) {
		this.jobsAddress = jobsAddress;
	}

	/**
	 * @return the postRequirements
	 */
	public final String getPostRequirements() {
		return postRequirements;
	}

	/**
	 * @param postRequirements
	 *            the postRequirements to set
	 */
	public final void setPostRequirements(String postRequirements) {
		this.postRequirements = postRequirements;
	}

	/**
	 * @return the salary
	 */
	public final Integer getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public final void setSalary(Integer salary) {
		this.salary = salary;
	}

	/**
	 * @return the major
	 */
	public final String getMajor() {
		return major;
	}

	/**
	 * @param major
	 *            the major to set
	 */
	public final void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the relatefile
	 */
	public final String getRelatefile() {
		return relatefile;
	}

	/**
	 * @param relatefile
	 *            the relatefile to set
	 */
	public final void setRelatefile(String relatefile) {
		this.relatefile = relatefile;
	}

	/**
	 * @return the note
	 */
	public final String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public final void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the recruitNum
	 */
	public final Integer getRecruitNum() {
		return recruitNum;
	}

	/**
	 * @param recruitNum
	 *            the recruitNum to set
	 */
	public final void setRecruitNum(Integer recruitNum) {
		this.recruitNum = recruitNum;
	}

	/**
	 * @return the postinfo
	 */
	public String getPostinfo() {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (null != releaseTime)
			postinfo = postName + "(" + fmt.format(releaseTime) + ")";
		else
			postinfo = null;
		return postinfo;
	}

	/**
	 * @param postinfo
	 *            the postinfo to set
	 */
	public void setPostinfo(String postinfo) {
		this.postinfo = postinfo;
	}

	/**
	 * @return the recruitDate
	 */
	public String getRecruitDate() {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (null != releaseTime)
			recruitDate = fmt.format(releaseTime);
		else
			recruitDate = null;
		return recruitDate;
	}

	/**
	 * @param recruitDate
	 *            the recruitDate to set
	 */
	public void setRecruitDate(String recruitDate) {
		this.recruitDate = recruitDate;
	}
}
