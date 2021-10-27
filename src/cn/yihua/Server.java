package cn.yihua;

import cn.yihua.httphandler.AddProductHandler;
import cn.yihua.httphandler.LoginHandler;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081),0);
        httpServer.createContext("/login", new LoginHandler());
        httpServer.createContext("/add/product",new AddProductHandler());
        httpServer.setExecutor(Executors.newFixedThreadPool(10));
        httpServer.start();
    }
}
