package org.example.agent.core.processor;

import lombok.extern.slf4j.Slf4j;
import org.example.agent.core.Config;
import org.example.agent.monitor.api.Monitor;
import org.example.agent.monitor.api.MonitorResponse;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MonitorProcessor extends processor {

    private final Config config;

    private ScheduledExecutorService monitorScheduledThreadPool;

    public MonitorProcessor(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        monitorScheduledThreadPool = Executors.newScheduledThreadPool(config.getMonitorMap().size());
    }

    @Override
    public void start() {
        for(Map.Entry<String, Monitor> entry : config.getMonitorMap().entrySet()) {
            // 将monitor提交给线程池调度执行
            monitorScheduledThreadPool.scheduleWithFixedDelay(()->{
                Monitor monitor = entry.getValue();
                MonitorResponse response = monitor.monitor();
                process(entry.getKey(), response);
            },5,config.getMonitorInterval(), TimeUnit.SECONDS);
            log.info("monitor {} has been scheduled", entry.getKey());
        }
    }

    @Override
    public void shutdown() {
        if(monitorScheduledThreadPool != null) {
            monitorScheduledThreadPool.shutdown();
            log.info("monitorScheduledThreadPool has shutdown");
        }
    }
}
