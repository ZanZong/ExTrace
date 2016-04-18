package ts.daoImpl;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	public boolean checkUserByID(Integer uID, String psw){
		UserInfo userInfo = get(uID);
	/*	System.out.println("name " + userInfo.getName()+"\npsw " + userInfo.getPWD()+"zong\n");*/
		return userInfo.getPWD().equals(psw);
	}
	public void addUser(UserInfo userInfo){
		save(userInfo);
	}
	
}
