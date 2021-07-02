package org.springcloud.coffee.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户微服务应用
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ProviderBootstrap.class,args);
    }
}
