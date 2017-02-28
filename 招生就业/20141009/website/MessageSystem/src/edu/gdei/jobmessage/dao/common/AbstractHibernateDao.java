/**
 * 
 */
package edu.gdei.jobmessage.dao.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

/**
 * @author dragonzhu
 * 
 */
public abstract class AbstractHibernateDao<T extends Serializable> implements
		IDao<T> {

	private Class<T> clazz;

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * @return the clazz
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	protected final void setClazz(final Class<T> clazzToSet) {
		this.clazz = Preconditions.checkNotNull(clazzToSet);
	}

	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T findOneByLong(final long id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T findOneByString(final String id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName())
				.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.common.IDao#findPage(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final List<T> findPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		long firstResult = page * pageSize; // 计算从第几条记录开始查询
		long count = countTotal(); // 查找总记录数
		if (firstResult <= count) {
			if (firstResult + pageSize > count) // 如果要查询的记录数量超过了记录总数，则将页面最大记录数改为总数减去起始位置
				pageSize = (int) (count - firstResult);
			Criteria criteria = (Criteria) getCurrentSession().createCriteria(clazz.getName());
			criteria.setProjection(null);
			criteria.setFirstResult((int) firstResult);
			criteria.setMaxResults(pageSize);
			return criteria.list();
		}
		return null;
	}

	@Override
	public final void create(final T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public final T update(final T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().update(entity);
		return entity;
	}

	@Override
	public final void delete(final T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().delete(entity);
	}

	@Override
	public final void deleteById(final long entityId) {
		final T entity = findOneByLong(entityId);
		Preconditions.checkState(entity != null);
		delete(entity);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.dao.common.IDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String entityId) {
		// TODO Auto-generated method stub
		final T entity = findOneByString(entityId);
		Preconditions.checkState(entity != null);
		delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.gdei.jobmessage.dao.common.IDao#countTotal(java.io.Serializable)
	 */
	@Override
	public final Long countTotal() {
		// TODO Auto-generated method stub
		return (Long) getCurrentSession()
				.createQuery("select count(*) from " + clazz.getName()).uniqueResult();
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
