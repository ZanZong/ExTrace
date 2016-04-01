package ts.daoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ts.daoBase.BaseDao;
import ts.model.CustomerInfo;
public class CustomerInfoDao extends BaseDao<CustomerInfo, Integer>{
	private RegionDao regionDao;
	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}
	
	public CustomerInfoDao(){
		super(CustomerInfo.class);
	}
	
	public CustomerInfo get(int id) {
		//System.out.println("id=" + id);
		CustomerInfo ci = super.get(id);
		ci.setRegionString(regionDao.getRegionNameByID(ci.getRegionCode()));	//获取区域的名字字符串
		return ci;
	}

	public List<CustomerInfo> findByName(String name) {
		return findLike("name", name+"%", "telCode", true);
	}

	public List<CustomerInfo> findByTelCode(String telCode) {
		return findBy("telCode", telCode, "telCode", true);
	}
	@Resource
	static CustomerInfo Customer;
	public static void main(String[] args){
		Customer.setAddress("郑州市高新区科学大道100号郑州大学新校区松园");
		Customer.setDepartment("#19-407");
		Customer.setName("zongzan");
		Customer.setPostCode(450001);
		save(Customer);
	}
}
