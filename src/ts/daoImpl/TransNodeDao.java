package ts.daoImpl;

import java.util.List;

import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.TransNode;

public class TransNodeDao extends BaseDao<TransNode, String>{
	public TransNodeDao(){
		super(TransNode.class);
	}

	public List<TransNode> findByRegionCode(String region_code) {
        Assert.hasText(region_code);
        return findBy("regionCode", region_code, "nodeName", true);
	}

}
