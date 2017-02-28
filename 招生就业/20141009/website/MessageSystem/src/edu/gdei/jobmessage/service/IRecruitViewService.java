/**
 * 
 */
package edu.gdei.jobmessage.service;

import java.util.List;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.RecruitInfoView;

/**
 * @author dragonzhu
 *
 */
public interface IRecruitViewService extends IDao<RecruitInfoView> {
	public List<RecruitInfoView> getRecruitByPage(int page, int pageSize);
}
