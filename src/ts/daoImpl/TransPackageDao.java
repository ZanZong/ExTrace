package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;
/**
 * 
 * @author Zongzan
 * @version 1.0
 */
public class TransPackageDao extends BaseDao<TransPackage,String> {
	private RegionDao regionDao;
	public TransPackageDao(){
		super(TransPackage.class);
	}
	public RegionDao getRegionDao() {
		return regionDao;
	}
	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}
	public String getDestination(String id){
		TransPackage tspack = get(id);
		String dest = regionDao.getRegionNameByID(tspack.getTargetNode());
		System.out.println(dest);
		return dest;
	}
	public void addTransPackage(TransPackage tp){
		save(tp);
	}
	public void changeStatus(TransPackage tp){
		update(tp);
	}
}
