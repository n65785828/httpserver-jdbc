package cn.yihua.httphandler;

import cn.yihua.dao.ProductDao;
import cn.yihua.entity.Product;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class AddProductHandler extends BasicHandler{

    private ProductDao productDao = new ProductDao();
    @Override
    public void dealRequest(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        Map<String, String> requestMap = requestBody(inputStream);
        String username = (String) httpExchange.getAttribute("username");
        inputStream.close();
        String name = requestMap.get("name");
        String price = requestMap.get("price");
        Product product = new Product();
        product.setId(UUID.randomUUID().toString().replaceAll("-",""));
        product.setName(name);
        product.setPrice(price);
        product.setUsername(username);
        int column = productDao.save(product);
        String result = null;
        if(column!=1){
            result = "{\"msg\":\"添加失败\"}";
        }else{
            result = "{\"msg\":\"添加成功\"}";
        }
        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type","application/json; charset=utf8");
        httpExchange.sendResponseHeaders(200,bytes.length);
        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(bytes);
        responseBody.close();
    }
}
