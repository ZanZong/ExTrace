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
		return ups.size() > 0 ? ups.get(0).getUserU().getUID() : null; 
	}
}
