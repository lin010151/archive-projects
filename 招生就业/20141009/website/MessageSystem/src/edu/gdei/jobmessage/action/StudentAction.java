/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Address;
import edu.gdei.jobmessage.model.Major;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.Student;
import edu.gdei.jobmessage.service.IAddressService;
import edu.gdei.jobmessage.service.IMajorService;
import edu.gdei.jobmessage.service.IStudentService;
import edu.gdei.jobmessage.util.UuidUtil;

/**
 * @author dragonzhu
 * 
 */
public class StudentAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize; // 每页数量
	private Long totalsize; // 总数量

	private String update; // 是否更新学生 信息
	private String delete; // 是否删除学生信息
	private String insert; // 是否插入学生信息
	private String find; // 是否查找并修改
	private String pageNo; // 页码
	private String stuID; // 学号
	// 上传文件的名字
	private String excelFileFileName;

	private File excelFile; // 上传的文件

	private Manager manager; // 管理员号
	private Student student; // 学生信息

	@Resource(name = "studentService")
	private IStudentService studentService; // 学生信息服务
	@Resource(name = "majorService")
	private IMajorService majorService; // 学生信息服务
	@Resource(name = "addressService")
	private IAddressService addressService; // 学生信息服务

	private List<Student> list = new ArrayList<Student>();

	/**
	 * @return the totalsize
	 */
	public Long getTotalsize() {
		return totalsize;
	}

	/**
	 * @param totalsize
	 *            the totalsize to set
	 */
	public void setTotalsize(Long totalsize) {
		this.totalsize = totalsize;
	}

	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}

	/**
	 * @return the delete
	 */
	public String getDelete() {
		return delete;
	}

	/**
	 * @param delete
	 *            the delete to set
	 */
	public void setDelete(String delete) {
		this.delete = delete;
	}

	/**
	 * @return the insert
	 */
	public String getInsert() {
		return insert;
	}

	/**
	 * @param insert
	 *            the insert to set
	 */
	public void setInsert(String insert) {
		this.insert = insert;
	}

	/**
	 * @return the find
	 */
	public String getFind() {
		return find;
	}

	/**
	 * @param find
	 *            the find to set
	 */
	public void setFind(String find) {
		this.find = find;
	}

	/**
	 * @return the pageNo
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the list
	 */
	public List<Student> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Student> list) {
		this.list = list;
	}

	/**
	 * @return the stuID
	 */
	public String getStuID() {
		return stuID;
	}

	/**
	 * @param stuID
	 *            the stuID to set
	 */
	public void setStuID(String stuID) {
		this.stuID = stuID;
	}

	/**
	 * @return the manager
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the studentService
	 */
	public IStudentService getStudentService() {
		return studentService;
	}

	/**
	 * @param studentService
	 *            the studentService to set
	 */
	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (pageNo == null) // 如果页面号为空，则从session中读取
		{
			pageNo = (String) getSession().get("stuPage");
			if (null == pageNo) // 如果session会话也没有的话，则设为1
				pageNo = "1";
		}
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			if ("1".equals(find)) // 查找单个学生并进行修改
			{
				student = studentService.findOneByString(stuID);
				if (null != student)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(delete)) // 删除学生信息
			{
				studentService.deleteById(stuID);
			}
			if ("1".equals(update)) // 更新学生信息
			{
				studentService.update(student);
			}
			if ("1".equals(insert)) // 批量插入学生信息
			{
				// 创建工作册
				Workbook book = createWorkBook(new FileInputStream(excelFile));

				Sheet sheet = book.getSheetAt(0); // 取第一个表单
				// 保存工作单名称
				Row firstRow = sheet.getRow(0);
				Iterator<Cell> iterator = firstRow.iterator();

				// 保存列名
				List<String> cellNames = new ArrayList<String>();
				while (iterator.hasNext()) {
					cellNames.add(iterator.next().getStringCellValue());
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row ros = sheet.getRow(i);
					Student g = new Student();
					// 设置学生姓名
					g.setStuName(ros.getCell(0).getStringCellValue());
					// 设置学生学号
					g.setStuID(ros.getCell(1).getStringCellValue());
					// 设置身份证号码
					g.setStuIDCard(ros.getCell(2).getStringCellValue());
					// 设置专业信息
					Major major = getMajor(ros.getCell(3).getStringCellValue());
					if (null == major)
						continue;
					g.setMajor(major);
					// 设置性别(男：0，女：1)
					if ("男".equals(ros.getCell(4).getStringCellValue()))
						g.setStuSex(0);
					else
						g.setStuSex(1);
					// 设置生源地
					Address address = getAddress(ros.getCell(5)
							.getStringCellValue());
					if (null == address)
						continue;
					g.setAddress(address);
					// 设置政治面貌
					if ("中国共产党党员".equals(ros.getCell(6).getStringCellValue()))
						g.setStuPolitical(0);
					else if ("中共预备党员".equals(ros.getCell(6)
							.getStringCellValue()))
						g.setStuPolitical(1);
					else if ("共青团员".equals(ros.getCell(6).getStringCellValue()))
						g.setStuPolitical(2);
					else
						g.setStuPolitical(3);
					// 设置邮箱
					g.setStuEmail(ros.getCell(7).getStringCellValue());
					// 添加到毕业生列表中
					studentService.saveStudent(g);
				}
			}

			totalsize = (long) 0; // 赋予初始值
			totalsize = studentService.countTotal(); // 返回学生的数量
			pageSize = 20; // 每页最多只取20个学生信息
			// 返回学生列表
			list = studentService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("stuPage", pageNo); // 保存页码信息在session会话中
			return "success";
		}

		return "fail";
	}

	// 判断文件类型
	public Workbook createWorkBook(InputStream is) throws IOException {
		if (excelFileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (excelFileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	// 获取专业信息
	@SuppressWarnings("static-access")
	private Major getMajor(String name) {
		Major m = null;
		// 获取所有专业信息
		List<Major> majorlist = new ArrayList<Major>();
		majorlist = majorService.findAll();
		// 查找专业信息
		for (int i = 0; i < majorlist.size(); i++) {
			if (name.equals(majorlist.get(i).getMajorName())) {
				m = majorlist.get(i);
				break;
			}
		}
		if (null == m) // 如果没找到专业信息，则创建之
		{
			m = new Major(); // 实例化专业信息对象
			UuidUtil uuidUtil = new UuidUtil(); // 获取UUID
			m.setMajorID(uuidUtil.getCode()); // 设置主键
			m.setMajorName(name); // 设置专业名称
			majorService.create(m); // 保存专业信息
		}

		return m;
	}

	// 获取生源地
	@SuppressWarnings("static-access")
	private Address getAddress(String name) {
		// TODO Auto-generated method stub
		Address a = null;
		// 获取所有生源地信息
		List<Address> addresslist = new ArrayList<Address>();
		addresslist = addressService.findAll();
		// 查找生源地信息
		for (int i = 0; i < addresslist.size(); i++) {
			if (name.equals(addresslist.get(i).getAddressName())) {
				a = addresslist.get(i);
				break;
			}
		}
		if (null == a) // 如果没有找到生源地信息，则创建之
		{
			a = new Address(); // 实例化生源地信息对象
			UuidUtil uuidUtil = new UuidUtil(); // 获取UUID
			a.setAddressID(uuidUtil.getCode()); // 设置主键
			a.setAddressName(name); // 设置生源地
			addressService.create(a); // 保存生源地
		}
		return a;
	}

	/**
	 * @return the excelFileFileName
	 */
	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	/**
	 * @param excelFileFileName
	 *            the excelFileFileName to set
	 */
	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	/**
	 * @return the excelFile
	 */
	public File getExcelFile() {
		return excelFile;
	}

	/**
	 * @param excelFile
	 *            the excelFile to set
	 */
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

}
