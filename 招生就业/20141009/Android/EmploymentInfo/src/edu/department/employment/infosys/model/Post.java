/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 * 
 */
public class Post {

	private String postID; 				// 职位号
	private String postName; 			// 职位名称
	private String education; 			// 教育程度
	private Integer recruitNum; 		// 招聘人数
	private String releaseTime; 			// 时间
	private String jobsCategory; 		// 工作类别
	private String jobsAddress; 		// 工作地点
	private String postRequirements; 	// 工作要求
	private Integer salary; 			// 薪水
	private String major; 				// 专业要求
	private String relatefile; 			// 相关文件
	private String note; 				// 相关文件
	private String postinfo;			// 招聘简短信息
	/**
	 * @param postID
	 * @param postName
	 * @param education
	 * @param recruitNum
	 * @param releaseTime
	 * @param jobsCategory
	 * @param jobsAddress
	 * @param postRequirements
	 * @param salary
	 * @param major
	 * @param relatefile
	 * @param note
	 * @param postinfo
	 */
	public Post() {
		this.postID = "";
		this.postName = "";
		this.education = "";
		this.recruitNum = 0;
		this.releaseTime = "";
		this.jobsCategory = "";
		this.jobsAddress = "";
		this.postRequirements = "";
		this.salary = 0;
		this.major = "";
		this.relatefile = "";
		this.note = "";
		this.postinfo = "";
	}
	/**
	 * @return the postID
	 */
	public final String getPostID() {
		return postID;
	}
	/**
	 * @param postID the postID to set
	 */
	public final void setPostID(String postID) {
		this.postID = postID;
	}
	/**
	 * @return the postName
	 */
	public final String getPostName() {
		return postName;
	}
	/**
	 * @param postName the postName to set
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
	 * @param education the education to set
	 */
	public final void setEducation(String education) {
		this.education = education;
	}
	/**
	 * @return the recruitNum
	 */
	public final Integer getRecruitNum() {
		return recruitNum;
	}
	/**
	 * @param recruitNum the recruitNum to set
	 */
	public final void setRecruitNum(Integer recruitNum) {
		this.recruitNum = recruitNum;
	}
	/**
	 * @return the releaseTime
	 */
	public final String getReleaseTime() {
		return releaseTime;
	}
	/**
	 * @param releaseTime the releaseTime to set
	 */
	public final void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	/**
	 * @return the jobsCategory
	 */
	public final String getJobsCategory() {
		return jobsCategory;
	}
	/**
	 * @param jobsCategory the jobsCategory to set
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
	 * @param jobsAddress the jobsAddress to set
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
	 * @param postRequirements the postRequirements to set
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
	 * @param salary the salary to set
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
	 * @param major the major to set
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
	 * @param relatefile the relatefile to set
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
	 * @param note the note to set
	 */
	public final void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the postinfo
	 */
	public final String getPostinfo() {
		return postinfo;
	}
	/**
	 * @param postinfo the postinfo to set
	 */
	public final void setPostinfo(String postinfo) {
		this.postinfo = postinfo;
	}

}
