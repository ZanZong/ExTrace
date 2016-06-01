package BaiduMap;

import org.json.JSONException;
import org.json.JSONObject;


public class BaiduApiUtils {
	public static String getCity(double lat,double lng){
		try {
			return getLocationInfo(lat,lng)
					.getJSONObject("result")
					.getJSONObject("addressComponent").getString("city");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getDistrict(double lat,double lng){
		
		try {
			return getLocationInfo(lat, lng)
					.getJSONObject("result")
					.getJSONObject("addressComponent")
					.getString("district");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getFormatted_address(double lat,double lng){
		try {
			return getLocationInfo(lat, lng)
					.getJSONObject("result")
					.getString("formatted_address");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String getStreet(double lat,double lng){
		
		try {
			return getLocationInfo(lat, lng)
					.getJSONObject("result")
					.getJSONObject("addressComponent")
					.getString("street");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static JSONObject getLocationInfo(double lat,double lng){
		String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","
		        + lng + "&output=json&ak=" +"IU3cQYsk76zqxFY6azbce4SGlTyohln5"+"&pois=0";
		JSONObject obj = null;
		try{
			obj = new JSONObject(
					HttpUtil.getRequest(url));
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
}
