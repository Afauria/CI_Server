package com.zwy.ciserver;

import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by Afauria on 2019/3/1.
 */
@Component
public class JenkinsServerFactory {
    //@Value注解从配置文件获取属性，使用的时候不能new，应该用@AutoWired
    @Value("${jenkins.url}")
    private String jenkinsUrl;
    @Value("${jenkins.url}")
    private String jenkinsUser;
    @Value("${jenkins.pwd}")
    private String jenkinsPwd;

    private JenkinsServer mJenkinsServer;

    private JenkinsServerFactory() {
    }

    public JenkinsServer createJenkinsServer() {
        try {
            mJenkinsServer = new JenkinsServer(new URI(jenkinsUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return mJenkinsServer;
    }
}
