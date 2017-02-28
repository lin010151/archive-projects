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
        getCurrentSession().save(student);			// 先保存学生信息，否则因数据库表主键关联的问题，将不能插入。
		// 创建登录信息
		Login login=new Login();
		login.setUserID(student.getStuID());		// 以学号做主键
        login.setUserPSW(student.getStuIDCard());	// 以身份证做密码
        
        getCurrentSession().save(login);			// 保存登录信息
        
		return student;
	}

}
