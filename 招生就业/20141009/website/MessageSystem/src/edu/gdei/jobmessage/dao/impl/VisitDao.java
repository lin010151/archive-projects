/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IVisitDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Visit;
import edu.gdei.jobmessage.util.UuidUtil;

/**
 * @author dragonzhu
 * 
 */
@Repository("visitDao")
public class VisitDao extends AbstractHibernateDao<Visit> implements IVisitDao {

	/**
	 * 
	 */
	public VisitDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Visit.class);
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public boolean saveVisit(String stuid, String postid) {
		// TODO Auto-generated method stub
		String hql = "from " + getClazz().getName() + " where stuID='" + stuid
				+ "' and postID='" + postid + "'";
		List<Visit> list = new ArrayList<Visit>();
		list = getCurrentSession().createQuery(hql).list();
		if (list.size() < 1) // 如果尚未保存则保存之
		{
			Visit visit = new Visit();
			UuidUtil uuidUtil = new UuidUtil(); // 生成主键ID
			visit.setID(uuidUtil.getCode()); // 设置主键ID
			visit.setStuID(stuid);
			visit.setPostID(postid);
			create(visit);
			return true;
		} else
			return false;
	}
}
