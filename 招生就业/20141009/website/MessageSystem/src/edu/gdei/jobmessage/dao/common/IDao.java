/**
 * 
 */
package edu.gdei.jobmessage.dao.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragonzhu
 *
 */
public interface IDao<T extends Serializable> {
	// 通过数字ID查找一个对象
	T findOneByLong(final long id);
	// 通过字符串ID查找一个对象
	T findOneByString(final String id);
	// 查找所有对象
    List<T> findAll();
    // 分页查询
    List<T> findPage(int page, int pageSize);
    // 保存对象
    void create(final T entity);
    // 更新对象
    T update(final T entity);
    // 删除对象
    void delete(final T entity);
    // 通过ID号删除对象
    void deleteById(final long entityId);
    // 通过ID号删除对象
    void deleteById(final String entityId);
    // 统计数据库表的总数
    Long countTotal();
}
