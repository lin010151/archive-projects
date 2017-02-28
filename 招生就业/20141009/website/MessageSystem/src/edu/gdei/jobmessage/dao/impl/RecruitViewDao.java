/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IRecruitViewDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.RecruitInfoView;

/**
 * @author dragonzhu
 * 
 */
@Repository("recruitDao")
public class RecruitViewDao extends AbstractHibernateDao<RecruitInfoView> implements IRecruitViewDao {

	public RecruitViewDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(RecruitInfoView.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecruitInfoView> getRecruitByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		long firstResult = page * pageSize; // 计算从第几条记录开始查询
		long count = countTotal(); // 查找总记录数
		if (firstResult <= count) {
			if (firstResult + pageSize > count) // 如果要查询的记录数量超过了记录总数，则将页面最大记录数改为总数减去起始位置
				pageSize = (int) (count - firstResult);
			Criteria criteria = (Criteria) getCurrentSession().createCriteria(
					getClazz().getName());
			// 添加排序
			criteria.addOrder(Order.desc("releasetime"));
			criteria.setProjection(null); // 不作投影
			// 设置开始查询的位置
			criteria.setFirstResult((int) firstResult);
			// 设置最大的查询数量
			criteria.setMaxResults(pageSize);

			return criteria.list();
		}
		return null;
	}

}
