
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class AddTest extends BaseDao<CustomerInfo, String>{

	@Resource
	private  CustomerInfoDao customerInfoDao ;

	//SessionFactory sf;
	
	@Test
	public void fun(){
		CustomerInfo cus = customerInfoDao.get(1);
		System.out.println(cus.getName());
		CustomerInfo Customer = new CustomerInfo();
		Customer.setAddress("郑州市高新区科学大道100号郑州大学新校区松园");
		Customer.setDepartment("#19-407");
		Customer.setName("zongzan");
		Customer.setPostCode(450001);
		save(Customer);
		
	}
}
class HibernateUtils {  
	   private static SessionFactory factory;  
	   static{  
	           try{  
	            //读取hibernate.cfg.xml文件  
	            Configuration cfg = new Configuration();
	            cfg.addClass(CustomerInfo.class);
	               
	            //建立SessionFactory  
	            factory = cfg.buildSessionFactory();  
	     
	           }catch(Exception e){  
	              e.printStackTrace();   
	              System.out.println("\nError Create SessionFactory\n\n");
	           }  
	   }    
	     
	      //获得开启着的Session  
	   public static Session getSession(){  
	       return ((org.hibernate.SessionFactory) factory).openSession();  
	   }  
	     
	      //关闭Session  
	   public static void closeSession(Session session){  
	       if(session!=null){  
	           if(session.isOpen()){  
	               session.close();  
	           }  
	       }  
	   }  
	     
	   public static SessionFactory getSessionFactory(){  
	       return factory;  
	   }  
	}  
