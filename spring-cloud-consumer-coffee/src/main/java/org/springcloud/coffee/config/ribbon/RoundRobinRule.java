package org.springcloud.coffee.config.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询复杂均衡策略
 * 每个服务调用 3 次后，切换到另一个服务
 */
public class RoundRobinRule extends AbstractLoadBalancerRule {

    /**
     * 计数器
     */
    private static AtomicInteger SERVER_COUNT = new AtomicInteger(0);

    @Override
    public Server choose(Object key) {

        Server instance_server = null;

        ILoadBalancer balancer = getLoadBalancer();

        List<Server> reachableServers = balancer.getReachableServers();
        List<Server> servers = balancer.getAllServers();

        int reachableServerMount = reachableServers.size();
        int serverMount = servers.size();

        // 取模，轮询server
        int currentCount = getServerCount(serverMount);
        System.out.printf("目前使用节点 %s", currentCount);

        instance_server = servers.get(currentCount);

        return instance_server;
    }

    // atomic CAS 原子操作获取下一个服务节点序号
    private int getServerCount(int serverMount) {

        for (; ; ) {
            int i = SERVER_COUNT.get();
            int count = (i + 1) % serverMount;
            if (SERVER_COUNT.compareAndSet(i, count)) {
                return count;
            }
        }

    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
