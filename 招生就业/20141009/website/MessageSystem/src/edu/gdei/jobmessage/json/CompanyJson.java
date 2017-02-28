/**
 * 
 */
package edu.gdei.jobmessage.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Company;
import edu.gdei.jobmessage.service.ICompanyService;

/**
 * @author dragonzhu
 *
 */
public class CompanyJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083414440722191112L;
	@Resource(name = "companyService")
	private ICompanyService companyService;		// ��ַ��Ϣ����
	
	private List<Company> list=new ArrayList<Company>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		
		list=companyService.findAll();		// �������еĵ�ַ��Ϣ
		// ���´����JSON.java�п�������
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// ��Ҫ�����ص��ͻ��˵Ķ���
		JSONArray json = JSONArray.fromObject(list);
		out.write(json.toString());
		out.flush();
		out.close();
	}

}
