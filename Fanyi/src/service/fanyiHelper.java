package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class fanyiHelper {

	private String query;
	private String from;
	private String to;

	public String YOUDAO_URL = "https://openapi.youdao.com/api";
	
	public String APP_KEY = "3f6680b71c3a16d8";

    public String APP_SECRET = "xKUAYaGE9T93J1QxD6u6KKwQrpT4YSNv";
	
	public fanyiHelper(String query,String from ,String to) {
		this.query = query;
		this.from=from;
		this.to=to;
	}

	public String getFanyiFromUrl(String url) throws Exception, IOException {
		String fanyi = "";
		// 访问API
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableResponse = null;

		try {
			closeableResponse = closeableHttpClient.execute(httpGet);
			if (closeableResponse.getStatusLine().getStatusCode() == 200) 
			{
				HttpEntity entity = closeableResponse.getEntity();
				String json = EntityUtils.toString(entity, "utf-8");
				JsonParser jsonParser = new JsonParser();
				JsonElement jsonElement = jsonParser.parse(json);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String msgJson = jsonObject.get("translation").getAsString();
				fanyi = msgJson;
			}
			return fanyi;
		} finally {
			if (closeableResponse != null) {
				closeableResponse.close();
			}
			closeableHttpClient.close();
		}
	}
	
	public String createUrl(){
		String url="";
		
		Map<String, String> params = new HashMap<String,String>();
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(query) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("signType", "v3");
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", APP_KEY);
        
        url = getUrlWithQueryString(YOUDAO_URL,params);
		return url;
		
	}

	public  String getUrlWithQueryString(String url,
			Map<String, String> params) {
		if (params == null) {
			return url;
		}

		StringBuilder builder = new StringBuilder(url);
		if (url.contains("?")) {
			builder.append("&");
		} else {
			builder.append("?");
		}

		int i = 0;
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (value == null) { // 过滤空的key
				continue;
			}

			if (i != 0) {
				builder.append('&');
			}

			builder.append(key);
			builder.append('=');
			builder.append(encode(value));

			i++;
		}

		return builder.toString();
	}
	
	public  String encode(String input) {
		if (input == null) {
			return "";
		}

		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return input;
	}
    /**
     * 生成加密字段
     */
    public  String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public  String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

}
