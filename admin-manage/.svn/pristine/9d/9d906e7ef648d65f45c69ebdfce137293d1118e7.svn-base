package org.bumishi.admin;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * Created by xieqiang on 2016/9/11.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAdminServer
@EnableAutoConfiguration
public class Application{

    public static void main(String[] arg){
        SpringApplication.run(Application.class);
    }
    
    @Bean 
    public MultipartConfigElement multipartConfigElement() { 
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("512MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("512MB"); 
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        return factory.createMultipartConfig(); 
    } 
}

