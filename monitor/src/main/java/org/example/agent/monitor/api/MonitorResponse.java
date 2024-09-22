package org.example.agent.monitor.api;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
public class MonitorResponse {

    /**
     * 采集时间
     */
    private Long timestamp;

    /**
     * 指标列表
     */
    private List<Metric> metricList;


    @ToString
    @AllArgsConstructor
    public static class Metric {

        /**
         * 实例名称
         */
        private String instance;

        /**
         * 指标名称
         */
        private String name;

        /**
         * 指标描述
         */
        private String describe;

        /**
         * 指标值
         */
        private Object value;
    }
}
