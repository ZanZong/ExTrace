
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

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
		customer.setAddress("֣���и�������ѧ���100��֣�ݴ�ѧ��У����԰");
		customer.setDepartment("#19-"
				+ "");
		customer.setName("zongzan");
		customer.setPostCode(450001);
		customerInfoDao.save(customer);*/
	}
}
