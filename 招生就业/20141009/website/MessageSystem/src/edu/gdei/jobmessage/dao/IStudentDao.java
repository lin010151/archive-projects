/**
 * 
 */
package edu.gdei.jobmessage.dao;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Student;

/**
 * @author dragonzhu
 *
 */
public interface IStudentDao extends IDao<Student> {
	//�����е�DAO��ʵ�ֻ����Ĳ����ӿ�IDao
    //����ʵ��IDao��������֮�⣬�ض���DAOҪʵ���������������ڶ�Ӧ�Ľӿ�DAO�ж��巽����
    //�˴�StudentDao�Ľӿ�IStudentDao����Ҫʵ����������
	public Student saveStudent(Student student);
}
