package com.zwy.ciserver.websocket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.zwy.ciserver.common.WSEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Afauria on 2019/4/2.
 */
@Component
public class MessageEventHandler {
    public static SocketIOServer socketIoServer;
    static final int limitSeconds = 60;
    //线程安全的map
    public static ConcurrentHashMap<UUID, SocketIOClient> webSocketMap = new ConcurrentHashMap();

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.socketIoServer = server;
    }

    /**
     * 客户端连接的时候触发，前端js触发：socket = io.connect("http://192.168.0.109:8084");
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
//        可以通过url参数指定用户名，存入map
        String clientId = client.getHandshakeData().getSingleUrlParam("clientId");
        webSocketMap.put(client.getSessionId(), client);
        //socketIoServer.getClient(client.getSessionId()).sendEvent("message", "back data");
        System.out.println("客户端:" + client.getSessionId() + "已连接,clientId=" + clientId);
    }

    /**
     * 客户端关闭连接时触发：前端js触发：socket.disconnect();
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("客户端:" + client.getSessionId() + "断开连接");
        String clientId = client.getHandshakeData().getSingleUrlParam("clientId");
        webSocketMap.remove(client.getSessionId());
    }

    /**
     * 自定义消息事件，客户端js触发：socket.emit('clientevent', {msg: msg}); 时触发
     * 前端js的 socket.emit("事件名","参数数据")方法，是触发后端自定义消息事件的时候使用的,
     * 前端js的 socket.on("事件名",匿名函数(服务器向客户端发送的数据))为监听服务器端的事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msg: msg}
     */
    @OnEvent(value = "clientevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
        System.out.println("发来消息：" + data);
        //服务器端回复消息
        //socketIoServer.getClient(client.getSessionId()).sendEvent("event_server", "你好 data");
        client.sendEvent(WSEvent.COMMON, "我是服务端发送的信息");
    }

    //群发消息
    public static void sendAll(MessageInfo data) {
        for (SocketIOClient client : webSocketMap.values()) {
            client.sendEvent(WSEvent.COMMON, data);
        }
    }

    public static void sendAll(String event, MessageInfo data) {
        for (SocketIOClient client : webSocketMap.values()) {
            client.sendEvent(event, data);
        }
    }

    //发送消息到指定sessionId
    public static void sendToSession(UUID sid, MessageInfo data) {
        if (webSocketMap.containsKey(sid)) {
            webSocketMap.get(sid).sendEvent(WSEvent.COMMON, data);
        }
    }
}