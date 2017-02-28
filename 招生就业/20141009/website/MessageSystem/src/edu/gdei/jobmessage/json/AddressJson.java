/**
 * 
 */
package edu.gdei.jobmessage.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Address;
import edu.gdei.jobmessage.service.IAddressService;

/**
 * @author dragonzhu
 *
 */
public class AddressJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083414440722191112L;
	
	@Resource(name = "addressService")
	private IAddressService addressService;		// 地址信息服务
	
	private List<Address> list=new ArrayList<Address>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		
		list=addressService.findAll();		// 查找所有的地址信息
		// 以下代码从JSON.java中拷过来的
		getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out;
		out = getResponse().getWriter();
		// 将要被返回到客户端的对象
		JSONArray json = JSONArray.fromObject(list);
		out.write(json.toString());
		out.flush();
		out.close();
	}

}
