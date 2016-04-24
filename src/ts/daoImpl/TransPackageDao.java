package ts.daoImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
/**
 * 
 * @author xingjiali
 *
 */
public class TransPackageDao extends BaseDao<TransPackage,String> {
	private RegionDao regionDao;
	private TransPackageContentDao transPackageContentDao;
	private ExpressSheetDao expressSheetDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}
	public void setExpressSheetDao(ExpressSheetDao expressSheetDao) {
		this.expressSheetDao = expressSheetDao;
	}
	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}
	public void setTransPackageContentDao(TransPackageContentDao transPackageContentDao) {
		this.transPackageContentDao = transPackageContentDao;
	}
	
	public RegionDao getRegionDao() {
		return regionDao;
	}
	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}
	public TransPackageDao(){
		super(TransPackage.class);
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
	
	public List<TransPackage> sortTransPackage(List<TransPackage> transPackage,boolean isAsc){
		//System.out.println(transPackage);		
		Comparator<TransPackage> comparator = new Comparator<TransPackage>(){

			@Override
			public int compare(TransPackage tp1, TransPackage tp2) {
				// TODO Auto-generated method stub
				if(!tp1.getCreateTime().equals(tp2.getCreateTime())){
				      return tp1.getCreateTime().compareTo(tp2.getCreateTime());
				     }
				else
					return 1;
			}
			
		};
		Collections.sort(transPackage,comparator);
		/*System.out.println(transPackage);*/
		return transPackage;		
	}
	public List<TransPackage> getAllPackage(String expressId){		
		List<TransPackageContent> list1 = null;
		List<TransPackage> list2=new ArrayList<TransPackage>();
		list1=transPackageContentDao.getListPackageContent(expressId);
		//System.out.println(list1);
		for( TransPackageContent tp : list1)
			list2.add(tp.getPkg());
		//System.out.println(list2.get(0));
		list2=this.sortTransPackage(list2, true);
		//System.out.println(list2);		
		return list2;		
	}
	public List<String> getAllPackageId(String expressId){		
		List<String> li=new ArrayList<String>();
		List<TransPackage> list=null;
		list=this.getAllPackage(expressId);
		//System.out.println(list);
		for(TransPackage tp : list)
			li.add(tp.getID());
		//System.out.println(li.toString());
		return li;		
	}
	
	public void takeTransPackage(String transPackage1,String transPackage2){
		TransPackageContent tpc=new TransPackageContent();		
		List<String> list=new ArrayList<String>();
		list=transPackageContentDao.getAllExpressSheetId(transPackage1);
		//System.out.println(transPackage1);
	//System.out.println(list);
		for(String pc:list)
		{
			tpc.setExpress(expressSheetDao.get(pc));
			tpc.setPkg(this.get(transPackage2));
			tpc.setStatus(3);
			transPackageContentDao.addTransPackageContent(tpc);
			//System.out.println(tpc);
		}
	}
}
