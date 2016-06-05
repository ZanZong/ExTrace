package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

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
public class TransPackageContentDao extends BaseDao<TransPackageContent,Integer> {
	private TransPackageDao transPackageDao;
	public TransPackageContentDao(){
		super(TransPackageContent.class);
	}
	
	public TransPackageContent get(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.sqlRestriction("ExpressID = '"+ expressId + "' and PackageID = '" + packageId +"'"));
		if(list.size() == 0)
			return null;
		/*System.out .println(list.get(0));*/
		return list.get(0);
	}
	public int getSn(String expressId, String packageId){
		TransPackageContent cn = get(expressId,packageId);
		if(cn == null){
			return 0;
		}
		//System.out.println(cn.getSN());
		return cn.getSN();
	}

	public void delete(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, Restrictions.sqlRestriction("ExpressID = '"+ expressId + "'and PackageID = '" + packageId +"' "));
		for(TransPackageContent pc : list)
			super.remove(pc);
		return ;
	}
	public void addTransPackageContent(TransPackageContent transPackageContent){
		save(transPackageContent);
	}
	
	public List<TransPackageContent> getListPackageContent(String expressId){
		List<TransPackageContent> list=null;
		list=super.findBy("SN",true,Restrictions.sqlRestriction("ExpressID = '"+ expressId + "'"));
		//System.out.println(list);
		return list ;		
	}
	
	public List<String> getAllExpressSheetId(String packageId){
		List<String> li=new ArrayList<String>();
		List<TransPackageContent> list=new ArrayList<TransPackageContent>();
	//	System.out.println(packageId);
		list=findBy("SN",true,Restrictions.sqlRestriction("PackageID = '"+ packageId + "'"));
		for(TransPackageContent pc: list)
	       li.add(pc.getExpress().getID());
		//System.out.println(li);
		return li;		
	}
	public List<ExpressSheet> getAllExpressSheet(String packageId){
		List<TransPackageContent> list=new ArrayList<TransPackageContent>();
		List<ExpressSheet> elist=new ArrayList<ExpressSheet>();
		/*System.out.println(packageId);*/
		list=super.findBy("SN",true,Restrictions.sqlRestriction("PackageID = '"+ packageId + "'"));
		for(TransPackageContent pc: list)
	       elist.add(pc.getExpress());
		/*System.out.println(elist);*/
		return elist;		
	}
//shihu
	public List<ExpressSheet> getAllExpressSheetNow(String packageId){
		List<TransPackageContent> list = new ArrayList<TransPackageContent>();
		List<ExpressSheet> elList = new ArrayList<ExpressSheet>();
		list = super.findBy("SN", true, Restrictions.sqlRestriction("PackageID = '"+packageId+"'" +" and "+
					"Status='"+0+"'"));
		for(TransPackageContent pc : list){
			elList.add(pc.getExpress());
		}
		return elList;
	}
	
	public List<ExpressSheet> getAllExpressSheetOld(String packageid){
		List<TransPackageContent> list = new ArrayList<TransPackageContent>();
		List<ExpressSheet> elList = new ArrayList<ExpressSheet>();
		list = super.findBy("SN", true, Restrictions.sqlRestriction("PackageID = '"+packageid+"'" +" and "+
					"Status='"+1+"'"));
		for(TransPackageContent pc : list){
			elList.add(pc.getExpress());
		}
		return elList;
	}
	public TransPackageContent getExistPackage(String expresssid){
		List<TransPackageContent> list = findBy("SN", true, Restrictions.sqlRestriction("expressid='" + expresssid + "' and status=0"));
		if(list.size() > 0)
			return list.get(0);
		else return null;
	}
	
}
