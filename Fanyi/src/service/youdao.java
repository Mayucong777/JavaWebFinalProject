package service;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class youdao {

    //public  Logger logger = LoggerFactory.getLogger(youdao.class);

    public   String YOUDAO_URL = "https://openapi.youdao.com/api";

    public   String APP_KEY = "3f6680b71c3a16d8";

    public   String APP_SECRET = "xKUAYaGE9T93J1QxD6u6KKwQrpT4YSNv";

//    public static void main(String[] args) throws IOException {
//
//    	youdao yd = new youdao();
//        Scanner sc=new Scanner(System.in);
//        String query = sc.next();
//        String from = "zh-CHS";
//        String to = "en";
//        Map<String,String> params = yd.createUrl(query,from,to);
//        
//        System.out.println(yd.requestForHttp(yd.YOUDAO_URL,params));
//        
//    }

    public youdao(){
    	super();
    }
    
    public  Map<String, String> createUrl(String query,String from,String to)
    {
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
        
        return params;
    }
    
    
    public  String readTranslationFromJson(String result){
    	String translation="";
    	JsonParser parser=new JsonParser();
		JsonElement element=parser.parse(result);
		if(element.isJsonObject()) {
			JsonObject object=element.getAsJsonObject();
			if(!(object.get("errorCode").equals(0))){
				translation=object.get("translation").getAsString();
			}
			else{
				translation = object.get("errorCode").getAsString();
			}
		}
		return translation;
    }
    
    public  String requestForHttp(String url,Map<String,String> params) throws IOException {

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            //logger.info("Content-Type:" + contentType[0].getValue());
            if("audio/mp3".equals(contentType[0].getValue())){
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if(result != null){//合成成功
                    String file = "合成的音频存储路径"+System.currentTimeMillis() + ".mp3";
                    byte2File(result,file);
                }
            }else{
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity,"UTF-8");
                EntityUtils.consume(httpEntity);
                //logger.info(json);
                return readTranslationFromJson(json);
                //System.out.println(readTranslationFromJson(json));
            }
        }finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                //logger.info("## release resouce error ##" + e);
            }
        }
		return "无结果";
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

    /**
    *
    * @param result 音频字节流
    * @param file 存储路径
    */
    private  void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            //logger.info(e.toString());
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
