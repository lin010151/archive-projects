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
import net.sf.json.JSONObject;
import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Post;
import edu.gdei.jobmessage.model.RecruitInfoView;
import edu.gdei.jobmessage.service.IClickCountService;
import edu.gdei.jobmessage.service.IPostService;
import edu.gdei.jobmessage.service.IRecruitViewService;
import edu.gdei.jobmessage.service.IVisitService;

/**
 * @author dragonzhu
 * 
 */
public class PostJson extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083414440722191112L;
	private String what; // 用户操作
	private String companyid; // 用人单位号
	private String postID; // 招聘岗位信息
	private String pageNo; // 页码
	private String userid; // 用户账号

	private int pageSize; // 每页数量

	@Resource(name = "postService")
	private IPostService postService; 				// 招聘岗位信息服务
	@Resource(name = "recruitviewService")
	private IRecruitViewService recruitviewService; // 招聘岗位信息服务
	@Resource(name = "clickcountService")
	private IClickCountService clickcountService; 	// 点击率服务
	@Resource(name = "visitService")
	private IVisitService visitService; 			// 访问历史记录服务

	private List<Post> list = new ArrayList<Post>();
	private List<RecruitInfoView> comlist = new ArrayList<RecruitInfoView>();
	private Post post;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public void doAction() throws IOException {
		// TODO Auto-generated method stub
		// 以下代码从JSON.java中拷过来的
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if ("all".equals(what)) // 查找所有招聘岗位信息
		{
			// 否则，查找所有的招聘岗位信息
			list = postService.findAll(); // 查找所有的地址信息
			// 将要被返回到客户端的对象
			JSONArray json = JSONArray.fromObject(list);
			out.write(json.toString());
		}
		if ("bycompany".equals(what))// 按用人单位查询
		{
			if (null != companyid) // 如果是按用人单位来查找的话
			{
				list = postService.findAllOfCompany(companyid);
				// 将要被返回到客户端的对象
				JSONArray json = JSONArray.fromObject(list);
				out.write(json.toString());
			}
		}
		if ("companyinfo".equals(what)) // 查找所有招聘公司信息
		{
			if (null != userid) {
				pageSize = 20;
				comlist = recruitviewService.getRecruitByPage(
						Integer.parseInt(pageNo), pageSize);
				// 将要被返回到客户端的对象
				JSONArray json = JSONArray.fromObject(comlist);
				out.write(json.toString());
			}
		}
		if ("single".equals(what)) // 只查找一个招聘岗位信息
		{
			if (null != postID) {
				// 查找出一个招聘岗位信息
				post = postService.findOneByString(postID);
				// 保存点击率
				if (visitService.saveVisit(userid, postID))	// 如果保存成功访问历史的话，则记录点击率
					clickcountService.saveClick(postID);
				JSONObject json = JSONObject.fromObject(post);
				out.write(json.toString());
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * @return the companyid
	 */
	public String getCompanyid() {
		return companyid;
	}

	/**
	 * @param companyid
	 *            the companyid to set
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	/**
	 * @return the what
	 */
	public String getWhat() {
		return what;
	}

	/**
	 * @param what
	 *            the what to set
	 */
	public void setWhat(String what) {
		this.what = what;
	}

	/**
	 * @return the postID
	 */
	public String getPostID() {
		return postID;
	}

	/**
	 * @param postID
	 *            the postID to set
	 */
	public void setPostID(String postID) {
		this.postID = postID;
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
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
