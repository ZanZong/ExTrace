
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.CustomerInfo;
import ts.model.ExpressSheet;
import ts.model.PackageRoute;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;

/**|
 * 
 * @author Zongzan
 *1.openSession和getCurrentSession的区别？
   *openSession必须关闭，currentSession在事务结束后自动关闭
   *openSession没有和当前线程绑定，currentSession和当前线程绑定
2.如果使用currentSession需要在hibernate.cfg.xml文件中进行配置：
   *如果是本地事务（jdbc事务）
     <propertyname="hibernate.current_session_context_class">thread</property>
   *如果是全局事务（jta事务）
   <propertyname="hibernate.current_session_context_class">jta</property>
全局事务：资源管理器管理和协调的事务，可以跨越多个数据库和进程。资源管理器一般使用XA 二阶段提交协议与“企业信息系统”(EIS) 或数据库进行交互。 
本地事务：在单个 EIS或数据库的本地并且限制在单个进程内的事务。本地事务不涉及多个数据来源。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AddTest {

//	@Resource
//	private SessionFactory sessionFactory;
//	public SessionFactory setSessionFactory(){
//		return sessionFactory;
//	}
//	@Test 
//	public void fun(){
//		Configuration configuration = new Configuration() ;
//		configuration.configure("ExTrace.cfg.xml");
//		try{
//			SessionFactory sessionFactory = configuration.buildSessionFactory();
//			System.out.println(sessionFactory);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
//	@Resource
//	SessionFactory sessionFactory;
//	
//	@Test
//	public void fun(){
//		System.out.println(sessionFactory);
//	}
	@Resource
	CustomerInfoDao customerInfoDao;
	@Resource
	TransPackageDao transPackageDao;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	PackageRouteDao pkgRouteDao;
	@Resource
	TransPackageContentDao transPkgConDao;
	@Test
	public void fun(){
		/*PackageRoute pr = new PackageRoute();
		TransPackage tp = new TransPackage();
		TransPackageContent transPkgCont = new TransPackageContent();
		ExpressSheet es = new ExpressSheet();
		es.setID("10000001");
		es.setPackageFee((float)20);
		es.setSender(new CustomerInfo());
		es.setRecever(new CustomerInfo());
		es.setStatus(2);
		
		tp.setSourceNode("1000");
		tp.setStatus(2);
		tp.setTargetNode("1001");

		transPkgCont.setPkg(tp);
		transPkgCont.setExpress(es);
		Set<TransPackageContent> s = new HashSet<TransPackageContent>();
		s.add(transPkgCont);
		tp.setContent(s);
		transPkgCont.setPkg(tp);
		
		pr.setPkg(tp);
		pr.setX((float)21.3213);
		pr.setY((float)42.2312);
		packageRD.addPackageRoute(pr);*/
		List<PackageRoute> pkgRouteList =  pkgRouteDao.findPkgRoute("1");
		System.out.println(pkgRouteList.size());
		/*System.out.println(transPackageDao.getDestination("1111112222"));*/
	/*	CustomerInfo cus = customerInfoDao.get(1);
		System.out.println(cus.getName());*/
		
		/*CustomerInfo customer = new CustomerInfo();
		customer.setAddress("郑州市高新区科学大道100号郑州大学新校区松园");
		customer.setDepartment("#19-"
				+ "");
		customer.setName("zongzan");
		customer.setPostCode(450001);
		customerInfoDao.save(customer);*/
	}
}
