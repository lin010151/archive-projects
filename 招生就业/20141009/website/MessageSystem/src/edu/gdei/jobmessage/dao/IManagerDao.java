package edu.gdei.jobmessage.dao;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.Manager;
import edu.gdei.jobmessage.model.ManagerLogin;

public interface IManagerDao extends IDao<Manager>{
	//�����е�DAO��ʵ�ֻ����Ĳ����ӿ�IDao
    //����ʵ��IDao��������֮�⣬�ض���DAOҪʵ���������������ڶ�Ӧ�Ľӿ�DAO�ж��巽����
    //�˴�ManagerDao�Ľӿ�IManagerDao����Ҫʵ����������
	public Manager findOneByLogin(final String id, final String psw);
	
	// ��ȡ��¼��Ϣ
	public ManagerLogin findLogin(final String id);
	
	// ���µ�¼��Ϣ
	public void updateLogin(ManagerLogin login);
	
	// �������Ա��Ϣ
	public void saveManager(Manager manager);
	
	// ɾ������Ա��Ϣ
	public void deleteManager(String id);
	
}
