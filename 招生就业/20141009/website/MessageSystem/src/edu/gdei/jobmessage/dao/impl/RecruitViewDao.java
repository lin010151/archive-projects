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
		long firstResult = page * pageSize; // ����ӵڼ�����¼��ʼ��ѯ
		long count = countTotal(); // �����ܼ�¼��
		if (firstResult <= count) {
			if (firstResult + pageSize > count) // ���Ҫ��ѯ�ļ�¼���������˼�¼��������ҳ������¼����Ϊ������ȥ��ʼλ��
				pageSize = (int) (count - firstResult);
			Criteria criteria = (Criteria) getCurrentSession().createCriteria(
					getClazz().getName());
			// �������
			criteria.addOrder(Order.desc("releasetime"));
			criteria.setProjection(null); // ����ͶӰ
			// ���ÿ�ʼ��ѯ��λ��
			criteria.setFirstResult((int) firstResult);
			// �������Ĳ�ѯ����
			criteria.setMaxResults(pageSize);

			return criteria.list();
		}
		return null;
	}

}
