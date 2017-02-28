/**
 * 
 */
package edu.gdei.jobmessage.service;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Student;

/**
 * @author dragonzhu
 *
 */
public interface IStudentService extends IDao<Student> {
	public Student saveStudent(Student student);
}
