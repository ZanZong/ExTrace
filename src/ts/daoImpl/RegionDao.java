package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.Region;

public class RegionDao  extends BaseDao<Region, String>{
	public RegionDao(){
		super(Region.class);
	}
	/**
     * ��ȡ����ȫ��������������
     */
	public Region getFullNameRegionByID(String ID){
		String id_tmp = "";
		Region rg_0 = null;
		Region ret_rg =  get(ID);

		if(ret_rg.getStage() == 3){
			id_tmp = ID.substring(0, 4)+"00";
			rg_0 = get(id_tmp);
			ret_rg.setCty(rg_0.getCty());

			id_tmp = ID.substring(0, 2)+"0000";
			rg_0 = get(id_tmp);
			ret_rg.setPrv(rg_0.getPrv());
		}
		else if(ret_rg.getStage() == 2){
			id_tmp = ID.substring(0, 2)+"0000";
			rg_0 = get(id_tmp);
			ret_rg.setPrv(rg_0.getPrv());
		}
		return ret_rg;
	}
	
	/**
     * ��ȡ������ȫ�����ַ���
     */
	public String getRegionNameByID(String ID) {
		Region rg = getFullNameRegionByID(ID);

		StringBuffer sbname = new StringBuffer();
		if(rg.getStage()>0)
			sbname.append(rg.getPrv());
		if(rg.getStage()>1)
			sbname.append(rg.getCty());
		if(rg.getStage()>2)
			sbname.append(rg.getTwn());
		return sbname.toString();
	}

	/**
     * ��ȡʡ
     */
	public List<Region> getProvinceList() {
		List<Region> listrg = findBy("stage", 1, "regionCode", true);
		return listrg;
	}

	/**
     * ��ȡ��
     */
	public List<Region> getCityList(String ID) {
		String sid = ID.substring(0, 2).concat("%");
		List<Region> listrg = (List<Region>) findBy("regionCode", true,Restrictions.like("regionCode", sid),Restrictions.eq("stage", 2));
		return listrg;
	}
	
	/**
     * ��ȡ��
     */
	public List<Region> getTownList(String ID) {
		String sid = ID.substring(0, 4).concat("%");
		List<Region> listrg = (List<Region>) findBy("regionCode", true,Restrictions.like("regionCode", sid),Restrictions.eq("stage", 3));
		return listrg;
	}
}
