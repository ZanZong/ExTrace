package ts.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Http �򵥷�װ
 * 
 * @author xuyw
 * @email xyw10000@163.com
 * @date 2012-06-14
 */
public class HttpUtil {
	// ���ӳ�ʱʱ��
	private static final int CONNECTION_TIMEOUT = 3000;
	// ��ȡ��ʱʱ��
	private static final int READ_TIMEOUT = 5000;
	// ��������
	private static final String ENCODE_CHARSET = "utf-8";

	/**
	 * ����HTTP_POST����
	 * 
	 * @see ������Ĭ�ϵ����ӺͶ�ȡ��ʱ��Ϊ30��
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            ���͵�Զ����������������[a:1,b:2]
	 * @return String
	 */
	public static String postRequest(String reqURL, String... params) {
		StringBuilder resultData = new StringBuilder();
		URL url = null;
		try {

			url = new URL(reqURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection urlConn = null;
		InputStreamReader in = null;
		BufferedReader buffer = null;
		String inputLine = null;
		DataOutputStream out = null;
		if (url != null) {
			try {
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setDoInput(true);// ���������������ֽ���
				urlConn.setDoOutput(true);// ��������������ֽ���
				urlConn.setRequestMethod("POST");
				urlConn.setUseCaches(false); // POST������ʹ�û���
				urlConn.setInstanceFollowRedirects(true);
				urlConn.setConnectTimeout(CONNECTION_TIMEOUT);// �������ӳ�ʱ
				urlConn.setReadTimeout(READ_TIMEOUT); // ���ö�ȡ��ʱ
				// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				urlConn.setRequestProperty("Charset", ENCODE_CHARSET);//
				String param = sendPostParams(params);
				urlConn.setRequestProperty("Content-Length", param.getBytes().length + "");//
				// urlConn.setRequestProperty("Connection", "Keep-Alive");
				// //���ó�����
				urlConn.connect();// ���ӷ�����������Ϣ
				if (!"".equals(param)) {
					out = new DataOutputStream(urlConn.getOutputStream());
					// ��Ҫ�ϴ�������д������
					out.writeBytes(param);
					// ˢ�¡��ر�
					out.flush();
					out.close();

				}
				in = new InputStreamReader(urlConn.getInputStream(), HttpUtil.ENCODE_CHARSET);
				buffer = new BufferedReader(in);
				if (urlConn.getResponseCode() == 200) {
					while ((inputLine = buffer.readLine()) != null) {
						resultData.append(inputLine);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (buffer != null) {
						buffer.close();
					}

					if (in != null) {
						in.close();
					}

					if (urlConn != null) {
						urlConn.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultData.toString();
	}

	/**
	 * ����HTTP_GET����
	 * 
	 * @see ������Ĭ�ϵ����ӺͶ�ȡ��ʱ��Ϊ30��
	 * @param httpUrl
	 *            �����ַ
	 * @param map
	 *            ���͵�Զ����������������[a:1,b:2]
	 * @return String
	 */
	public static String getRequest(String httpUrl, String... params) {
		StringBuilder resultData = new StringBuilder();
		URL url = null;
		try {

			String paramurl = sendGetParams2(httpUrl, params);
			url = new URL(paramurl);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection urlConn = null;
		InputStreamReader in = null;
		BufferedReader buffer = null;
		String inputLine = null;
		if (url != null) {
			try {
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setRequestMethod("GET");
				urlConn.setConnectTimeout(CONNECTION_TIMEOUT);
				in = new InputStreamReader(urlConn.getInputStream(), HttpUtil.ENCODE_CHARSET);
				buffer = new BufferedReader(in);
				if (urlConn.getResponseCode() == 200) {
					while ((inputLine = buffer.readLine()) != null) {
						resultData.append(inputLine);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (buffer != null) {
						buffer.close();
					}

					if (in != null) {
						in.close();
					}
					if (urlConn != null) {
						urlConn.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return resultData.toString();
	}

	/**
	 * Post׷�Ӳ���
	 * 
	 * @see <code>params</code>
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            ���͵�Զ����������������[a:1,b:2]
	 * @return
	 */
	private static String sendPostParams(String... params) {
		StringBuilder sbd = new StringBuilder("");
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				String[] temp = params[i].split(":");
				sbd.append(temp[0]);
				sbd.append("=");
				sbd.append(urlEncode(temp[1]));
				sbd.append("&");
			}
			sbd.setLength(sbd.length() - 1);// ɾ�����һ��
		}
		return sbd.toString();
	}

	/**
	 * ������ʽΪʱʹ�ø÷�����{}/{}/{}��
	 * @param reqURL
	 * @param params
	 * @return
	 */
	private static String sendGetParams2(String reqURL, String... params) {
		StringBuilder sbd = new StringBuilder(reqURL);
		for(int i = 0; i < params.length; i++){
			sbd.append("/" + params[i]);
		}
		return sbd.toString();
	}
	/**
	 * Get׷�Ӳ���
	 * 
	 * @see <code>params</code>
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            ���͵�Զ����������������[a:1,b:2]
	 * @return
	 */
	private static String sendGetParams(String reqURL, String... params) {
		StringBuilder sbd = new StringBuilder(reqURL);
		if (params != null && params.length > 0) {
			if (isexist(reqURL, "?")) {// ����?
				sbd.append("&");
			} else {
				sbd.append("?");
			}
			for (int i = 0; i < params.length; i++) {
				String[] temp = params[i].split(":");
				sbd.append(temp[0]);
				sbd.append("=");
				sbd.append(urlEncode(temp[1]));
				sbd.append("&");
			}
			sbd.setLength(sbd.length() - 1);// ɾ�����һ��
		}
		return sbd.toString();
	}

	// ����ĳ���ַ����Ƿ����
	private static boolean isexist(String str, String fstr) {
		return str.indexOf(fstr) == -1 ? false : true;
	}

	/**
	 * ����
	 * 
	 * @param source
	 * @return
	 */
	private static String urlEncode(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, HttpUtil.ENCODE_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}