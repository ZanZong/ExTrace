package ts.daoImpl;
/**
 * @author Zongzan
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.aegis.type.java5.IgnoreProperty;
import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.PackageRoute;

public class PackageRouteDao extends BaseDao<PackageRoute, Integer>{
	public TransPackageContentDao transPackageContentDao;
	public TransPackageDao transPackageDao;
	
	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao transPackageDao) {
		this.transPackageDao = transPackageDao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao transPackageContentDao) {
		this.transPackageContentDao = transPackageContentDao;
	}

	public PackageRouteDao(){
		super(PackageRoute.class);
	}
	
	public void addPackageRoute(PackageRoute packageRoute){
		if(packageRoute == null){
			System.out.println("packageRoute is null\n\n");
			return;
		}
		super.save(packageRoute);
	}
	public List<PackageRoute> findPkgRoute(String ExpressSheetID){
		List<String> items = transPackageDao.getAllPackageId(ExpressSheetID);
		System.out.println(items);
		List<PackageRoute> pkgRoute = new ArrayList<PackageRoute>();
		for(String pkgid : items){
			pkgRoute.addAll(findBy("SN",true,Restrictions.sqlRestriction("PackageID = '"+ pkgid + "'")));
		}
		return pkgRoute;
	}
}
