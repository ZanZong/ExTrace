package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
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
	public String getRegionString(String id){
		/*List<TransNode> tns = getTransNodeByNode(id);*/
		return  get(id).getNodeName();
	}
	public TransNode getTransNodeByName(String source) {
		// TODO Auto-generated method stub
		//System.out.println(source);
		//System.out.println(findBy("nodeName",source,"id",true).get(0));
		return findBy("nodeName",source,"id",true).get(0);
	}
	public TransNode getTransNodeByNode(String node) {
		// TODO Auto-generated method stub
		//System.out.println(source);
		//System.out.println(findBy("id",node,"id",true).get(0));
		return findBy("id",node,"id",true).get(0);
	}
}
