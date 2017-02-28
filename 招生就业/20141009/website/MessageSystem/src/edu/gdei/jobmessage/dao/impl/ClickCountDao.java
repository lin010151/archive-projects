/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IClickCountDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.ClickCount;

/**
 * @author dragonzhu
 * 
 */
@Repository("clickcountDao")
public class ClickCountDao extends AbstractHibernateDao<ClickCount> implements
		IClickCountDao {

	/**
	 * 
	 */
	public ClickCountDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(ClickCount.class);
	}

	@Override
	public void saveClick(String id) {
		// TODO Auto-generated method stub
		// 先找出该招聘岗位信息
		ClickCount clickcount=findOneByString(id);
		if (null==clickcount)
		{
			clickcount=new ClickCount();
			clickcount.setID(id);
			clickcount.setPostClickCount(0);
		}
		// 点击率增1
		int count=clickcount.getPostClickCount();
		if (count<=0)
			count=1;
		else
			count++;
		clickcount.setPostClickCount(count);
		create(clickcount);
	}

}
