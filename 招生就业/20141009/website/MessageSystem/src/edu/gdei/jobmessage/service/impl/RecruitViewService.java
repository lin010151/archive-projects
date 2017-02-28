/**
 * 
 */
package edu.gdei.jobmessage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.gdei.jobmessage.dao.IRecruitViewDao;
import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.RecruitInfoView;
import edu.gdei.jobmessage.service.IRecruitViewService;
import edu.gdei.jobmessage.service.common.AbstractService;

/**
 * @author dragonzhu
 * 
 */
@Service("recruitviewService")
public class RecruitViewService extends AbstractService<RecruitInfoView>
		implements IRecruitViewService {

	@Resource(name = "recruitDao")
	private IRecruitViewDao recruitDao;

	/**
	 * 
	 */
	public RecruitViewService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IDao<RecruitInfoView> getDao() {
		// TODO Auto-generated method stub
		return this.recruitDao;
	}

	@Override
	public List<RecruitInfoView> getRecruitByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return this.recruitDao.getRecruitByPage(page, pageSize);
	}

	/**
	 * @return the recruitDao
	 */
	public IRecruitViewDao getRecruitDao() {
		return recruitDao;
	}

	/**
	 * @param recruitDao the recruitDao to set
	 */
	public void setRecruitDao(IRecruitViewDao recruitDao) {
		this.recruitDao = recruitDao;
	}

}
