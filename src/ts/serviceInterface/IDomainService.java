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

import ts.model.ExpressSheet;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.smodel.History;
import ts.smodel.LocXY;

@Path("/Domain")	//业务操作
public interface IDomainService {
    //快件操作访问接口=======================================================================
	/**
	 *JAX-RS 定义了 @POST、@GET、@PUT 和 @DELETE，分别对应 4 种 HTTP 方法
	 *用于对资源进行创建、检索、更新和删除的操作。 
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
    
  //预填快递单
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
    @Path("/receiveExpressSheetId/id/{id}/uid/{uid}") //揽收快件  	以下三个功能需要存History
	public Response ReceiveExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/dispatchExpressSheetId/id/{id}/uid/{uid}") //转运快件
	public Response DispatchExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliveryExpressSheetId/id/{id}/uid/{uid}") //派送快件
	public Response DeliveryExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/MoveExpressIntoPackage/id/{id}/uid/{uid}") //将快递移动到包裹中
    public boolean MoveExpressIntoPackage(String id, String targetPkgId);
    //包裹操作访问接口=======================================================================
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
    
  //画路径使用  
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

   //转运
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/putExpressIntoPkg/{ExpressSheetid}/{packageid}")
    public Response putExpressIntoPkg(@PathParam("expressSheetId")String ExpressSheetid,
    								  @PathParam("packageId")String packageId);
   
    //历史信息
    @GET
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransHistory/{expreeSheetId}")
    public List<History> getTransHistroy(@PathParam("expreeSheetId")String expressSheetId);
    
   /* @GET
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveTransHistory/{history}/{status}")
    public Response saveTransHistory(@PathParam("history")History history, @PathParam("status")int status);
*/
    //test
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getString")
    public String getString(LocXY local);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/fun/{name}")
    public String fun(@PathParam("name")int shihu);
    
  //@xingjiali
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage2/PackageId/{PackageId}")
	public List<ExpressSheet> getExpressListInPackage2(@PathParam("PackageId")String packageId);
    
 //@xingjiali
    
    //快件派送
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/deliveExpress")
    public List<ExpressSheet> deliveExpress(@FormParam("list")List<String> list,@FormParam("PackId")String PackId);
    
    //签收
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/signExpress/{expressId}")
    public ExpressSheet  signExpress(@PathParam("expressId")String expressId);
    
    //拆包
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/unpackaTransPackage/{packageId}")
    public Response unpackaTransPackage(@PathParam("packageId")String packageId);
    //得到tranPackagenode的发送地区名字和接收名字
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/getTransNamePair/{paira}/{pairb}")
    public Response getTransNamePair(@PathParam("paira") String a,@PathParam("pairb") String b);

}
