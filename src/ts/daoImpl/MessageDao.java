package ts.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.Message;
import ts.model.PackageRoute;
import ts.model.UserInfo;
import ts.smodel.LocXY;
import ts.util.MapCalculator;

public class MessageDao extends BaseDao<Message, Integer> {
	CustomerInfoDao customerInfoDao;
	UserInfoDao userInfoDao;
	PackageRouteDao packageRouteDao;
	UsersPackageDao usersPackageDao;
	
	public UsersPackageDao getUsersPackageDao() {
		return usersPackageDao;
	}

	public void setUsersPackageDao(UsersPackageDao usersPackageDao) {
		this.usersPackageDao = usersPackageDao;
	}

	public PackageRouteDao getPackageRouteDao() {
		return packageRouteDao;
	}

	public void setPackageRouteDao(PackageRouteDao packageRouteDao) {
		this.packageRouteDao = packageRouteDao;
	}

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
		System.out.println("accId:" + accId);
		List<Message> items = super.findBy("SN", true, Restrictions.sqlRestriction("accepter=" + accId + " and isrecv=0"));
		if(items == null)	return null;
		fillName(items);
		if(items.isEmpty())
			System.out.println("is empty");		
		return items;
	}
	
	public List<Message> getMsgBySender(int senderId){
		List<Message> items = super.findBy("SN", true, Restrictions.sqlRestriction("sender=" + senderId)); 
		if(items == null) return null;
		fillName(items);
		return items;
	}
	
	public int findSuitAccepter(int SN){
		List<UserInfo> users = userInfoDao.getAll();
		List<PackageRoute> pkgRoute = new ArrayList<PackageRoute>();
		for(UserInfo item : users){
			//System.out.println(item.getUID());
			pkgRoute.add(packageRouteDao.getLastPkgRouteByPID((item.getReceivePackageID())));
		}
		//System.out.println("pkgRoute.size():" + pkgRoute.size());
		PackageRoute nearest = this.getNearestPoint(pkgRoute, super.get(SN));
		System.out.println(nearest.toString()+"  it can find accepter//zong");
		//查找userpackage表得到userid
		if(nearest == null)
			return -1;
		else
			return usersPackageDao.getUidByPackage(nearest.getPkg().getID());
	}
	
	
	public PackageRoute getNearestPoint(List<PackageRoute> prList, Message m){
		int min = -1;
		double minDist = 0;
		int i = 0;
		Map<Integer, PackageRoute> locMap = new HashMap<>();
		for(PackageRoute pr : prList)
			if(pr != null)
				locMap.put(i++, pr);
		System.out.println("locMap_size:"+locMap.size());
		int len = locMap.size();
		double dist = 0;
		for(i = 0; i < len; i++){
			dist = MapCalculator.getDistanceFrom2Point(locMap.get(i).getX(), locMap.get(i).getY(),
					m.getX(), m.getY());
			System.out.println("dist"+i+" = "+dist);
			if(dist > minDist){
				minDist = dist;
				min = i;
			}
		}
		if(min == -1)
			return null;
		return locMap.get(min);
	}
	public void fillName(List<Message> msgs){
		for(Message item : msgs){
			/*System.out.println(item.toString());*/
			item.setSenderName(customerInfoDao.get(item.getSender()).getName());
			item.setAccepterName(userInfoDao.get(item.getAccepter()).getName());
			item.setTel(customerInfoDao.get(item.getSender()).getTelCode());
		}
	}

}
