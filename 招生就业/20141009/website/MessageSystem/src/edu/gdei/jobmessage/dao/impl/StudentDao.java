/**
 * 
 */
package edu.gdei.jobmessage.dao.impl;

import org.springframework.stereotype.Repository;

import edu.gdei.jobmessage.dao.IStudentDao;
import edu.gdei.jobmessage.dao.common.AbstractHibernateDao;
import edu.gdei.jobmessage.model.Login;
import edu.gdei.jobmessage.model.Student;

/**
 * @author dragonzhu
 *
 */
@Repository("studentDao")
public class StudentDao extends AbstractHibernateDao<Student> implements IStudentDao {

	public StudentDao() {
		super();
		// TODO Auto-generated constructor stub
		setClazz(Student.class);
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
        getCurrentSession().save(student);			// �ȱ���ѧ����Ϣ�����������ݿ���������������⣬�����ܲ��롣
		// ������¼��Ϣ
		Login login=new Login();
		login.setUserID(student.getStuID());		// ��ѧ��������
        login.setUserPSW(student.getStuIDCard());	// �����֤������
        
        getCurrentSession().save(login);			// �����¼��Ϣ
        
		return student;
	}

}
