import java.util.List;

import org.apache.xmlbeans.impl.schema.SoapEncSchemaTypeSystem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BaiduMap.BaiduApiUtils;
import ts.model.Message;


public class test {

	public test() {
		
	}
	public static void main(String[] args){
		/*Gson gson = new Gson();
		String jsonData = "[{\"accepter\":12,\"accepterName\":\"张道达\",\"expId\":100001,\"id\":9,\"isrecv\":false,\"SN\":9,\"sender\":7,\"senderName\":\"呵呵大\",\"tel\":13900894652,\\"
				+ "\"time\":\"2016-05-17T10:04:07+08:00\",\"x\":113.54143006591796,\"y\":34.82447524804688}]";
		List<Message> items = gson.fromJson(jsonData, new TypeToken<List<Message>>(){}.getType());
		System.out.println(items.toString());*/
		System.out.println(BaiduApiUtils.getFormatted_address(34.822499248046874, 113.53189006591798));
		System.out.println("f".equals("f"));
	}

}
