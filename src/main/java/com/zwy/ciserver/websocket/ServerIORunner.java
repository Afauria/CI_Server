package com.zwy.ciserver.websocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在项目服务启动的时候启动socket.io服务
 * Created by Afauria on 2019/4/2.
 */
@Component
@Order(value=1)
public class ServerIORunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(Logger.class);
    private final SocketIOServer server;


    @Autowired
    public ServerIORunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
        logger.info("socket.io启动成功！");
    }

}