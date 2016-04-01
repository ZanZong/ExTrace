
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//һ��Ҫ��hinerbate��
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ts.daoBase.BaseDao;
import ts.daoImpl.CustomerInfoDao;
import ts.model.CustomerInfo;

/**|
 * 
 * @author Zongzan
 *1.openSession��getCurrentSession������
   *openSession����رգ�currentSession������������Զ��ر�
   *openSessionû�к͵�ǰ�̰߳󶨣�currentSession�͵�ǰ�̰߳�
2.���ʹ��currentSession��Ҫ��hibernate.cfg.xml�ļ��н������ã�
   *����Ǳ�������jdbc����
     <propertyname="hibernate.current_session_context_class">thread</property>
   *�����ȫ������jta����
   <propertyname="hibernate.current_session_context_class">jta</property>
ȫ��������Դ�����������Э�������񣬿��Կ�Խ������ݿ�ͽ��̡���Դ������һ��ʹ��XA ���׶��ύЭ���롰��ҵ��Ϣϵͳ��(EIS) �����ݿ���н����� 
���������ڵ��� EIS�����ݿ�ı��ز��������ڵ��������ڵ����񡣱��������漰���������Դ��
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
		Customer.setAddress("֣���и�������ѧ���100��֣�ݴ�ѧ��У����԰");
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
	            //��ȡhibernate.cfg.xml�ļ�  
	            Configuration cfg = new Configuration();
	            cfg.addClass(CustomerInfo.class);
	               
	            //����SessionFactory  
	            factory = cfg.buildSessionFactory();  
	     
	           }catch(Exception e){  
	              e.printStackTrace();   
	              System.out.println("\nError Create SessionFactory\n\n");
	           }  
	   }    
	     
	      //��ÿ����ŵ�Session  
	   public static Session getSession(){  
	       return ((org.hibernate.SessionFactory) factory).openSession();  
	   }  
	     
	      //�ر�Session  
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
