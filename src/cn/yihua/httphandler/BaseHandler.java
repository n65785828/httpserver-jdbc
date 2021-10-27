package cn.yihua.httphandler;

import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseHandler implements HttpHandler {

    protected static final Map<String,String> LOGIN_USERS = new ConcurrentHashMap<>();

    protected Map<String,String> requestBody(InputStream requestBody) throws IOException {
        Map<String,String> resultMap = new HashMap<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = requestBody.read(buf))!=-1){
            baos.write(buf,0,len);
        }
        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        String[] split = data.split("&");
        for (String row: split) {
            String[] keyValue = row.split("=");
            if(keyValue.length==2){
                resultMap.put(URLDecoder.decode(keyValue[0],"utf-8"),URLDecoder.decode(keyValue[1],"utf-8"));
            }
        }
        requestBody.close();
        return resultMap;
    }
}
