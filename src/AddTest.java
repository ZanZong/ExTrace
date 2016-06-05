import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.MessageDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.daoImpl.UsersPackageDao;
import ts.model.CustomerInfo;
import ts.model.ExpressSheet;
import ts.model.Message;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.util.HttpUtil;
import ts.util.JSONObjectUtils;

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

	@Resource
	CustomerInfoDao customerInfoDao;
	@Resource
	TransPackageDao transPackageDao;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	PackageRouteDao packageRouteDao;
	@Resource
	TransPackageContentDao transPackageContentDao;
	@Resource
	ExpressSheetDao expressSheetDao;
	@Resource
	TransHistoryDao transHistoryDao;
	@Resource 
	MessageDao messageDao;
	@Resource
	UsersPackageDao usersPackageDao;
	@Test
	public void fun(){
		System.out.println(transPackageContentDao.getExistPackage("20254"));
		//System.out.println(messageDao.getMsgByAccepter(11));
		//System.out.println("accepter:" + messageDao.findSuitAccepter(9));
		//System.out.println(userInfoDao.checkUserByID(11,"123456"));
		//	System.out.println(items.isEmpty());
		/*CustomerInfo customer = new CustomerInfo();
		customer.setAddress("֣���и�������ѧ���100��֣�ݴ�ѧ��У����԰");
		customer.setDepartment("#18-"
				+ "");
		customer.setName("aaaaa");
		customer.setPostCode(450001);
		customerInfoDao.save(customer);*/
	}
}
