package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

//import org.apache.jasper.tagplugins.jstl.core.Remove;
import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;
import ts.model.UserInfo;
/**
 * 
 * @author xingjiali
 *
 */

public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	public boolean checkUserByID(Integer uID, String psw){
		UserInfo userInfo = get(uID);
		System.out.println("name " + userInfo.getName()+"\npsw " + userInfo.getPWD()+"zong\n");
		/*System.out.println(userInfo.getPWD().equals(psw));*/
		return userInfo.getPWD().equals(psw);
	}
	public void addUser(UserInfo userInfo){
		save(userInfo);
	}
	public void updateUser(UserInfo userInfo){
		update(userInfo);
	}
	public void deleteUser(UserInfo userInfo){
		remove(userInfo);
	}
	public void deleteUserById(int uid){
		removeById(uid);
	}
	public String getReceivePackageId(int uid){
		UserInfo ui = get(uid);
		System.out.println(ui.getReceivePackageID());
		return ui.getReceivePackageID();		
	}
	public String getDelivePackageId(int uid){
		UserInfo ui = get(uid);
		System.out.println(ui.getDelivePackageID());
		return ui.getDelivePackageID();	
	}
	public String getTransPackageId(int uid){
		UserInfo ui = get(uid);
		System.out.println(ui.getTransPackageID());
		return ui.getTransPackageID();
	}
	public String getDptId(int uid){
		UserInfo ui = get(uid);
		System.out.println(ui.getDptID());
		return ui.getDptID();
	}
	public String getTel(int uid){
		UserInfo ui = get(uid);
		System.out.println(ui.getTelCode());
		return ui.getTelCode();
	}
	public UserInfo getUserByDPid(String dpId)
	{
		List<UserInfo> ui=new ArrayList<UserInfo>();
		ui=super.findBy("UID", true, Restrictions.sqlRestriction("DelivePackageID = '"+ dpId + "'"));
		System.out.println(ui.toString());
		return ui.get(0);
	}
	public UserInfo getUserByUid(int uid)
	{
		List<UserInfo> ui=new ArrayList<UserInfo>();
		ui=super.findBy("UID", true, Restrictions.sqlRestriction("UID = '"+ uid + "'"));
		//System.out.println(ui.toString());
		//System.out.print(ui.get(0));
		return ui.get(0);
		
	}
	public UserInfo getUserByUname(String uname)
	{
		List<UserInfo> ui=new ArrayList<UserInfo>();
		ui=super.findBy("UID", true, Restrictions.sqlRestriction("Name = '"+ uname + "'"));
		//System.out.println(ui.toString());
		//System.out.print(ui.get(0));
		return ui.get(0);
		
	}
	public List<UserInfo> getUsersList() {
		// TODO Auto-generated method stub
		List<UserInfo> listui=new ArrayList<UserInfo>();
		listui=findBy("URull", 0, "UID", true);
        System.out.print(listui);
		return listui;
	}
}
