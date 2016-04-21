package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.PackageRoute;

public class PackageRouteDao extends BaseDao<PackageRoute, Integer>{
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
	
	public void addPackageRoute(PackageRoute packageRoute){
		if(packageRoute == null){
			System.out.println("packageRoute is null\n\n");
			return;
		}
		super.save(packageRoute);
	}
	public List<PackageRoute> findPkgRoute(String ExpressSheetID){
		
		return null;
	}
}
