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
import edu.gdei.jobmessage.model.Major;
import edu.gdei.jobmessage.service.IMajorService;

/**
 * @author dragonzhu
 *
 */
public class MajorJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083414440722191112L;
	@Resource(name = "majorService")
	private IMajorService majorService;		// 地址信息服务
	
	private List<Major> list=new ArrayList<Major>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		
		list=majorService.findAll();		// 查找所有的地址信息
		// 以下代码从JSON.java中拷过来的
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 将要被返回到客户端的对象
		JSONArray json = JSONArray.fromObject(list);
		out.write(json.toString());
		out.flush();
		out.close();
	}

}
