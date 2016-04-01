package ts.daoImpl;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;

public class TransPackageDao extends BaseDao<TransPackage,String> {
	public TransPackageDao(){
		super(TransPackage.class);
	}
}
