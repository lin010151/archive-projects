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
	// ͨ������ID����һ������
	T findOneByLong(final long id);
	// ͨ���ַ���ID����һ������
	T findOneByString(final String id);
	// �������ж���
    List<T> findAll();
    // ��ҳ��ѯ
    List<T> findPage(int page, int pageSize);
    // �������
    void create(final T entity);
    // ���¶���
    T update(final T entity);
    // ɾ������
    void delete(final T entity);
    // ͨ��ID��ɾ������
    void deleteById(final long entityId);
    // ͨ��ID��ɾ������
    void deleteById(final String entityId);
    // ͳ�����ݿ�������
    Long countTotal();
}
