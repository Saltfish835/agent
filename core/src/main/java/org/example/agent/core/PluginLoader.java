package org.example.agent.core;

import lombok.extern.slf4j.Slf4j;
import org.example.agent.monitor.api.Monitor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PluginLoader {

    private static final PluginLoader INSTANCE = new PluginLoader();

    private PluginLoader() {
    }

    public static PluginLoader getInstance() {
        return INSTANCE;
    }


    /**
     * 加载所有插件
     * @param config
     */
    public void load(Config config) throws MalformedURLException {
        // 加载并保存所有monitor
        config.setMonitorMap(loadMonitor(config));
    }


    /**
     * 加载monitor
     * @return
     */
    private ConcurrentHashMap<String, Monitor> loadMonitor(Config config) throws MalformedURLException {
        ConcurrentHashMap<String, Monitor>  monitorMap = new ConcurrentHashMap<>();
        URL[] urls = null;
        File dir = new File(config.getMonitorPath());
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            urls = new URL[files.length];
            for (int i = 0; i < files.length; i++) {
                urls[i] = files[i].toURL();
            }
        }
        ServiceLoader<Monitor> monitors = ServiceLoader.load(Monitor.class, new URLClassLoader(urls));
        for(Monitor monitor : monitors) {
            String monitorName = monitor.getClass().getName();
            monitorMap.put(monitorName, monitor);
            log.info("load monitor success: {}", monitorName);
        }
        return monitorMap;
    }


}
