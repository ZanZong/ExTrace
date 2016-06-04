
package ts.util;

import java.net.URL;

import com.mchange.v1.lang.GentleThread;

public class ImgUtil {
	public static String byte2hex(byte[] b) // 二进制转字符串
	{
	   StringBuffer sb = new StringBuffer();
	   String stmp = "";
	   for (int n = 0; n < b.length; n++) {
	    stmp = Integer.toHexString(b[n] & 0XFF);
	    if (stmp.length() == 1){
	    sb.append("0" + stmp);
	    }else{
	    sb.append(stmp);
	    }
	    
	   }
	   return sb.toString();
	}

	public static byte[] hex2byte(String str) { // 字符串转二进制
	    if (str == null)
	     return null;
	    str = str.trim();
	    int len = str.length();
	    if (len == 0 || len % 2 == 1)
	     return null;
	    byte[] b = new byte[len / 2];
	    try {
	     for (int i = 0; i < str.length(); i += 2) {
	      b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
	     }
	     return b;
	    } catch (Exception e) {
	     return null;
	    }
	}
	public URL getRealPath(){
		return this.getRealPath();
	}
	public static void main(String[] args){
		System.out.println(new ImgUtil().getRealPath());
	}

}
