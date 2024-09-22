package org.example.agent.core;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;

/**
 * Hello world!
 *
 */
@Slf4j
public class Bootstrap
{
    public static void main( String[] args ) throws MalformedURLException {
        // 加载配置
        Config config = ConfigLoader.getInstance().load(args);

        // 插件初始化，保存在config对象中
        PluginLoader.getInstance().load(config);

        log.info("agent is starting...");
        Container container = new Container(config);
        container.start();

        // 优雅关闭
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                container.shutdown();
                log.info("agent has shutdown");
            }
        });

        log.info("agent has started");
    }
}
