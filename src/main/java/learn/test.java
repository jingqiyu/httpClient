package learn;

import learn.enity.HttpResponse;
import learn.tool.HTTPTool;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class test {
    public static void main(String[] args){
       /* Map<String,String> params = new HashMap<String, String>();
        params.put("appKey","573729c1d0083abb");
        HttpResponse httpResponse = HTTPTool.httpPostForm("http://192.168.10.120:20052/ugc/content/send", params, null, "utf-8");
        System.out.println(httpResponse);*/
        String s = "laoliu qing wo chi yu";
        List<String> strings = Arrays.asList(s.split(" "));
        List<String> result = new ArrayList<String>();
        for (String tmp : strings ) {
            System.out.println(tmp);

            if ( !tmp.equals("laoliu") ) {
                result.add(tmp );
            }
        }
        System.out.println(result.size());
    }
}
