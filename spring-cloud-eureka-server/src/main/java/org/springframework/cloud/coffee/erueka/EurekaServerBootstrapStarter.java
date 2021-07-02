package org.springframework.cloud.coffee.erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * erueka 服务端
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerBootstrapStarter {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBootstrapStarter.class, args);
    }
}
