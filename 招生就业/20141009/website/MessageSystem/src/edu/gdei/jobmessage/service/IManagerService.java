package edu.gdei.jobmessage.service;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;

public interface IManagerService extends IDao<Manager> {
	
	// ͨ����¼�����û�
	Manager findOneByLogin(final String id, final String psw);
	
	// ��ȡ��¼��Ϣ
	public ManagerLogin findLogin(final String id);
	
	// ���µ�¼��Ϣ
	public void updateLogin(ManagerLogin login);	

	// �������Ա��Ϣ
	public void saveManager(Manager manager);
	
	// ɾ������Ա��Ϣ
	public void deleteManager(String id);
}