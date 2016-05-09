package ts.daoImpl;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Zongzan
 */
import ts.daoBase.BaseDao;
import ts.model.TransHistory;

public class TransHistoryDao extends BaseDao<TransHistory,Integer> {
	public TransHistoryDao(){
		super(TransHistory.class);
	}
	public List<TransHistory> getPackageHistory(String packageId){
		return findBy("SN", true, Restrictions.sqlRestriction("PackageID='" + packageId + "'"));
	}

}
