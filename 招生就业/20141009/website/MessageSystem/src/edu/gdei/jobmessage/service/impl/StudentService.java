/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IStudentDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Student;
import edu.gdei.jobmessage.service.IStudentService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 * 
 */
@Service("studentService")
public class StudentService extends AbstractService<Student> implements
		IStudentService {

	@Resource(name = "studentDao")
	private IStudentDao studentDao;

	public StudentService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the studentDao
	 */
	public IStudentDao getStudentDao() {
		return studentDao;
	}

	/**
	 * @param studentDao
	 *            the studentDao to set
	 */
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	protected IDao<Student> getDao() {
		// TODO Auto-generated method stub
		return this.studentDao;
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		return this.studentDao.saveStudent(student);
	}
}
