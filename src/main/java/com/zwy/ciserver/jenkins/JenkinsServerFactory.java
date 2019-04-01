package com.zwy.ciserver.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.FolderJob;
import com.zwy.ciserver.entity.ModuleEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    @Value("${jenkins.folders.module}")
    private String moduleFolder;
    @Value("${jenkins.folders.project}")
    private String projectFolder;

    private JenkinsServer mJenkinsServer;

    private String mModuleJobTemplate;
    private String mProjectJobTemplate;
    private FolderJob moduleFolderJob;
    private FolderJob projectFolderJob;

    @PostConstruct
    public void init() {
        //放在构造方法中会出错，对象还没有实例化，空指针
        mJenkinsServer = createJenkinsServer();
        try {
            moduleFolderJob = mJenkinsServer.getFolderJob(mJenkinsServer.getJob(moduleFolder)).get();
            projectFolderJob = mJenkinsServer.getFolderJob(mJenkinsServer.getJob(projectFolder)).get();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        Resource resource = new ClassPathResource("jobtemplates/module_job.xml");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s + "\n");
            }
            br.close();
            mModuleJobTemplate = new String(sb.toString().getBytes(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JenkinsServerFactory() {
    }

    public JenkinsServer createJenkinsServer() {
        if (mJenkinsServer != null) {
            return mJenkinsServer;
        }
        try {
            mJenkinsServer = new JenkinsServer(new URI(jenkinsUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return mJenkinsServer;
    }

    //.*：匹配\n外的所有字符
    //[\s\S]*：匹配所有字符。\s空白符，\S非空白符
    public String generateModuleConfig(ModuleEntity moduleEntity) {
        String jobXml = mModuleJobTemplate;
        jobXml = replaceByLabel("displayName", moduleEntity.getCatalog() + "_" + moduleEntity.getName(), jobXml);
        jobXml = replaceByLabel("url", moduleEntity.getRepo(), jobXml);
        jobXml = replaceByLabel("branch", moduleEntity.getBranch(), jobXml);
        jobXml = replaceByLabel("customWorkspace", "/var/jenkins_workspace/" + moduleEntity.getCatalog(), jobXml);
        return jobXml;
    }

    private String replaceByLabel(String label, String replaceStr, String source) {
        String pattern = "<Label>.*</Label>";
        pattern = pattern.replaceAll("Label", label);
        String replace = "<Label>" + replaceStr + "</Label>";
        replace = replace.replaceAll("Label", label);
        return source.replaceAll(pattern, replace);
    }

    public String generateProjectConfig() {
        return mProjectJobTemplate;
    }

    public FolderJob getModuleFolderJob() {
        return moduleFolderJob;
    }

    public FolderJob getProjectFolderJob() {
        return projectFolderJob;
    }

}
