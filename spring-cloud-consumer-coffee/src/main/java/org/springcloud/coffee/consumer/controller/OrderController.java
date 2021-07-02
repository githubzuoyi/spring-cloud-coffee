package org.springcloud.coffee.consumer.controller;

import com.netflix.discovery.converters.Auto;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 订单微服务controller
 */
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/order/{name}")
    public String orderTicket(@PathVariable String name) {

        // TODO 使用 Spring Cloud 改造请求调用，使用 Feign + Eureka 用过服务名调用
        String balanceUrl = "http://provider/balance/";
        Integer balance = restTemplate.getForObject(balanceUrl + name, Integer.class);
        Integer ticket_price = getTicketPrice();
        int remain = balance - ticket_price;
        System.out.println("请求购票。。");
        if (remain > 0) {
            String reduceBalanceUrl = "http://provider/reducebalance/";
            restTemplate.getForObject(reduceBalanceUrl + name + "/" + remain, Boolean.class);
            System.out.println("购票成功，余额：" + remain);
            return "购票成功，余额：" + remain;
        } else {
            System.out.println("购票成功，余额：" + balance);
            return "购票失败，余额：" + balance;
        }
    }

    private Integer getTicketPrice() {
        return 50;
    }

    @GetMapping("/getService/{provider}")
    public String getServiceInfo(@PathVariable String provider) {
        ServiceInstance instance = loadBalancerClient.choose(provider);
        return String.format("Thread [%s] ServiceInfo : %s", getThreadInfo(), instance.getServiceId() + ":" + instance.getPort());
    }

    private String getThreadInfo() {
        return Thread.currentThread().getName();
    }

}
