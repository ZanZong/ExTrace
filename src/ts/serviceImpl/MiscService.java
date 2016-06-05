package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.MessageDao;
import ts.daoImpl.RegionDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.UserInfoDao;
import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.Message;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.UserInfo;
import ts.serviceInterface.IMiscService;
import ts.util.JSONObjectUtils;
import ts.util.PostSplite;

public class MiscService implements IMiscService{
	//TransNodeCatalog nodes;	//自己做的缓存和重定向先不要了,用Hibernate缓存对付一下，以后加上去
	//RegionCatalog regions;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	private CustomerInfoDao customerInfoDao;
	private UserInfoDao userInfoDao;
	private MessageDao messageDao;
	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao dao) {
		this.transNodeDao = dao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao dao) {
		this.customerInfoDao = dao;
	}

	public MiscService(){
//		nodes = new TransNodeCatalog();
//		nodes.Load();
//		regions = new RegionCatalog();
//		regions.Load();
	}



	@Override
	public List<TransNode> getNodesList(String regionCode, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerInfo> getCustomerListByName(String name) {
//		List<CustomerInfo> listci = customerInfoDao.findByName(name);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByName(name);
	}

	@Override
	public List<CustomerInfo> getCustomerListByTelCode(String TelCode) {
//		List<CustomerInfo> listci = customerInfoDao.findByTelCode(TelCode);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByTelCode(TelCode);
	}

	@Override
	public Response getCustomerInfo(String id) {
		CustomerInfo cstm = customerInfoDao.get(Integer.parseInt(id));
//		try{
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//这部分功能放到DAO里去了
//		}catch(Exception e){}
		return Response.ok(cstm).header("EntityClass", "CustomerInfo").build(); 
	}
	
	@Override
	public Response deleteCustomerInfo(int id) {
		customerInfoDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_CustomerInfo").build(); 
	}

	@Override
	public Response saveCustomerInfo(CustomerInfo obj) {
		try{
			customerInfoDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_CustomerInfo").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	@Override
	public List<CodeNamePair> getProvinceList() {		
		List<Region> listrg = regionDao.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getCityList(String prv) {
		List<Region> listrg = regionDao.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getTownList(String city) {
		List<Region> listrg = regionDao.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}

	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String checkLoginUser(int uid, String pwd) {
		// TODO Auto-generated method stub
		System.out.println("hahhahahahaha");
		System.out.println(uid+"&"+pwd);
		boolean flag = userInfoDao.checkUserByID(uid, pwd);
		System.out.println(flag);
		if(flag){
			System.out.println("eee");
			return "success";
		}
		else return "unSuccess";
			
	}


	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Message> loadMessageForCustomer(String tel) {
		// TODO Auto-generated method stub
		return messageDao.getMsgBySender(customerInfoDao.findByTel(tel).getID());
	}

	@Override
	public List<Message> loadMessageForUser(int accId) {
		// TODO Auto-generated method stub
		System.out.println("MiscService:" + accId);
		return messageDao.getMsgByAccepter(accId);
	}

	@Override
	public UserInfo getUser(int uid, String psw) {
		if(userInfoDao.checkUserByID(uid, psw)){
			System.out.println("right");
			return userInfoDao.get(uid);
		}
		else{
			return null;
		}
	}
	
	
	/**
	 *客户端msg只需要填senderid, x, y,由recvMessage（）来填accepter,time,isrevc
	 *取件后再次填写expid，isrev
	 * @param msg
	 * @return
	 */
	@Override
	public String recvMessage(String tel, double x, double y) {
		// TODO Auto-generated method stub
		CustomerInfo c = customerInfoDao.findByTel(tel);
		Message msg = new Message();
		if(c == null){
			CustomerInfo nc = new CustomerInfo();
			nc.setTelCode(tel);
			nc.setName("新用户");
			customerInfoDao.save(nc);
			msg.setSender(nc.getID());
		}
		else{
			msg.setSender(c.getID());
		}
		msg.setX(x);
		msg.setY(y);
		msg.setTime(getCurrentDate());
		msg.setIsrecv(false);
		try {
			messageDao.save(msg);
			msg.setAccepter(messageDao.findSuitAccepter(msg.getSN()));
			if(msg.getAccepter() == 0){
				System.out.println("查找揽收快递员失败-------------");
				return "unsucc";
			}
			messageDao.update(msg);
			return "succ";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unsucc";
		}
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
	public List<Message> isReceive(int SN) {
		// TODO Auto-generated method stub
		List<Message> items = new ArrayList<Message>();
		try {
			Message m = messageDao.get(SN);
			m.setIsrecv(true);
			messageDao.update(m);
			return items;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return items;
		}
	}

	@Override
	public String register(String uname, String tel, String password) {
		// TODO Auto-generated method stub
		UserInfo ui = new UserInfo();
		ui.setName(uname);
		ui.setPWD(password);
		ui.setTelCode(tel);
		try {
			userInfoDao.save(ui);
			return "注册成功";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "注册失败";
		}
	}
	@Override
	public UserInfo getUserInfo(int uid) {
		
		// TODO Auto-generated method stub
		UserInfo ui= null;
		ui=userInfoDao.get(uid);
		System.out.println("getUserInfo:"+ui.toString());
		return ui;
	}

	@Override
	public String saveUserInfo(String ui) {
		// TODO Auto-generated method stub
		System.out.println("liushuo"+ui);
		
		try {
		Map<String,String> uri = PostSplite.postchange(ui);
			UserInfo us=new UserInfo();
		    us.setUID(Integer.parseInt(uri.get("uid")));
		    System.out.println(uri.get("uid"));
		    us.setPWD(uri.get("pwd"));
		    us.setName(uri.get("name"));
		    us.setURull(Integer.parseInt(uri.get("urull")));
		    us.setTelCode(uri.get("telcode"));
		    us.setStatus(Integer.parseInt(uri.get("status")));
		    us.setDptID(uri.get("dptid"));
		    us.setReceivePackageID(uri.get("receivepackageid"));
		    us.setDelivePackageID(uri.get("delivepackageid"));
		    us.setTransPackageID(uri.get("transpackageid"));
		    
		    userInfoDao.update(us);
			
	    	
	    	System.out.println("liushuo");
	    	//userInfoDao.update(us);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unsuccess";
		}
		
	}
	@Override
	public List<TransNode> getNodesList(String regionCode) {
		// TODO Auto-generated method stub
		return transNodeDao.findByRegionCode(regionCode);
	}

	@Override
	public List<UserInfo> getUserList() {
		// TODO Auto-generated method stub
		List<UserInfo> listui=userInfoDao.getUsersList();
		return listui;
	}
	
	
	//liushuo

	@Override
	public String saveNode(String transnodeid,String nodename, String nodetype,  String telcode,String regioncode, float x, float y) {
		// TODO Auto-generated method stub
		System.out.println("liushuo"+nodename);
		TransNode tn = new TransNode();
		tn.setID(transnodeid);
		tn.setNodeName(nodename);
		tn.setNodeType(Integer.parseInt(nodetype));
		tn.setRegionCode(regioncode);
		tn.setTelCode(telcode);
		tn.setX(x);
		tn.setY(y);
		
		try {
			transNodeDao.saveOnly(tn);
			System.out.println(tn.getID());
			return "succ";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unsucc";
		}
	}
	
	@Override
	public TransNode getNode(String code) {
		// TODO Auto-generated method stub
		return transNodeDao.get(code);
	}

	@Override
	public String delete(String nodeid) {
		// TODO Auto-generated method stub
		try {
			transNodeDao.removeById(nodeid);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "default";
		}
		
	}
}
