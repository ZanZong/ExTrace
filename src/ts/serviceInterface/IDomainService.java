package ts.serviceInterface;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.annotations.Parameter;
import org.jboss.logging.annotations.Param;

import ts.model.ExpressSheet;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.UserInfo;
import ts.smodel.History;
import ts.smodel.LocXY;
import ts.smodel.PackageNamePair;
import ts.smodel.WebHistory;

@Path("/Domain")	//ҵ�����
public interface IDomainService {
    //����������ʽӿ�=======================================================================
	/**
	 *JAX-RS ������ @POST��@GET��@PUT �� @DELETE���ֱ��Ӧ 4 �� HTTP ����
	 *���ڶ���Դ���д��������������º�ɾ���Ĳ����� 
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newExpressSheet/id/{id}/uid/{uid}") 
	public Response newExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveExpressSheet") 
	public Response saveExpressSheet(ExpressSheet obj);
    
  //Ԥ���ݵ�
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/savePreFillList")
    public Response savePreFillList(ExpressSheet obj);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/FillList")
    public Response FillList(ExpressSheet obj);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPreFillListInPackage/{packageId}")
    public List<ExpressSheet> getPreFillListInPackage(@PathParam("package")String packageId);
   
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/unpackTransPackage/{packageId}")
    public Response unpackTransPackage(@PathParam("packageId")String packageId);
    
    //=======================================================
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/receiveExpressSheetId/id/{id}/uid/{uid}") //���տ��  	��������������Ҫ��History
	public Response ReceiveExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/dispatchExpressSheetId/id/{id}/uid/{uid}") //ת�˿��
	public Response DispatchExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliveryExpressSheetId/id/{id}/uid/{uid}") //���Ϳ��
	public Response DeliveryExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/MoveExpressIntoPackage/id/{id}/targetPkgId/{targetPkgId}") //������ƶ���������
    public boolean MoveExpressIntoPackage(@PathParam("id")String id, @PathParam("targetPkgId")String targetPkgId);
   
    @POST
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
    @Path("/MoveExpressIntoPackage") //������ƶ���������
    public boolean MoveExpressIntoPackage(String uri);
    
    //�����������ʽӿ�=======================================================================
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newTransPackage/id/{expreeSheetId}/uid/{uid}") 
    public Response newTransPackage(@PathParam("expreeSheetId")String expreeSheetId,
    			@PathParam("uid")int uid);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveTransPackage") 
	public Response saveTransPackage(TransPackage obj);
    
  //��·��ʹ��  
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/savePackageRoutePos/{packagetId}/{x}/{y}")
    public Response saveRoutePos(@PathParam("packagetId")String packageId,@PathParam("x")double x,@PathParam("y")double y);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getPackageRoutePos/{ExpressSheetid}/{time}")
    public List<LocXY> getPackageRoutePos(@PathParam("ExpressSheetid")String ExpressSheetid, @PathParam("time")String time);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getPackageRoutePos/{ExpressSheetid}")
    public List<LocXY> getPackageRoutePos(@PathParam("ExpressSheetid")String ExpressSheetid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getPostCode/{pro}/{city}/{town}")
    public String getPostCode(@PathParam("pro")String pro, @PathParam("city")String city, @PathParam("town")String town);

   //ת��
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/putExpressIntoPkg/{ExpressSheetid}/{packageid}")
    public Response putExpressIntoPkg(@PathParam("expressSheetId")String ExpressSheetid,
    								  @PathParam("packageId")String packageId);
   
    //��ʷ��Ϣzong
    @GET
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransHistory/{expreeSheetId}")
    public List<History> getTransHistroy(@PathParam("expreeSheetId")String expressSheetId);
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getWebHistory/{expressSheetId}")
    public List<WebHistory> getWebHistory(@PathParam("expressSheetId") String expressSheetId);
    
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/saveTransHistory/{history}")
    public String saveTransHistory(@PathParam("history")History history);

    //test
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getString")
    public String getString(LocXY local);
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/fun")
    public String fun();
    
  //@xingjiali
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path("/getExpressInPackage/{PackageId}") //�����ڵĿ��
	public List<ExpressSheet> getExpressInPackage(@PathParam("PackageId")String packageId);

    //@xingjiali
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage2/{PackageId}") //�õ������б�ɾ���Ŀ��
	public List<ExpressSheet> getExpressListInPackage2(@PathParam("PackageId")String packageId);
  
 //@xingjiali
    
    //�������
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/deliveExpress")
    public List<ExpressSheet> deliveExpress(@FormParam("list")List<String> list,@FormParam("PackId")String PackId);
    
    //ǩ��
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/signExpress/{expressId}")
    public ExpressSheet  signExpress(@PathParam("expressId")String expressId);
    
/*    //���
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/unpackaTransPackage/{packageId}")
    public Response unpackaTransPackage(@PathParam("packageId")String packageId);*/
    //�õ�tranPackagenode�ķ��͵������ֺͽ�������
    
    //xingjiali
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/getTransNamePair/{paira}/{pairb}")
    public Response getTransNamePair(@PathParam("paira") String a,@PathParam("pairb") String b);

    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/getTransPackageById/{id}") 
	public List<PackageNamePair> getTransPackageById(@PathParam("id")String id);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/getTransPackagesBySource/{source}") 
	public List<PackageNamePair> getTransPackagesBySource(@PathParam("source")String source);

    //����ԭ�ص㣬���Ұ���
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/getTransNamePair/{paira}/{pairb}")
    public List<TransPackage> getTransPackageBySource(@PathParam("source") String source);
   
    
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/getTransPackagesByDestination/{destination}") 
	public List<PackageNamePair> getTransPackagesByDestination(@PathParam("destination")String destination);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/getTransPackagesBySD/{source}/{destination}") 
	public List<PackageNamePair> getTransPackagesBySD(@PathParam("source")String source,@PathParam("destination")String destination);
    
    @POST
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/saveTransPackages") 
	public String saveTransPackages(String obj);
    
    @POST
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/updateTransPackages") 
	public String updateTransPackages(String obj);
    
    //�ϴ�ͼƬ
    @POST
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/savePic") 
	public String savePic(String pic);
    
	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	@Path("/getPicPath/{expressSheet}")
	public String getPicPath(@PathParam("expressSheet")String expressSheet);
    
	//���ɿ��
	@GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/allocationForDelver/{nodeId}/{userId}")
    public String allocationForDelver(@PathParam("nodeId")String nodeid,
    		@PathParam("userId")String userid);
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/getTransUserList/{nodeId}")
    public List<UserInfo> getTransUserList(@PathParam("nodeId")String nodeid);
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/getAllExpressSheetOld/{pkId}")
    public List<ExpressSheet> getAllExpressSheetOld(@PathParam("pkId")String pkid); 
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/getAllExpressSheetNow/{pkId}")
    public List<ExpressSheet> getAllExpressSheetNow(@PathParam("pkId")String pkid); 
    
}
