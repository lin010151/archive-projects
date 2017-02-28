/**
 * 
 */
package edu.gdei.jobmessage.dao;

import java.util.List;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Post;

/**
 * @author dragonzhu
 *
 */
public interface IPostDao extends IDao<Post> {
	//�����е�DAO��ʵ�ֻ����Ĳ����ӿ�IDao
    //����ʵ��IDao��������֮�⣬�ض���DAOҪʵ���������������ڶ�Ӧ�Ľӿ�DAO�ж��巽����
    //�˴�StudentDao�Ľӿ�IStudentDao����Ҫʵ����������
	// �������ж���
    public List<Post> findAllOfCompany(String companyid);
}
