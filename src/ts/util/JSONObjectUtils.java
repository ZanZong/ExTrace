package ts.util;


import org.json.JSONException;
import org.json.JSONObject;


public class JSONObjectUtils {
	
	/**
	 * 
	 * @param data Json格式的数据
	 * @param key Json数据中的key
	 * 
	 * @return key代表的值
	 */
	public static String getString(String data,String key){
		JSONObject json = null;
		try {
			json = new JSONObject(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(json != null){
			try {
				return json.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
	public static Integer getInt(String data,String key){
		JSONObject json = null;
		try {
			json = new JSONObject(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(json != null){
			try {
				return Integer.valueOf(json.getInt(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
	
	public static Boolean getBoolean(String data,String key){
		JSONObject json = null;
		try {
			json = new JSONObject(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(json != null){
			try {
				return Boolean.valueOf(json.getBoolean(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
	
	public String Union(String... params){
		if(params.length == 0){
			return "";
		}
		else{
			StringBuilder sb = new StringBuilder(params[0].substring(0,params[0].length()-1));
			for(int i = 1;i < params.length;i++){
				sb.append(",");
				sb.append(params[i].subSequence(1, params[i].length()-1));
			}
			sb.append("}");
			return sb.toString();
		}
	}
	
	
}
