/**
 * 
 */
package edu.gdei.jobmessage.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import edu.gdei.jobmessage.action.common.BaseAction;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.Post;
import edu.gdei.jobmessage.service.IPostService;
import edu.gdei.jobmessage.util.UuidUtil;

/**
 * @author dragonzhu
 * 
 */
public class PostAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize; 	// 每页数量
	private Long totalsize; // 总数量

	private String update; // 是否更新招聘岗位 信息
	private String delete; // 是否删除招聘岗位信息
	private String insert; // 是否插入招聘岗位信息
	private String find;   // 是否查找并修改
	private String pageNo; // 页码
	private String postID; // 招聘号

	private Manager manager; // 管理员号
	private Post post;		// 招聘岗位信息

	@Resource(name = "postService")
	private IPostService postService; // 招聘岗位信息服务

	private List<Post> list = new ArrayList<Post>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		manager = (Manager) getSession().get("manager"); // 获取session会话中的管理员信息
		if (pageNo == null) // 如果页面号为空，则从session中读取
		{
			pageNo = (String) getSession().get("postPage");
			if (null == pageNo) // 如果session会话也没有的话，则设为1
				pageNo = "1";
		}
		if (null != manager) // 此处以管理员号不为空来判断是否登录，以后可以在此作安全检查
		{
			if ("1".equals(find))					// 查找单个招聘岗位并进行修改
			{
				post=postService.findOneByString(postID);
				if (null!=post)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(delete))					// 删除招聘岗位信息
			{
				postService.deleteById(postID);
			}
			if ("1".equals(update))					// 更新招聘岗位信息
			{
				post.setReleaseTime(new Date());		// 获取当前时间
				postService.update(post);
			}
			if ("1".equals(insert))					// 插入招聘岗位信息
			{
				UuidUtil uuidUtil=new UuidUtil();	// 生成主键ID
				post.setPostID(uuidUtil.getCode());	// 设置主键ID
				post.setReleaseTime(new Date());	// 获取当前时间
				postService.create(post);			// 保存招聘岗位信息
			}
			
			totalsize = (long) 0; // 赋予初始值
			totalsize = postService.countTotal(); // 返回招聘岗位的数量
			pageSize = 20; // 每页最多只取20个招聘岗位信息
			// 返回招聘岗位列表
			list = postService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("postPage", pageNo); // 保存页码信息在session会话中
			return "success";
		}

		return "fail";
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalsize
	 */
	public Long getTotalsize() {
		return totalsize;
	}

	/**
	 * @param totalsize the totalsize to set
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
	 * @param update the update to set
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
	 * @param delete the delete to set
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
	 * @param insert the insert to set
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
	 * @param find the find to set
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
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the postID
	 */
	public String getPostID() {
		return postID;
	}

	/**
	 * @param postID the postID to set
	 */
	public void setPostID(String postID) {
		this.postID = postID;
	}

	/**
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}

	/**
	 * @return the list
	 */
	public List<Post> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Post> list) {
		this.list = list;
	}

}
