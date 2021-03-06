package ts.serviceImpl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;

import com.google.gson.Gson;

import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.ExpressSheet;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.serviceInterface.IDomainService;
import ts.smodel.History;
import ts.smodel.LocXY;

public class DomainService implements IDomainService {
	
	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private PackageRouteDao packageRouteDao;
	
	public PackageRouteDao getPackageRouteDao() {
		return packageRouteDao;
	}

	public void setPackageRouteDao(PackageRouteDao packageRouteDao) {
		this.packageRouteDao = packageRouteDao;
	}

	private UserInfoDao userInfoDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public Date getCurrentDate() {
		//产生一个不带毫秒的时间,不然,SQL时间和JAVA时间格式不一致
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}

	@Override
	public List<ExpressSheet> getExpressList(String property,
			String restrictions, String value) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = expressSheetDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = expressSheetDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
	}
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}

	@Override
	public List<ExpressSheet> getExpressListInPackage(String packageId){
		List<ExpressSheet> list = null;
		list = expressSheetDao.getListInPackage(packageId);
		return list;		
	}

	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build(); 
	}

	@Override
	public Response newExpressSheet(String id, int uid) {
		ExpressSheet es = null;
		try{
			es = expressSheetDao.get(id);
		} catch (Exception e1) {}

		if(es != null){
//			if(es.getStatus() != 0)
//				return Response.ok(es).header("EntityClass", "L_ExpressSheet").build(); //已经存在,且不能更改
//			else
				return Response.ok("快件运单信息已经存在!\n无法创建!").header("EntityClass", "E_ExpressSheet").build(); //已经存在
		}
		try{
			String pkgId = userInfoDao.get(uid).getReceivePackageID();
			ExpressSheet nes = new ExpressSheet();
			nes.setID(id);
			nes.setType(0);
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
//			TransPackageContent pkg_add = new TransPackageContent();
//			pkg_add.setPkg(transPackageDao.get(pkgId));
//			pkg_add.setExpress(nes);
//			nes.getTransPackageContent().add(pkg_add);
			System.out.println(nes.toString());
			expressSheetDao.save(nes);
			//放到收件包裹中
			MoveExpressIntoPackage(nes.getID(),pkgId);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response saveExpressSheet(ExpressSheet obj) {
		try{
			//ExpressSheet nes = expressSheetDao.get(obj.getID());
			if(obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
				return Response.ok("快件运单已付运!无法保存更改!").header("EntityClass", "E_ExpressSheet").build(); 
			}
			expressSheetDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response ReceiveExpressSheetId(String id, int uid) {
		try{
			ExpressSheet nes = expressSheetDao.get(id);
			if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
				return Response.ok("快件运单状态错误!无法收件!").header("EntityClass", "E_ExpressSheet").build(); 
			}
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
			expressSheetDao.save(nes);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response DispatchExpressSheet(String id, int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean MoveExpressIntoPackage(String id, String targetPkgId) {
		TransPackage targetPkg = transPackageDao.get(targetPkgId);
		if((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)){	//包裹的状态快点定义,打开的包裹或者货篮才能操作==================================================================
			return false;
		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
		if((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)){
			return false;
		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		//需要加入事务机制
		MoveExpressFromPackage(id,sourcePkgId);
		MoveExpressIntoPackage(id,targetPkgId);
		return true;
	}

	@Override
	public Response DeliveryExpressSheetId(String id, int uid) {
		try{
			String pkgId = userInfoDao.get(uid).getDelivePackageID();
			ExpressSheet nes = expressSheetDao.get(id);
			if(nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT){
				return Response.ok("快件运单状态错误!无法交付").header("EntityClass", "E_ExpressSheet").build(); 
			}
			
			if(transPackageContentDao.getSn(id, pkgId) == 0){
				//临时的一个处理方式,断路了包裹的传递过程,自己的货篮倒腾一下
				MoveExpressBetweenPackage(id, userInfoDao.get(uid).getReceivePackageID(),pkgId);
				return Response.ok("快件运单状态错误!\n快件信息没在您的派件包裹中!").header("EntityClass", "E_ExpressSheet").build(); 
			}
				
			nes.setDeliver(String.valueOf(uid));
			nes.setDeliveTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
			expressSheetDao.save(nes);
			//从派件包裹中删除
			MoveExpressFromPackage(nes.getID(),pkgId);
			//快件没有历史记录,很难给出收件和交付的记录
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public List<TransPackage> getTransPackageList(String property,
			String restrictions, String value) {
		List<TransPackage> list = new ArrayList<TransPackage>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = transPackageDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = transPackageDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
	}

	@Override
	public Response getTransPackage(String id) {
		TransPackage es = transPackageDao.get(id);
		ts.smodel.TransPackage stp = new ts.smodel.TransPackage();
		stp.setCreateTime(es.getCreateTime());
		stp.setID(es.getID());
		stp.setSourceNode(es.getSourceNode());
		stp.setTargetNode(es.getTargetNode());
		stp.setStatus(es.getStatus());
		return Response.ok(stp).header("EntityClass", "TransPackage").build(); 
	}

	@Override
	public Response newTransPackage(String id, int uid) {
		try{
			TransPackage npk = new TransPackage();
			npk.setID(id);
			npk.setStatus(TransPackage.STATUS.RECEIVE);
			npk.setCreateTime(new Date());
			transPackageDao.save(npk);
			return Response.ok(npk).header("EntityClass", "TransPackage").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response saveTransPackage(TransPackage obj) {
		try{
			transPackageDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response saveRoutePos(String packageId, double x, double y) {
		// TODO Auto-generated method stub
		PackageRoute pr = new PackageRoute();
		try{
			pr.setPkg(transPackageDao.get(packageId));
			pr.setX((float)x);
			pr.setY((float)y);
			pr.setTm(new Date());
			packageRouteDao.save(pr);
			return Response.ok().header("EntityClass", "R_TransPackage").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public String getPostCode(String pro, String city, String town) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<LocXY> getPackageRoutePos(String ExpressSheetid, String time) {			//有问题
		// TODO Auto-generated method stub
		List<PackageRoute> prList = packageRouteDao.findPkgRoute(ExpressSheetid);
		List<LocXY> locItems = new ArrayList<LocXY>();
		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = df.format(time);
		java.util.Calendar c1=java.util.Calendar.getInstance();
		java.util.Calendar c2=java.util.Calendar.getInstance();
		try
		{
			c1.setTime(df.parse(time));
			for(PackageRoute pr : prList){
				c2.setTime(df.parse(pr.getTm().toString()));
				if(c1.compareTo(c2) < 0){  
					locItems.add(new LocXY(pr.getX(), pr.getY()));
				}
			}
		}catch(java.text.ParseException e){
			System.err.println("格式不正确");
		}
		return locItems;
	}
	@Override
	public List<LocXY> getPackageRoutePos(String ExpressSheetid) {
		// TODO Auto-generated method stub
		List<PackageRoute> routeItems = packageRouteDao.findPkgRoute(ExpressSheetid);
		System.out.println("routesize is:"+routeItems.size());
		List<LocXY> locItems = new ArrayList<LocXY>();
		for(PackageRoute pr : routeItems){
			locItems.add(new LocXY((pr.getX()), pr.getY()));
		}
		System.out.println(locItems.get(0).toString());
		return locItems;
	}

	@Override
	public String getString(LocXY local) {
		// TODO Auto-generated method stub
		System.out.println(local.toString());
		return	 "haha";
	}

	@Override
	public Response fun(String shihu) {
		// TODO Auto-generated method stub
		
		return Response.ok(shihu + "shoudaole").build();
	}

	@Override
	public Response putExpressIntoPkg(String ExpressSheetid, String packageId) {
		// TODO Auto-generated method stub
		try{
			TransPackageContent transPackageContent = new TransPackageContent();
			transPackageContent.setPkg(transPackageDao.get(packageId));
			transPackageContent.setStatus(0);
			transPackageContent.setExpress(expressSheetDao.get(ExpressSheetid));
			transPackageContentDao.save(transPackageContent);
			
			return Response.ok(transPackageContent).build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response unBoxingPackage(String packageId) {
		// TODO Auto-generated method stub
		try{
			TransPackage td = transPackageDao.get(packageId);
			td.setStatus(td.getStatus() + 1);
			transPackageDao.update(td);
			return Response.ok().build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public Response unBoxingExpressSheet(String expressSheetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getTransHistroy(String expressSheetId) {
		// TODO Auto-generated method stub
		List<TransHistory> history = new ArrayList<TransHistory>();
		try{
			List<TransPackage> pkgs = transPackageDao.getAllPackage(expressSheetId);
			for(TransPackage item : pkgs){
				history.addAll(transHistoryDao.getPackageHistory(item.getID()));
			}
			return Response.ok(history).build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	/**
	 * 	揽收后，由于uidFrom的值是没有的，我们设‘0’为这时的uidFrom
	 * 	派送时，没有uidTo,我们设‘1’为这时的uidTo
	 */
	@Override
	public Response saveTransHistory(History history, int status) {
		// TODO Auto-generated method stub
		TransHistory th = new TransHistory();
		th.setPkg(transPackageDao.get(history.getPackageID()));
		th.setUIDFrom(history.getUidFrom());
		th.setX(history.getX());
		th.setY(history.getY());
		if(status == TransPackage.STATUS.RECEIVE){
			th.setUIDFrom(0);
			th.setUIDTo(history.getUidTo());
		}
		else if(status == TransPackage.STATUS.DELIVERYPKG){
			th.setUIDFrom(history.getUidFrom());
			th.setUIDTo(0);
		}
		else{
			th.setUIDFrom(history.getUidFrom());
			th.setUIDTo(history.getUidTo());
		}
		try {
			transHistoryDao.save(th);
			return Response.ok("已更新历史").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public Response savePreFillList(ExpressSheet obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response FillList(ExpressSheet obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpressSheet> getPreFillListInPackage(String packageId) {
		// TODO Auto-generated method stub
		return expressSheetDao.getPreFillListInPackage(packageId);
	}
}
