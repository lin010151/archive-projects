/**
 * 
 */
package edu.department.employment.infosys.data;

/**
 * @author dragonzhu
 *
 */
public class GlobalDataInstance {

	private static GlobalDataInstance instance;			// 单例
	
	private int screenWidth;							// 屏幕宽度
	
	private GlobalDataInstance ()
	{
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
}
