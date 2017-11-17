package learn.tool;


import learn.enity.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 采用 org.apache.httpcomponents 包下的 httpClient 封装的http工具类
 * @author jingqy
 * @version 0.1
 */
public class HttpUtil {

    private static RequestConfig requestConfig = RequestConfig.custom()
        .setSocketTimeout(1000)
        .setConnectTimeout(1000)
        .setConnectionRequestTimeout(1000)
        .build();
    private static HttpUtil instance = null;
    private HttpUtil(){}

    /**
     * 单例模式获取1个HttpUtil实例
     * @return HttpUtil
     */
    public static HttpUtil getInstance(){
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }

    /**
     * HttpPost请求
     * @param url url
     * @param params 参数
     * @param headers 头信息
     * @param encode 编码
     * @return HttpResponse
     */
    public static HttpResponse httpPostForm(String url, Map<String,String> params, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 自定义RequestConfig参数
     * @param url url地址
     * @param params 参数
     * @param headers 头信息
     * @param encode 编码
     * @param requestConfig 连接配置
     * @return HttpResponse
     */
    public static HttpResponse httpPostForm(String url, Map<String,String> params, Map<String,String> headers, String encode, RequestConfig requestConfig){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * httpGet 请求
     * @param url url地址
     * @param params 参数map
     * @param headers 头信息
     * @param encode 编码信息
     * @return HttpResponse
     */
    public static HttpResponse httpGetForm(String url,Map<String,String> params, Map<String,String> headers,String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        String getQuery=null;
        try {
            getQuery = EntityUtils.toString(new UrlEncodedFormEntity(paramList, encode));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if ( getQuery != null && !getQuery.equals("")) {
            url = url + "?" + getQuery;
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(),entry.getValue());
            }
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * httpGet 请求 -- 自定义Config方式
     * @param url url地址
     * @param params 参数map
     * @param headers 头信息
     * @param encode 编码信息
     * @return HttpResponse
     */
    public static HttpResponse httpGetForm(String url,Map<String,String> params, Map<String,String> headers,String encode,RequestConfig requestConfig){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        String getQuery=null;
        try {
            getQuery = EntityUtils.toString(new UrlEncodedFormEntity(paramList, encode));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if ( getQuery != null && !getQuery.equals("")) {
            url = url + "?" + getQuery;
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(),entry.getValue());
            }
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * http-delete 请求
     * @param url 请求地址
     * @param headers 请求头信息
     * @param encode 编码
     * @return HttpResponse
     */
    public static HttpResponse httpDelete(String url,  Map<String,String> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient  = HttpClientBuilder.create().build();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        if ( headers != null && headers.size() > 0 ) {
            for ( String key :headers.keySet() ){
                httpDelete.setHeader( key, headers.get(key) );
            }
        }

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpDelete);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse!=null )
                    closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * httpDelete 以Query方式传递参数 : 注意url最大长度，参数过多请使用 httpDeleteWithJson 方法
     * @param url url
     * @param params 参数map
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpDeleteFormWithQuery(String url, Map<String,String> params, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }

        String getQuery=null;
        try {
            getQuery = EntityUtils.toString(new UrlEncodedFormEntity(paramList, encode));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if ( getQuery != null && !getQuery.equals("")) {
            url = url + "?" + getQuery;
        }

        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
        httpDeleteWithBody.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDeleteWithBody.setHeader(entry.getKey(),entry.getValue());
            }
        }

        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpDeleteWithBody);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 自定义Config
     * httpDelete 以Query方式传递参数 : 注意url最大长度，参数过多请使用 httpDeleteWithJson 方法
     * @param url url
     * @param params 参数map
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpDeleteFormWithQuery(String url, Map<String,String> params, Map<String,String> headers, String encode,RequestConfig requestConfig){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }

        String getQuery=null;
        try {
            getQuery = EntityUtils.toString(new UrlEncodedFormEntity(paramList, encode));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if ( getQuery != null && !getQuery.equals("")) {
            url = url + "?" + getQuery;
        }

        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
        httpDeleteWithBody.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDeleteWithBody.setHeader(entry.getKey(),entry.getValue());
            }
        }

        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpDeleteWithBody);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }




    /**
     * httpDelete 带有body方式
     * @param url url
     * @param json 对象json
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpDeleteWithJson(String url, String json, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
        httpDeleteWithBody.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDeleteWithBody.setHeader(entry.getKey(),entry.getValue());
            }
        }
        httpDeleteWithBody.setHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        try {
            httpDeleteWithBody.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpDeleteWithBody);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



    /**
     * httpDelete 带有body方式 自定义Config信息
     * @param url url
     * @param json 对象json
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpDeleteWithJson(String url, String json, Map<String,String> headers, String encode,RequestConfig requestConfig){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
        httpDeleteWithBody.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDeleteWithBody.setHeader(entry.getKey(),entry.getValue());
            }
        }
        httpDeleteWithBody.setHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        try {
            httpDeleteWithBody.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpDeleteWithBody);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }




    /**
     * HttpPut
     * @param url url
     * @param params 参数map
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpPutForm(String url, Map<String,String> params, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * HttpPut 自定义Config
     * @param url url
     * @param params 参数map
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    public static HttpResponse httpPutForm(String url, Map<String,String> params, Map<String,String> headers, String encode,RequestConfig requestConfig){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(),entry.getValue());
            }
        }

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
