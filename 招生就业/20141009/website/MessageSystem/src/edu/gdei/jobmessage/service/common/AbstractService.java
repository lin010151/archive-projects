/**
 * 
 */
package edu.gdei.jobmessage.service.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.gdei.jobmessage.dao.common.IDao;

/**
 * @author dragonzhu
 *
 */
@Transactional
public abstract class AbstractService<T extends Serializable> implements IDao<T> {
	
	protected abstract IDao<T> getDao();

    @Override
    public T findOneByLong(final long id) {
        return getDao().findOneByLong(id);
    }
    
    @Override
    public T findOneByString(final String id) {
        return getDao().findOneByString(id);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    /* (non-Javadoc)
	 * @see edu.gdei.jobmessage.dao.common.IDao#findPage(int, int)
	 */
	@Override
	public List<T> findPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return getDao().findPage(page, pageSize);
	}

	@Override
    public void create(final T entity) {
        getDao().create(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().update(entity);
    }

    @Override
    public void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        getDao().deleteById(entityId);
    }

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.dao.common.IDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String entityId) {
		// TODO Auto-generated method stub
		getDao().deleteById(entityId);
	}

	/* (non-Javadoc)
	 * @see edu.gdei.jobmessage.dao.common.IDao#countTotal()
	 */
	@Override
	public Long countTotal() {
		// TODO Auto-generated method stub
		return getDao().countTotal();
	}

}
