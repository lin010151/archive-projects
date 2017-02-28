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
	// ָ������Ϊ������������Ҫʹ��int��ԭʼ��������
	private String postID; // ְλ��
	private Company company; // ��˾��
	@Column(name = "postName")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"postName"
	private String postName; // ְλ����
	@Column(name = "education")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"education"
	private String education; // �����̶�
	@Column(name = "recruitNum")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"recruitNum"
	private Integer recruitNum; // ��Ƹ����
	@Temporal(TemporalType.DATE)
	@Column(name = "releaseTime")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"releaseTime"
	private Date releaseTime; // ʱ��
	@Column(name = "jobsCategory")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"releaseTime"
	private String jobsCategory; // �������
	@Column(name = "jobsAddress")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"releaseTime"
	private String jobsAddress; // �����ص�
	@Column(name = "postRequirements")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"postRequirements"
	private String postRequirements; // ����Ҫ��
	@Column(name = "salary")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"salary"
	private Integer salary; // нˮ
	@Column(name = "major")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"major"
	private String major; // רҵҪ��
	@Column(name = "relatefile")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"relatefile"
	private String relatefile; // ����ļ�
	@Column(name = "note")
	// ָ�����Զ�Ӧ�����ݿ�����Ϊ"note"
	private String note; // ����ļ�
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
