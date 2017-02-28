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
	private String what; // �û�����
	private String companyid; // ���˵�λ��
	private String postID; // ��Ƹ��λ��Ϣ
	private String pageNo; // ҳ��
	private String userid; // �û��˺�

	private int pageSize; // ÿҳ����

	@Resource(name = "postService")
	private IPostService postService; 				// ��Ƹ��λ��Ϣ����
	@Resource(name = "recruitviewService")
	private IRecruitViewService recruitviewService; // ��Ƹ��λ��Ϣ����
	@Resource(name = "clickcountService")
	private IClickCountService clickcountService; 	// ����ʷ���
	@Resource(name = "visitService")
	private IVisitService visitService; 			// ������ʷ��¼����

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
		// ���´����JSON.java�п�������
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if ("all".equals(what)) // ����������Ƹ��λ��Ϣ
		{
			// ���򣬲������е���Ƹ��λ��Ϣ
			list = postService.findAll(); // �������еĵ�ַ��Ϣ
			// ��Ҫ�����ص��ͻ��˵Ķ���
			JSONArray json = JSONArray.fromObject(list);
			out.write(json.toString());
		}
		if ("bycompany".equals(what))// �����˵�λ��ѯ
		{
			if (null != companyid) // ����ǰ����˵�λ�����ҵĻ�
			{
				list = postService.findAllOfCompany(companyid);
				// ��Ҫ�����ص��ͻ��˵Ķ���
				JSONArray json = JSONArray.fromObject(list);
				out.write(json.toString());
			}
		}
		if ("companyinfo".equals(what)) // ����������Ƹ��˾��Ϣ
		{
			if (null != userid) {
				pageSize = 20;
				comlist = recruitviewService.getRecruitByPage(
						Integer.parseInt(pageNo), pageSize);
				// ��Ҫ�����ص��ͻ��˵Ķ���
				JSONArray json = JSONArray.fromObject(comlist);
				out.write(json.toString());
			}
		}
		if ("single".equals(what)) // ֻ����һ����Ƹ��λ��Ϣ
		{
			if (null != postID) {
				// ���ҳ�һ����Ƹ��λ��Ϣ
				post = postService.findOneByString(postID);
				// ��������
				if (visitService.saveVisit(userid, postID))	// �������ɹ�������ʷ�Ļ������¼�����
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
