package ts.daoImpl;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Zongzan
 */
import ts.daoBase.BaseDao;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.smodel.History;
/**
 * 	���պ�����uidFrom��ֵ��û�еģ������衮0��Ϊ��ʱ��uidFrom
 * 	����ʱ��û��uidTo,�����衮1��Ϊ��ʱ��uidTo
 */
public class TransHistoryDao extends BaseDao<TransHistory,Integer> {
	TransPackageDao transPackageDao;
	
	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}
	public void setTransPackageDao(TransPackageDao transPackageDao) {
		this.transPackageDao = transPackageDao;
	}
	public TransHistoryDao(){
		super(TransHistory.class);
	}
	public List<TransHistory> getPackageHistory(String packageId){
		return findBy("SN", true, Restrictions.sqlRestriction("PackageID='" + packageId + "'"));
	}
	/*public boolean saveTransHistory(TransHistory th, int status){
		th.setPkg(transPackageDao.get(history.getPackageID()));
		th.setUIDFrom(history.getUidFrom());
		th.setX(history.getX());
		th.setY(history.getY());
		if(status == TransPackage.STATUS.RECEIVE){
			th.setUIDFrom(0);
			th.setUIDTo(history.getUidTo());
		}
		else if(status == TransPackage.STATUS.DELIVERYPKG){
			th.setUIDFrom(history.getUidFrom());
			th.setUIDTo(0);
		}
		else{
			th.setUIDFrom(history.getUidFrom());
			th.setUIDTo(history.getUidTo());
		}
		try {
			transHistoryDao.save(th);
			return Response.ok("�Ѹ�����ʷ").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).build();
		}
	}*/
}
