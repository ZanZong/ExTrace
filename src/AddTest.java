
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

//一定要导hinerbate的
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ts.daoBase.BaseDao;
import ts.daoImpl.CustomerInfoDao;
import ts.model.CustomerInfo;

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
	
	@Test
	public void fun(){
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
