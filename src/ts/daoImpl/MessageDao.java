package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.Message;
import ts.model.PackageRoute;

public class MessageDao extends BaseDao<Message, Integer> {
	CustomerInfoDao customerInfoDao;
	UserInfoDao userInfoDao;
	
	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public MessageDao(){
		super(Message.class);
	}
	
	public List<Message> getMsgByAccepter(int accId) {
		List<Message> items = super.findBy("accepter", accId, "SN", true);
		fillName(items);
		return items;
	}
	
	public List<Message> getMsgBySender(int senderId){
		List<Message> items = super.findBy("SN", true, Restrictions.sqlRestriction("sender=" + senderId)); 
		fillName(items);
		return items;
	}
	
	public int findSuitAccepter(int SN){
		return 1;
	}
	public void fillName(List<Message> msgs){
		for(Message item : msgs){
			item.setSenderName(customerInfoDao.get(item.getSender()).getName());
			item.setAccepterName(userInfoDao.get(item.getAccepter()).getName());
		}
	}

}
