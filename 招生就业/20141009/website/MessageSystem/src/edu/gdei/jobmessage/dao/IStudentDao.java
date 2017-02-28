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
	//让所有的DAO都实现基本的操作接口IDao
    //除了实现IDao基本操作之外，特定的DAO要实现其他操作可以在对应的接口DAO中定义方法，
    //此处StudentDao的接口IStudentDao不需要实现其他方法
	public Student saveStudent(Student student);
}
