package com.sl.ue.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static String get(String url){
        try {
        	HttpClient client = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
        } 
        catch (IOException e) {
        	e.printStackTrace();
        }
        return null;

	}
	
	public static String post(String url, Map<String, String> params){
		BufferedReader in = null;  
	    try {  
	        // 定义HttpClient  
	        HttpClient client = HttpClientBuilder.create().build();  
	        // 实例化HTTP方法  
	        HttpPost request = new HttpPost();  
	        request.setURI(new URI(url));
	        
	        request.addHeader("Content-Type", "application/json");
	        
	        //设置参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
	        for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String value = params.get(name);
				nvps.add(new BasicNameValuePair(name, value));
				
				//System.out.println(name +"-"+value);
			}
	        request.setEntity(new UrlEncodedFormEntity(nvps,StandardCharsets.UTF_8));
	        
	        HttpResponse response = client.execute(request);  
	        int code = response.getStatusLine().getStatusCode();
	        if(code == 200){	//请求成功
	        	in = new BufferedReader(new InputStreamReader(response.getEntity()  
	                    .getContent(),"utf-8"));
	            StringBuffer sb = new StringBuffer("");  
	            String line = "";  
	            String NL = System.getProperty("line.separator");  
	            while ((line = in.readLine()) != null) {  
	                sb.append(line + NL);  
	            }
	            
	            in.close();  
	            
	            return sb.toString();
	        }
	        else{	
	        	System.out.println("状态码：" + code);
	        	return null;
	        }
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	
	    	return null;
	    }

	}
}
