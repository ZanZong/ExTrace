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
		customer.setAddress("郑州市高新区科学大道100号郑州大学新校区松园");
		customer.setDepartment("#18-"
				+ "");
		customer.setName("aaaaa");
		customer.setPostCode(450001);
		customerInfoDao.save(customer);*/
	}
}
