package org.example.agent.monitor.node;


import org.example.agent.monitor.api.Monitor;
import org.example.agent.monitor.api.MonitorResponse;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * 监控主机信息
 */
public class NodeMonitor implements Monitor {

    @Override
    public MonitorResponse monitor() {
        Sigar sigar = new Sigar();
        ArrayList<MonitorResponse.Metric> metricList = new ArrayList<>();
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            // 获取内存信息
            Mem memory = sigar.getMem();
            metricList.add(new MonitorResponse.Metric(hostName,"memory_total", "总内存", memory.getTotal() / (1024 * 1024)+ " MB"));
            metricList.add(new MonitorResponse.Metric(hostName,"memory_used", "已用内存",memory.getUsed() / (1024 * 1024)+ " MB"));
            metricList.add(new MonitorResponse.Metric(hostName,"memory_free", "剩余内存",memory.getFree() / (1024 * 1024)+ " MB"));
            // 获取 CPU 信息
            CpuPerc cpuPerc = sigar.getCpuPerc();
            metricList.add(new MonitorResponse.Metric(hostName,"cpu_used","CPU使用率",CpuPerc.format(cpuPerc.getCombined())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MonitorResponse(System.currentTimeMillis(),metricList);
    }
}
