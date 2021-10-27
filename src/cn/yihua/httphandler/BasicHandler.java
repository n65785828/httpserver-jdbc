package cn.yihua.httphandler;


import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


public abstract class BasicHandler extends BaseHandler {

    private String loginIntercept(HttpExchange httpExchange) throws IOException {
        List<String> tokenList = httpExchange.getRequestHeaders().get("token");
        String username = null;
        if(tokenList!=null&&tokenList.size()>0){
            String token = tokenList.get(0);
            username = LOGIN_USERS.get(token);
        }
        if(username!=null){
            return username;
        }
        OutputStream responseBody = httpExchange.getResponseBody();
        byte[] bytes = "{\"msg\":\"无法访问接口，没有登录信息\"}".getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200,bytes.length);
        responseBody.write(bytes);
        responseBody.close();
        return null;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.setAttribute("username",loginIntercept(httpExchange));
        dealRequest(httpExchange);
    }

    public abstract void dealRequest(HttpExchange httpExchange) throws IOException;
}
