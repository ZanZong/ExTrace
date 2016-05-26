package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class MiscService implements IMiscService{
	//TransNodeCatalog nodes;	//�Լ����Ļ�����ض����Ȳ�Ҫ��,��Hibernate����Ը�һ�£��Ժ����ȥ
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
	public TransNode getNode(String code) {
		// TODO Auto-generated method stub
		return null;
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
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//�ⲿ�ֹ��ܷŵ�DAO��ȥ��
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
	public List<Message> loadMessageForCustomer(int uid) {
		// TODO Auto-generated method stub
		return messageDao.getMsgBySender(uid);
	}

	@Override
	public List<Message> loadMessageForUser(int accId) {
		// TODO Auto-generated method stub
		System.out.println("MiscService:" + accId);
		return messageDao.getMsgByAccepter(accId);
	}

	/**
	 *�ͻ���msgֻ��Ҫ��senderid, x, y,��recvMessage��������accepter,time,isrevc
	 *ȡ�����ٴ���дexpid��isrev
	 * @param msg
	 * @return
	 */
	@Override
	public int recvMessage(int senderId, double x, double y) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.setSender(senderId);
		msg.setX(x);
		msg.setY(y);
		msg.setTime(getCurrentDate());
		msg.setIsrecv(false);
		try {
			messageDao.save(msg);
			msg.setAccepter(messageDao.findSuitAccepter(msg.getSN()));
			messageDao.update(msg);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public Date getCurrentDate() {
		//����һ�����������ʱ��,��Ȼ,SQLʱ���JAVAʱ���ʽ��һ��
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
	public int isReceive(int SN) {
		// TODO Auto-generated method stub
		try {
			Message m = messageDao.get(SN);
			m.setIsrecv(true);
			messageDao.update(m);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
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
			return "ע��ɹ�";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ע��ʧ��";
		}
	}

	
}
