package org.example.agent.core;

import org.example.agent.core.netty.NettyHttpClient;
import org.example.agent.core.processor.MonitorProcessor;

public class Container implements LifeCycle{


    /**
     * agent的所有配置
     */
    private final Config config;

    private NettyHttpClient nettyHttpClient;

    private MonitorProcessor monitorProcessor;

    public Container(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        nettyHttpClient = new NettyHttpClient(config);
        monitorProcessor = new MonitorProcessor(config);
    }

    @Override
    public void start() {
        nettyHttpClient.start();
        monitorProcessor.start();
    }

    @Override
    public void shutdown() {
        nettyHttpClient.shutdown();
        monitorProcessor.shutdown();
    }


}
