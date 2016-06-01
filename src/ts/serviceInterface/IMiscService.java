package ts.serviceInterface;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.Message;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.UserInfo;

@Path("/Misc")
public interface IMiscService {
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNode/{NodeCode}") 
	public TransNode getNode(@PathParam("NodeCode")String code);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNodesList/{RegionCode}/{Type}") 
	public List<TransNode> getNodesList(@PathParam("RegionCode")String regionCode, @PathParam("Type")int type);

    //===============================================================================================
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByName/{name}") 
	public List<CustomerInfo> getCustomerListByName(@PathParam("name")String name);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByTelCode/{TelCode}") 
	public List<CustomerInfo> getCustomerListByTelCode(@PathParam("TelCode")String TelCode);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerInfo/{id}") 
	public Response getCustomerInfo(@PathParam("id")String id);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteCustomerInfo/{id}") 
	public Response deleteCustomerInfo(@PathParam("id")int id);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveCustomerInfo") 
	public Response saveCustomerInfo(CustomerInfo obj);
    
    //===============================================================================================
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Path("/getProvinceList") 
    @Consumes("application/json")
	public List<CodeNamePair> getProvinceList();
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCityList/{prv}") 
	public List<CodeNamePair> getCityList(@PathParam("prv")String prv);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTownList/{city}") 
	public List<CodeNamePair> getTownList(@PathParam("city")String city);
    
    @GET
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
    @Path("/getRegionString/{id}") 
	public String getRegionString(@PathParam("id")String id);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getRegion/{id}") 
	public Region getRegion(@PathParam("id")String id);
    //----用户登陆注册
    @GET
    @Consumes({ MediaType.TEXT_PLAIN})
    @Produces({ MediaType.TEXT_PLAIN})
    @Path("/checkLoginUser/{uid}/{pwd}")
	public String checkLoginUser(@PathParam("uid")int uid, @PathParam("pwd") String pwd);
    
    @GET
    @Consumes({ MediaType.TEXT_PLAIN})
    @Produces({ MediaType.TEXT_PLAIN})
    @Path("/register/{uname}/{tel}/{password}")
    public String register(@PathParam("uname")String uname, @PathParam("tel")String tel,
    						@PathParam("password")String password);
    
    
    //客户用来查找自己发出的请求
    @GET
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/loadMessageForCustomer/{tel}")
    public List<Message> loadMessageForCustomer(@PathParam("tel") String tel);
    
    //快递员load属于自己的请求
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/loadMessageForUser/{uid}")
    public List<Message> loadMessageForUser(@PathParam("uid")int accId);
    
    //添加请求，适配快递员-----待测试
    @GET	
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/recvMessage/{tel}/{x}/{y}")
    public int recvMessage(@PathParam("tel")String tel,
    		@PathParam("x")double x, @PathParam("y")double y);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/isReceive/{SN}")
    public int isReceive(@PathParam("SN")int SN);
    
	@GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getUser/{uid}/{psw}")
    public UserInfo getUser(@PathParam("uid")int uid,@PathParam("psw") String psw);
	
	//xingjiali
	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/getUserInfo/{uid}") 
	public UserInfo getUserInfo(@PathParam("uid") int uid);
	
	@GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("saveUserInfo") 
	public String saveUserInfo(UserInfo ui);
	
	public void RefreshSessionList();
	public void CreateWorkSession(int uid);
}
