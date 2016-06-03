package ts.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PostSplite {
	public static Map<String, String> postchange(String uri){
		Map<String, String> map = new HashMap<>();
		String[] li = uri.split("\\&");
		String pp = "[\\s\\S]*\\%[0-9a-fA-F][0-9a-fA-F][\\s\\S]*";
		for(String item:li){
			String[] cc = item.split("\\=");
			StringBuilder sb = new StringBuilder();
			try {
				
				while(cc[1].matches(pp)){
					int s = cc[1].indexOf("%");
					String sub = cc[1].substring(s, cc[1].length());
					StringBuilder sb2 = new StringBuilder();
					
					while(sub.indexOf("%") == 0){
						sb2.append(sub.substring(0+1,0+3));
						sub = sub.substring(0+3, sub.length());
					}
					
					sb.append(cc[1].substring(0, s))
						.append(new String(decode(sb2.toString()).getBytes("GBK"),"UTF-8"));;
					cc[1] = sub;
				}
				sb.append(cc[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put(cc[0].toLowerCase(), sb.toString());
		}
		return map;
	}
	private static String hexString="0123456789ABCDEF";
	public static String decode(String bytes)
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
		for(int i=0;i<bytes.length();i+=2)
			baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
		return new String(baos.toByteArray());
	}
	
	public static void main(String args[]){
		System.out.println(PostSplite.postchange("Name=2016-5-1+18%3A3%3A3"));
	}
}
