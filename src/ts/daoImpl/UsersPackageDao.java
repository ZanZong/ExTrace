package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.UsersPackage;

public class UsersPackageDao extends BaseDao<UsersPackage, Integer> {
	public UsersPackageDao(){
		super(UsersPackage.class);
	}
	public int getUidByPackage(String PID){
		List<UsersPackage> ups = findBy("SN", true, Restrictions.sqlRestriction("PackageID='"+ PID +"'"));
		System.out.println("getUIDByPackage:");
		return ups.size() > 0 ? ups.get(0).getUserU().getUID() : 0; 
	}
	public void addUsersPackage(UsersPackage up){
		save(up);
	}
}
