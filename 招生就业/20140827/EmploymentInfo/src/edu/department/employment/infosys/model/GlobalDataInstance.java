/**
 * 
 */
package edu.department.employment.infosys.model;

/**
 * @author dragonzhu
 *
 */
public class GlobalDataInstance {

	private static GlobalDataInstance instance;			// 单例
	
	private int screenWidth;							// 屏幕宽度
	private Student student;							//保存用户ID
	
	private GlobalDataInstance ()
	{
		student=new Student();
	}
	
	// 返回单例
	public static GlobalDataInstance GetInstance()
	{
		if (null==instance)
		{
			instance=new GlobalDataInstance();
		}
		return instance;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
}
