/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IUserDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.User;
import edu.gdei.jobmessage.util.UserNotFoundException;

/**
 * @author dragonzhu
 * 
 */
@Repository("userDao")
public class UserDao extends AbstractHibernateDao<User> implements IUserDao {

	/**
	 * 
	 */
	public UserDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(User.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IUserDao#getUser(java.lang.Long)
	 */
	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return findOneByString(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.gdei.jobmessage.dao.IUserDao#saveUser(edu.gdei.jobmessage.model.User)
	 */
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		create(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IUserDao#removeUser(java.lang.Long)
	 */
	@Override
	public void removeUser(String id) {
		// TODO Auto-generated method stub
		deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IUserDao#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return findOneByString(id) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IUserDao#getUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery(
				"from " + getClazz().getName()
						+ " u order by u.createdDate desc").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		long firstResult = page * pageSize; // ����ӵڼ�����¼��ʼ��ѯ
		long count = countTotal(); // �����ܼ�¼��
		if (firstResult <= count) {
			if (firstResult + pageSize > count) // ���Ҫ��ѯ�ļ�¼���������˼�¼��������ҳ������¼����Ϊ������ȥ��ʼλ��
				pageSize = (int) (count - firstResult);
			Criteria criteria = (Criteria) getCurrentSession().createCriteria(
					getClazz().getName());
			// �������
			criteria.addOrder(Order.desc("createdDate"));
			criteria.setProjection(null); // ����ͶӰ
			// ���ÿ�ʼ��ѯ��λ��
			criteria.setFirstResult((int) firstResult);
			// �������Ĳ�ѯ����
			criteria.setMaxResults(pageSize);

			return criteria.list();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.IUserDao#getUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUsername(String username) throws UserNotFoundException {
		// TODO Auto-generated method stub
		List<User> users =  getCurrentSession().createQuery(
				"from User where username='" + username + "'")
				.list();
		if (users == null || users.size()==0) {
			return null;
		} else {
			return (User)users.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUsersForNotify(String majorid, String addressid,
			int sex) {
		// TODO Auto-generated method stub

		// ��װhql���
		String hql = "select u.username from User u, Student s where u.username=s.stuID";
		if (!"0".equals(majorid))
			hql += " and s.major.majorID='" + majorid + "'";
		if (!"0".equals(addressid))
			hql += " and s.address.addressID='" + addressid + "'";
		if (sex < 2)
			hql += " and s.stuSex=" + sex;

		return getCurrentSession().createQuery(hql).list();
	}

}
