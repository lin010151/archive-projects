/**
 * 
 */
package edu.department.employment.infosys.data;

/**
 * @author dragonzhu
 *
 */
public class GlobalDataInstance {

	private static GlobalDataInstance instance;			// ����
	
	private int screenWidth;							// ��Ļ���
	
	private GlobalDataInstance ()
	{
	}
	
	// ���ص���
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
