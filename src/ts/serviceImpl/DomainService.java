package ts.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;

import com.google.gson.Gson;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

import BaiduMap.BaiduApiUtils;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.daoImpl.UsersPackageDao;
import ts.model.ExpressSheet;
import ts.model.Message;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.model.UsersPackage;
import ts.serviceInterface.IDomainService;
import ts.smodel.History;
import ts.smodel.LocXY;
import ts.smodel.NamePair;
import ts.smodel.PackageNamePair;
import ts.smodel.WebHistory;
import ts.util.ImgUtil;
import ts.util.PostSplite;

public class DomainService implements IDomainService {
	
	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private PackageRouteDao packageRouteDao;
	private TransNodeDao transNodeDao;
	private UsersPackageDao usersPackageDao;
	/**
	 * @return the usersPackageDao
	 */
	public UsersPackageDao getUsersPackageDao() {
		return usersPackageDao;
	}

	/**
	 * @param usersPackageDao the usersPackageDao to set
	 */
	public void setUsersPackageDao(UsersPackageDao usersPackageDao) {
		this.usersPackageDao = usersPackageDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tm = new Date();
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		System.out.println(tm.toLocaleString());
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
			System.out.println("MoveExpressIntoPackage");
			System.out.println(nes.getAccepteTime().toLocaleString());
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
		TransPackage stp =new TransPackage();
		stp.setCreateTime(es.getCreateTime());
		stp.setID(es.getID());
		stp.setSourceNode(es.getSourceNode());
		stp.setTargetNode(es.getTargetNode());
		stp.setStatus(es.getStatus());
		System.out.println(stp.toString());
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
	public Response unpackTransPackage(String packageId) {
		// TODO Auto-generated method stub
		try{
			TransPackage tp=new TransPackage();
			transPackageDao.unpackTransPackage(packageId);
			tp=transPackageDao.get(packageId);
			TransPackage stp = new TransPackage();
			stp.setCreateTime(tp.getCreateTime());
			stp.setID(tp.getID());
			stp.setSourceNode(tp.getSourceNode());
			stp.setTargetNode(tp.getTargetNode());
			stp.setStatus(tp.getStatus());
			return Response.ok(stp).header("EntityClass", "O_TransPackage").build(); 
		}catch(Exception e){
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
		//System.out.println(locItems.get(0).toString());
		return locItems;
	}

	@Override
	public String getString(LocXY local) {
		// TODO Auto-generated method stub
		System.out.println(local.toString());
		return	 "haha";
	}

	@Override
	public String fun() {
		// TODO Auto-generated method stub
		
		retur;
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
	public List<History> getTransHistroy(String expressSheetId) {
		// TODO Auto-generated method stub
		System.out.println(expressSheetId);
		List<History> history = new ArrayList<History>();
		List<TransHistory> thList;
		List<TransPackage> pkgs = transPackageDao.getAllPackage(expressSheetId);
			for(TransPackage item : pkgs){
				thList = transHistoryDao.getPackageHistory(item.getID());
				for(TransHistory th : thList){
					History m = new History();
					m.setPackageID(th.getPkg().getID());
					m.setNameFrom(userInfoDao.get(th.getUIDFrom()).getName());
					m.setNameTo(userInfoDao.get(th.getUIDTo()).getName());
					m.setX(th.getX());
					m.setY(th.getY());
					m.setTime(th.getActTime());
					history.add(m);
				}
		}
		System.out.println("history:" + history.size());;
		return history;
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
	
	@Override
	public List<ExpressSheet> deliveExpress(List<String> list, String PackId) {
		// TODO Auto-generated method stub
       // System.out.println(list.get(0));
        System.out.println(PackId);
		UserInfo ui= new UserInfo();
		ui=userInfoDao.getUserByDPid(PackId);
		for(String expressId:list)
		{
			TransPackageContent tpc=new TransPackageContent();
			ExpressSheet es=null;
	    	es=expressSheetDao.get(expressId);	    	
	    	es.setDeliver(ui.getName());
	    	Date date=new Date();
	    	es.setDeliveTime(date);
	    	es.setStatus(4);
	    	System.out.println(es);
	    	tpc.setExpress(es);
			tpc.setPkg(transPackageDao.get(PackId));
			tpc.setStatus(5);
			System.out.println(tpc);
			transPackageContentDao.addTransPackageContent(tpc);
		}
		System.out.println(transPackageContentDao.getAllExpressSheet(PackId));
		return transPackageContentDao.getAllExpressSheet(PackId);
	}

	@Override
	public ExpressSheet signExpress(String expressId) {
		// TODO Auto-generated method stub
		ExpressSheet es=new ExpressSheet();
		es=expressSheetDao.get(expressId);
		Date date=new Date();
		es.setAccepter("xingjiali");
		es.setAccepteTime(date);
		//System.out.println(es);
		expressSheetDao.addExpressSheet(es);
		return es;
	}

	@Override
	public Response unpackaTransPackage(String packageId) {
		// TODO Auto-generated method stub
		try{
			TransPackage tp=new TransPackage();
			transPackageDao.unpackTransPackage(packageId);
			tp=transPackageDao.get(packageId);
			return Response.ok(tp).header("EntityClass", "TransPackage").build(); 
		}catch(Exception e){
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public List<ExpressSheet> getExpressListInPackage2(String packageId) {
		// TODO Auto-generated method stub
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = expressSheetDao.getListInPackage2(packageId);
		return list;
	}

	@Override
	public Response getTransNamePair(String a,String b) {
		NamePair name = new NamePair();
		name.setA(transNodeDao.getRegionString(a));
		name.setB(transNodeDao.getRegionString(b));
		return Response.ok("shihu").entity(name).header("EntityClass", "TranNode_name").build(); 
	}

	@Override
	public List<WebHistory> getWebHistory(String expressSheetId) {
		// TODO Auto-generated method stub
		List<WebHistory> whs = new ArrayList<WebHistory>();
		List<TransHistory> thList;
		List<TransPackage> pkgs = transPackageDao.getAllPackage(expressSheetId);
			for(TransPackage item : pkgs){
				thList = transHistoryDao.getPackageHistory(item.getID());
				for(TransHistory th : thList){
					WebHistory m = new WebHistory();
					m.setPackageID(th.getPkg().getID());
					m.setNameFrom(userInfoDao.get(th.getUIDFrom()).getName());
					m.setNameTo(userInfoDao.get(th.getUIDTo()).getName());
					m.setAddress(BaiduApiUtils.getFormatted_address(th.getY(), th.getX()));
					/*m.setX(th.getX());
					m.setY(th.getY());*/
					m.setTime(th.getActTime());
					whs.add(m);
				}
		}
		return whs;
	}

	@Override
	public String savePic(String pic) {
		// TODO Auto-generated method stub
		String id = null;
		String aa = null; 
		String bb = null; 
		byte[] src = null;
		try {
			aa = pic.substring(0,pic.indexOf('&'));
			bb = pic.substring(pic.indexOf('&'),pic.length());
			id = aa.substring(aa.indexOf('=')+1, aa.length());
			src = ImgUtil.hex2byte(bb.substring(bb.indexOf('=')+1, bb.length()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(pic);
		String url = "C:\\Extrace\\userdata\\";
		File a = new File(url + id + ".jpg");
		
		try {
			FileOutputStream out = new FileOutputStream(a);
			out.write(src);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	public List<ExpressSheet> getExpressInPackage(String packageId){
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = expressSheetDao.getListInPackage(packageId);
		return list;
	}

	@Override
	public List<TransPackage> getTransPackageBySource(String source) {
		// TODO Auto-generated method stub
		List<TransPackage> li= new ArrayList<TransPackage>();
		TransNode tn=new TransNode();
		tn=transNodeDao.getTransNodeByName(source);
		li=transPackageDao.getTransPackageBySource(tn.getID());
		return li;
	}

	@Override
	public List<PackageNamePair> getTransPackageById(String id) {
		// TODO Auto-generated method stub
		List<PackageNamePair> lp=new ArrayList<PackageNamePair>();
		PackageNamePair p=new PackageNamePair();
		TransPackage tp =transPackageDao.get(id);
		System.out.println(tp);
		int uid=usersPackageDao.getUidByPackage(id);
		String uname =userInfoDao.getUserByUid(uid).getName();
		p.setPackageId(id);
		p.setSourceNode(tp.getSourceNode());
		p.setSourceName(transNodeDao.getTransNodeByNode(tp.getSourceNode()).getNodeName());
        p.setTargetNode(tp.getTargetNode());
        p.setTargetName(transNodeDao.getTransNodeByNode(tp.getTargetNode()).getNodeName());
        p.setSourceRegionCode(transNodeDao.getTransNodeByNode(tp.getSourceNode()).getRegionCode());
        p.setTargetRegionCode(transNodeDao.getTransNodeByNode(tp.getTargetNode()).getRegionCode());
        p.setUserId(uid);
        p.setUserName(uname);
        p.setPkgType(tp.getStatus());
        if(p.getPkgType()==3){
        	p.setType("转运包裹");
        }else if(p.getPkgType()==4){
        	p.setType("揽收包裹");
        }else {
        	p.setType("派送包裹");
        }
        System.out.println(p.getPackageId());
        System.out.println(p.getType());
		//System.out.println(tp);
        lp.add(p);
		return lp;
	}

	@Override
	public List<PackageNamePair> getTransPackagesBySource(String source) {
		// TODO Auto-generated method stub
		List<PackageNamePair> pl=new ArrayList<PackageNamePair>();
		List<TransPackage> li=transPackageDao.getTransPackageBySource(source);
		System.out.println(li);
		for(TransPackage tp: li )
		{
			PackageNamePair p=new PackageNamePair();
			int uid=usersPackageDao.getUidByPackage(tp.getID());
			String uname =userInfoDao.getUserByUid(uid).getName();
			p.setPackageId(tp.getID());
			p.setSourceNode(tp.getSourceNode());
			p.setSourceName(transNodeDao.getTransNodeByNode(tp.getSourceNode()).getNodeName());
	        p.setTargetNode(tp.getTargetNode());
	        p.setTargetName(transNodeDao.getTransNodeByNode(tp.getTargetNode()).getNodeName());
	        p.setUserId(uid);
	        p.setUserName(uname);
	        p.setPkgType(tp.getStatus());
	        if(p.getPkgType()==3){
	        	p.setType("转运包裹");
	        }else if(p.getPkgType()==4){
	        	p.setType("揽收包裹");
	        }else {
	        	p.setType("派送包裹");
	        }
	        //System.out.println(p.getPackageId());
	        System.out.println(p);
			System.out.println(tp);
	        pl.add(p);
		}
		return pl;
	}

	@Override
	public List<PackageNamePair> getTransPackagesByDestination(String destination) {
		// TODO Auto-generated method stub
		System.out.println("getdest"+destination);
		List<PackageNamePair> pl=new ArrayList<PackageNamePair>();
		List<TransPackage> li=transPackageDao.getTransPackageByDestination(destination);
		System.out.println("0000");
		System.out.println(li);
		for(TransPackage tp: li )
		{
			PackageNamePair p=new PackageNamePair();
			int uid=usersPackageDao.getUidByPackage(tp.getID());
			String uname =userInfoDao.getUserByUid(uid).getName();
			p.setPackageId(tp.getID());
			p.setSourceNode(tp.getSourceNode());
			p.setSourceName(transNodeDao.getTransNodeByNode(tp.getSourceNode()).getNodeName());
	        p.setTargetNode(tp.getTargetNode());
	        p.setTargetName(transNodeDao.getTransNodeByNode(tp.getTargetNode()).getNodeName());
	        p.setUserId(uid);
	        p.setUserName(uname);
	        p.setPkgType(tp.getStatus());
	        if(p.getPkgType()==3){
	        	p.setType("转运包裹");
	        }else if(p.getPkgType()==4){
	        	p.setType("揽收包裹");
	        }else {
	        	p.setType("派送包裹");
	        }
	        //System.out.println(p.getPackageId());
	        System.out.println(p);
			System.out.println(tp);
	        pl.add(p);
		}
		return pl;
	}

	@Override
	public String saveTransPackages(String obj) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("12313");
			System.out.println(obj);
			System.out.println("12312");
			java.util.Map<String, String> uri= PostSplite.postchange(obj);
			System.out.println("nicneincei");
			//分配包裹
			int uid=Integer.parseInt(uri.get("name"));
			UserInfo ui=userInfoDao.getUserByUid(uid);
			ui.setReceivePackageID(uri.get("pkgid1"));
			ui.setTransPackageID(uri.get("pkgid2"));
			ui.setDelivePackageID(uri.get("pkgid3"));
			ui.setURull(1);
			userInfoDao.update(ui);
			
			//添加包裹
			TransPackage tp1=new TransPackage();
			tp1.setID(uri.get("pkgid1"));
			tp1.setSourceNode(uri.get("s_node1"));
			tp1.setTargetNode(uri.get("d_node1"));
			tp1.setCreateTime(new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss").parse(uri.get("time1")));
			String s1=uri.get("pkgtype1");
			if(s1.equals("揽收包裹") ){
				tp1.setStatus(3);
			}else if(s1.equals("转运包裹")){
				tp1.setStatus(4);
			}else {
				tp1.setStatus(5);
			}
			System.out.println(uri.get("time1"));
			System.out.println(uri.get("pkgtype1"));
			System.out.println(tp1);
			transPackageDao.save(tp1);
			
			//添加userpackage
			UsersPackage up1=new UsersPackage();
			up1.setPkg(tp1);
			up1.setUserU(ui);
			usersPackageDao.addUsersPackage(up1);
			
			//添加包裹
			TransPackage tp2=new TransPackage();
			tp2.setID(uri.get("pkgid2"));
			tp2.setSourceNode(uri.get("s_node2"));
			tp2.setTargetNode(uri.get("d_node2"));
			tp2.setCreateTime(new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss").parse(uri.get("time2")));
			String s2=uri.get("pkgtype2");
			if(s2.equals("揽收包裹")){
				tp2.setStatus(3);
			}else if(s2.equals("转运包裹")){
				tp2.setStatus(4);
			}else {
				tp2.setStatus(5);
			}
			System.out.println(uri.get("time2"));
			System.out.println(uri.get("pkgtype2"));
			System.out.println(tp2);
			transPackageDao.save(tp2);
			
			//添加userpackage
			UsersPackage up2=new UsersPackage();
			up2.setPkg(tp2);
			up2.setUserU(ui);
			usersPackageDao.addUsersPackage(up2);
			
			//添加包裹
			TransPackage tp3=new TransPackage();
			tp3.setID(uri.get("pkgid3"));
			tp3.setSourceNode(uri.get("s_node3"));
			tp3.setTargetNode(uri.get("d_node3"));
			tp3.setCreateTime(new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss").parse(uri.get("time3")));
			String s3=uri.get("pkgtype3");
			if(s3.equals("揽收包裹")){
				tp3.setStatus(3);
			}else if(s3.equals("转运包裹")){
				tp3.setStatus(4);
			}else {
				tp3.setStatus(5);
			}
			System.out.println(uri.get("time3"));
			System.out.println(uri.get("pkgtype3"));
			System.out.println(tp3);
			transPackageDao.save(tp3);
			
			//添加userpackage
			UsersPackage up3=new UsersPackage();
			up3.setPkg(tp3);
			up3.setUserU(ui);
			usersPackageDao.addUsersPackage(up3);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "成功添加包裹并分配！"; 
		
	}

	@Override
	public String updateTransPackages(String obj) {
		// TODO Auto-generated method stub
		java.util.Map<String, String> uri= PostSplite.postchange(obj);
		String id=uri.get("pkgid");
		TransPackage tp=transPackageDao.get(id);
		tp.setSourceNode(uri.get("s_node"));
		tp.setTargetNode(uri.get("d_node"));
		transPackageDao.update(tp);
		return "修改成功！";
	}

	@Override
	public List<PackageNamePair> getTransPackagesBySD(String source, String destination) {
		// TODO Auto-generated method stub
		List<PackageNamePair> pl=new ArrayList<PackageNamePair>();
		List<TransPackage> li=transPackageDao.getTransPackageBySD(source,destination);
		System.out.println("0000");
		System.out.println(li);
		for(TransPackage tp: li )
		{
			PackageNamePair p=new PackageNamePair();
			int uid=usersPackageDao.getUidByPackage(tp.getID());
			String uname =userInfoDao.getUserByUid(uid).getName();
			p.setPackageId(tp.getID());
			p.setSourceNode(tp.getSourceNode());
			p.setSourceName(transNodeDao.getTransNodeByNode(tp.getSourceNode()).getNodeName());
	        p.setTargetNode(tp.getTargetNode());
	        p.setTargetName(transNodeDao.getTransNodeByNode(tp.getTargetNode()).getNodeName());
	        p.setUserId(uid);
	        p.setUserName(uname);
	        p.setPkgType(tp.getStatus());
	        if(p.getPkgType()==3){
	        	p.setType("转运包裹");
	        }else if(p.getPkgType()==4){
	        	p.setType("揽收包裹");
	        }else {
	        	p.setType("派送包裹");
	        }
	        //System.out.println(p.getPackageId());
	        System.out.println(p);
			System.out.println(tp);
	        pl.add(p);
		}
		return pl;
	}

	@Override
	public String saveTransHistory(History history) {
		// TODO Auto-generated method stub
		TransHistory th = new TransHistory();
		th.setActTime(getCurrentDate());
		th.setUIDFrom(history.getIdFrom());
		th.setUIDTo(history.getIdTo());
		th.setX((float)history.getX());
		th.setY((float)history.getY());
		try {
			th.setPkg(transPackageDao.get(history.getPackageID()));
			transHistoryDao.save(th);
			return "succ";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unsucc";
		}
		
	}
}
