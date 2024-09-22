package org.example.agent.core.processor;

import org.example.agent.core.LifeCycle;


public abstract class processor implements LifeCycle {


    /**
     * 将结果上报给服务端
     * @param monitorName 是哪个monitor采集的
     * @param result 采集的结果
     */
    void process(String monitorName, Object result) {
        // todo 将结果上报给服务端
        System.out.println(result.toString());
    }

}
