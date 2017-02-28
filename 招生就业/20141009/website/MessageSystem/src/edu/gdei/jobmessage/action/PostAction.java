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
	private int pageSize; 	// ÿҳ����
	private Long totalsize; // ������

	private String update; // �Ƿ������Ƹ��λ ��Ϣ
	private String delete; // �Ƿ�ɾ����Ƹ��λ��Ϣ
	private String insert; // �Ƿ������Ƹ��λ��Ϣ
	private String find;   // �Ƿ���Ҳ��޸�
	private String pageNo; // ҳ��
	private String postID; // ��Ƹ��

	private Manager manager; // ����Ա��
	private Post post;		// ��Ƹ��λ��Ϣ

	@Resource(name = "postService")
	private IPostService postService; // ��Ƹ��λ��Ϣ����

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
		manager = (Manager) getSession().get("manager"); // ��ȡsession�Ự�еĹ���Ա��Ϣ
		if (pageNo == null) // ���ҳ���Ϊ�գ����session�ж�ȡ
		{
			pageNo = (String) getSession().get("postPage");
			if (null == pageNo) // ���session�ỰҲû�еĻ�������Ϊ1
				pageNo = "1";
		}
		if (null != manager) // �˴��Թ���Ա�Ų�Ϊ�����ж��Ƿ��¼���Ժ�����ڴ�����ȫ���
		{
			if ("1".equals(find))					// ���ҵ�����Ƹ��λ�������޸�
			{
				post=postService.findOneByString(postID);
				if (null!=post)
					return "update";
				else
					return "fail";
			}
			if ("1".equals(delete))					// ɾ����Ƹ��λ��Ϣ
			{
				postService.deleteById(postID);
			}
			if ("1".equals(update))					// ������Ƹ��λ��Ϣ
			{
				post.setReleaseTime(new Date());		// ��ȡ��ǰʱ��
				postService.update(post);
			}
			if ("1".equals(insert))					// ������Ƹ��λ��Ϣ
			{
				UuidUtil uuidUtil=new UuidUtil();	// ��������ID
				post.setPostID(uuidUtil.getCode());	// ��������ID
				post.setReleaseTime(new Date());	// ��ȡ��ǰʱ��
				postService.create(post);			// ������Ƹ��λ��Ϣ
			}
			
			totalsize = (long) 0; // �����ʼֵ
			totalsize = postService.countTotal(); // ������Ƹ��λ������
			pageSize = 20; // ÿҳ���ֻȡ20����Ƹ��λ��Ϣ
			// ������Ƹ��λ�б�
			list = postService.findPage(Integer.parseInt(pageNo) - 1,
					pageSize);

			getSession().put("postPage", pageNo); // ����ҳ����Ϣ��session�Ự��
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
