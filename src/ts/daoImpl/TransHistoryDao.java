package ts.daoImpl;
/**
 * @author Zongzan
 */
import ts.daoBase.BaseDao;
import ts.model.TransHistory;

public class TransHistoryDao extends BaseDao<TransHistory,Integer> {
	public TransHistoryDao(){
		super(TransHistory.class);
	}
}
