package org.example.agent.core;

import lombok.Data;
import org.example.agent.monitor.api.Monitor;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class Config {

    /**
     * 应用名称
     */
    private String applicationName = "agent";

    /**
     * 服务端地址
     */
    private String serverHost = "http://localhost:7001";

    /**
     * netty工作线程数
     */
    private int eventLoopGroupWorkerNum = Runtime.getRuntime().availableProcessors();

    /**
     * 采集间隔
     */
    private int monitorInterval = 5;

    /**
     * monitor插件目录
     */
    private String monitorPath = "D:\\IdeaProjects\\agent\\plugin\\monitor";

    /**
     * 加载的monitor
     */
    private ConcurrentHashMap<String, Monitor>  monitorMap;
}
