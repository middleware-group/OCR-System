package com.midware.ocr.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 图片绝对地址与虚拟地址映射
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String path = System.getProperty("user.dir");
        String resourcesPath = "file:///" + path + "\\src\\main\\resources\\images\\";
        resourcesPath = resourcesPath.replace('\\','/');
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        //registry.addResourceHandler("/image/**").addResourceLocations("file:///I:/1xuexi/大三下/ERP/大作业/ERP-backend/");
        registry.addResourceHandler("/image/**").addResourceLocations(resourcesPath);
        super.addResourceHandlers(registry);
    }

}
