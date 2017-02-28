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
	private int pageSize; // ÿҳ����
	private Long totalsize; // ������

	private String update; // �Ƿ����ѧ�� ��Ϣ
	private String delete; // �Ƿ�ɾ��ѧ����Ϣ
	private String insert; // �Ƿ����ѧ����Ϣ
	private String find; // �Ƿ���Ҳ��޸�
	private String pageNo; // ҳ��
	private String stuID; // ѧ��
	// �ϴ��ļ�������
	private String excelFileFileName;

	private File excelFile; // �ϴ����ļ�

	private Manager manager; // ����Ա��
	private Student student; // ѧ����Ϣ

	@Resource(name = "studentService")
	private IStudentService studentService; // ѧ����Ϣ����
	@Resource(name = "majorService")
	private IMajorService majorService; // ѧ����Ϣ����
	@Resource(name = "addressService")
	private IAddressService addressService; // ѧ����Ϣ����

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
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (pageNo == null) // ���ҳ���Ϊ�գ����session�ж�ȡ
		{
			pageNo = (String) getSession().get("stuPage");
			if (null == pageNo) // ���session�ỰҲû�еĻ�������Ϊ1
				pageNo = "1";
		}
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			if ("1".equals(find)) // ���ҵ���ѧ���������޸�
			{
				student = studentService.findOneByString(stuID);
				if (null != student)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(delete)) // ɾ��ѧ����Ϣ
			{
				studentService.deleteById(stuID);
			}
			if ("1".equals(update)) // ����ѧ����Ϣ
			{
				studentService.update(student);
			}
			if ("1".equals(insert)) // ��������ѧ����Ϣ
			{
				// ����������
				Workbook book = createWorkBook(new FileInputStream(excelFile));

				Sheet sheet = book.getSheetAt(0); // ȡ��һ����
				// ���湤��������
				Row firstRow = sheet.getRow(0);
				Iterator<Cell> iterator = firstRow.iterator();

				// ��������
				List<String> cellNames = new ArrayList<String>();
				while (iterator.hasNext()) {
					cellNames.add(iterator.next().getStringCellValue());
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row ros = sheet.getRow(i);
					Student g = new Student();
					// ����ѧ������
					g.setStuName(ros.getCell(0).getStringCellValue());
					// ����ѧ��ѧ��
					g.setStuID(ros.getCell(1).getStringCellValue());
					// �������֤����
					g.setStuIDCard(ros.getCell(2).getStringCellValue());
					// ����רҵ��Ϣ
					Major major = getMajor(ros.getCell(3).getStringCellValue());
					if (null == major)
						continue;
					g.setMajor(major);
					// �����Ա�(�У�0��Ů��1)
					if ("��".equals(ros.getCell(4).getStringCellValue()))
						g.setStuSex(0);
					else
						g.setStuSex(1);
					// ������Դ��
					Address address = getAddress(ros.getCell(5)
							.getStringCellValue());
					if (null == address)
						continue;
					g.setAddress(address);
					// ����������ò
					if ("�й���������Ա".equals(ros.getCell(6).getStringCellValue()))
						g.setStuPolitical(0);
					else if ("�й�Ԥ����Ա".equals(ros.getCell(6)
							.getStringCellValue()))
						g.setStuPolitical(1);
					else if ("������Ա".equals(ros.getCell(6).getStringCellValue()))
						g.setStuPolitical(2);
					else
						g.setStuPolitical(3);
					// ��������
					g.setStuEmail(ros.getCell(7).getStringCellValue());
					// ��ӵ���ҵ���б���
					studentService.saveStudent(g);
				}
			}

			totalsize = (long) 0; // �����ʼֵ
			totalsize = studentService.countTotal(); // ����ѧ��������
			pageSize = 20; // ÿҳ���ֻȡ20��ѧ����Ϣ
			// ����ѧ���б�
			list = studentService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("stuPage", pageNo); // ����ҳ����Ϣ��session�Ự��
			return "success";
		}

		return "fail";
	}

	// �ж��ļ�����
	public Workbook createWorkBook(InputStream is) throws IOException {
		if (excelFileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (excelFileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	// ��ȡרҵ��Ϣ
	@SuppressWarnings("static-access")
	private Major getMajor(String name) {
		Major m = null;
		// ��ȡ����רҵ��Ϣ
		List<Major> majorlist = new ArrayList<Major>();
		majorlist = majorService.findAll();
		// ����רҵ��Ϣ
		for (int i = 0; i < majorlist.size(); i++) {
			if (name.equals(majorlist.get(i).getMajorName())) {
				m = majorlist.get(i);
				break;
			}
		}
		if (null == m) // ���û�ҵ�רҵ��Ϣ���򴴽�֮
		{
			m = new Major(); // ʵ����רҵ��Ϣ����
			UuidUtil uuidUtil = new UuidUtil(); // ��ȡUUID
			m.setMajorID(uuidUtil.getCode()); // ��������
			m.setMajorName(name); // ����רҵ����
			majorService.create(m); // ����רҵ��Ϣ
		}

		return m;
	}

	// ��ȡ��Դ��
	@SuppressWarnings("static-access")
	private Address getAddress(String name) {
		// TODO Auto-generated method stub
		Address a = null;
		// ��ȡ������Դ����Ϣ
		List<Address> addresslist = new ArrayList<Address>();
		addresslist = addressService.findAll();
		// ������Դ����Ϣ
		for (int i = 0; i < addresslist.size(); i++) {
			if (name.equals(addresslist.get(i).getAddressName())) {
				a = addresslist.get(i);
				break;
			}
		}
		if (null == a) // ���û���ҵ���Դ����Ϣ���򴴽�֮
		{
			a = new Address(); // ʵ������Դ����Ϣ����
			UuidUtil uuidUtil = new UuidUtil(); // ��ȡUUID
			a.setAddressID(uuidUtil.getCode()); // ��������
			a.setAddressName(name); // ������Դ��
			addressService.create(a); // ������Դ��
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
