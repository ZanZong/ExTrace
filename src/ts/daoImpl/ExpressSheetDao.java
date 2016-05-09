package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.CustomerInfo;
import ts.model.ExpressSheet;

public class ExpressSheetDao extends BaseDao<ExpressSheet,String> {
	private RegionDao regionDao;
	public RegionDao getRegionDao() {
		return regionDao;
	}
	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public ExpressSheetDao(){
		super(ExpressSheet.class);
	}

	//重写的get方法,将客户的区域字符串加入
	public ExpressSheet get(String id) {
		ExpressSheet es = super.get(id);
		CustomerInfo ci = es.getRecever();
		if(ci!= null)
			ci.setRegionString(regionDao.getRegionNameByID(ci.getRegionCode()));	//获取区域的名字字符串
		CustomerInfo cs = es.getSender();
		if(cs != null) 
			cs.setRegionString(regionDao.getRegionNameByID(cs.getRegionCode()));	//获取区域的名字字符串
		return es;
	}

	//获得指定包裹ID的所有快件列表
	/*Restrictions类的静态方法返回值作为参数,可以代替SQl语句中的“>、=、<、!”等
	 * */
	public List<ExpressSheet> getListInPackage(String pkg_id) {	
		String sql = "{alias}.ID in (select ExpressID from TransPackageContent where Status = 0 and Type = 0 and PackageID = '"+pkg_id+"')";
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));		
		return list;
	}
	
	public List<ExpressSheet> getPreFillListInPackage(String pkg_id) {	
		String sql = "{alias}.ID in (select ExpressID from TransPackageContent where Status = 0 and Type = 1 and PackageID = '"+pkg_id+"')";
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));		
		return list;
	}
	
	public void addExpressSheet(ExpressSheet sh){
		save(sh);
	}
}
