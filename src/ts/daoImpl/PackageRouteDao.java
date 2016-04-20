package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.PackageRoute;

public class PackageRouteDao extends BaseDao<PackageRoute, String>{
	public TransPackageContentDao transPackageContentDao;
	
	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao transPackageContentDao) {
		this.transPackageContentDao = transPackageContentDao;
	}

	public PackageRouteDao(){
		super(PackageRoute.class);
	}
	
	public void addPackageRoute(PackageRoute pkgRoute){
		save(pkgRoute);
	}
	public List<PackageRoute> findPkgRoute(String ExpressSheetID){
		
		return null;
	}
}
