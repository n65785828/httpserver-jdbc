package cn.yihua.httphandler;

import cn.yihua.dao.UserDao;
import cn.yihua.entity.UserEntity;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class LoginHandler extends BaseHandler {

    private UserDao userDao = new UserDao();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream in = httpExchange.getRequestBody();
        Map<String, String> stringStringMap = requestBody(in);
        UserEntity userEntity = userDao.findByUsernameAndPassword(stringStringMap.get("username"), stringStringMap.get("password"));
        String result = null;
        if (userEntity == null) {
            result = "{\"msg\":\"用户不存在\"}";
        } else {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            LOGIN_USERS.put(token, stringStringMap.get("username"));
            result = "{\"token\":\"" + token + "\"}";
        }
        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset=utf8");
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(bytes);
        responseBody.close();
    }
}
