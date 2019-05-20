package com.sl.ue.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public static String post(String url, String params){
		BufferedReader in = null;  
	    try {  
	        // 定义HttpClient  
	        HttpClient client = HttpClientBuilder.create().build();  
	        // 实例化HTTP方法  
	        HttpPost request = new HttpPost();  
	        request.setURI(new URI(url));
	        
	        //设置参数
        	StringEntity entity = new StringEntity(params, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            request.setEntity(entity);
	        
	        
	        
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
	
	public static String post1(String urlStr, String params){
        try{
        	URL url=new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.addRequestProperty("Content-Type", "text/html");
            //conn.addRequestProperty("Accept", "application/json");
        	DataOutputStream printWriter = new DataOutputStream(conn.getOutputStream());
        	printWriter.write(params.getBytes("utf-8"));
            printWriter.flush();
            BufferedReader in = null;
            StringBuilder sb = new StringBuilder();
            try{
               in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
               String str = null;
               while((str = in.readLine()) != null){
                   sb.append( str );
               }
            }catch (Exception ex){

            }finally{    
            	try{
            		conn.disconnect();
            		if(in!=null){
            			in.close();
            		}
            		if(printWriter!=null){
            			printWriter.close();
            		}
            	}catch(IOException ex){

            	}
            }
            return sb.toString();
        }catch(Exception ex){
        	return null;
        }

	}
	
	public static String post2(String url,String jsonParam){
		try {
	        CloseableHttpClient client = null;
	        CloseableHttpResponse response = null;
	        try {
	
	            HttpPost httpPost = new HttpPost(url);
	            //httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
	            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
	            //httpPost.setHeader(HTTP.CONTENT_TYPE, "text/html");
	            
	            
	            StringEntity entityPost = new StringEntity(jsonParam, "utf-8");
//	            entityPost.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//	                    "application/json"));
	            entityPost.setContentEncoding("UTF-8");
	            //entityPost.setContentType("application/json");
	            httpPost.setEntity(entityPost);
	            
//	            httpPost.setEntity(new StringEntity(jsonParam,
//	                    ContentType.create("text/json", "UTF-8")));
	
	            client = HttpClients.createDefault();
	            response = client.execute(httpPost);
	            HttpEntity entity = response.getEntity();
	            String result = EntityUtils.toString(entity);
	            System.out.println(result);
	            return result;
	        } finally {
	            if (response != null) {
	                response.close();
	            }
	            if (client != null) {
	                client.close();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	public static String postUrl(String url, String params){
		BufferedReader in = null;  
	    try {  
	    	url+="?"+params;
	        // 定义HttpClient  
	        HttpClient client = HttpClientBuilder.create().build();  
	        // 实例化HTTP方法  
	        HttpPost request = new HttpPost();  
	        request.setURI(new URI(url));
	        
	        request.addHeader("Content-Type", "application/json;charset=UTF-8");
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
	
	public static String postJson(String url, String json){

		BufferedReader in = null;  
	    try {  
	        // 定义HttpClient  
	        HttpClient client = HttpClientBuilder.create().build();  
	        // 实例化HTTP方法  
	        HttpPost request = new HttpPost();  
	        request.setURI(new URI(url));
	        
	        request.addHeader("Content-Type", "application/json;charset=UTF-8");
	        
	        //设置参数
	        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
	        entity.setContentType("application/json");
            request.setEntity(entity);
            
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
