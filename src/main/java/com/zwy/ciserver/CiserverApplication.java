package com.zwy.ciserver;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 等于 @Configuration（Spring xml配置）+@EnableAutoConfiguration（自动配置）+@ComponentScan（可发现和装配一些bean）
@SpringBootApplication
@EnableTransactionManagement//开启事务，配合service的@Transactional注解
@MapperScan("com.zwy.ciserver.dao")//不加这个mapper AutoWired会失败
//要做用户系统的话，可以将用户名加入url中。或者作为参数，每次请求都带上。
public class CiserverApplication{

    public static void main(String[] args) {
        SpringApplication.run(CiserverApplication.class, args);
    }

    /**
     * 注册netty-socketio服务端
     */
    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){   //在本地window环境测试时用localhost
            System.out.println("this is  windows");
            config.setHostname("localhost");
        } else {
            config.setHostname("192.168.0.109");
        }
        config.setPort(8084);

        /*config.setAuthorizationListener(new AuthorizationListener() {//类似过滤器
            @Override
            public boolean isAuthorized(HandshakeData data) {
                //http://localhost:8083?username=test&password=test
                //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
                // String username = data.getSingleUrlParam("username");
                // String password = data.getSingleUrlParam("password");
                return true;
            }
        });*/

        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /**
     * tomcat启动时候，扫码socket服务器并注册
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}

