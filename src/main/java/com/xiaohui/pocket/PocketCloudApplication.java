package com.xiaohui.pocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan // 开启配置属性绑定
public class PocketCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocketCloudApplication.class, args);
    }

}
