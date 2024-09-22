package org.example.agent.core.netty;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.example.agent.common.utils.RemotingUtil;
import org.example.agent.core.Config;
import org.example.agent.core.LifeCycle;
import org.example.agent.core.helper.AsyncHttpHelper;

import java.io.IOException;

@Slf4j
public class NettyHttpClient implements LifeCycle {

    private final Config config;

    private EventLoopGroup eventLoopGroupWorker;

    private AsyncHttpClient asyncHttpClient;

    public NettyHttpClient(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        if(useEpoll()) {
            this.eventLoopGroupWorker = new EpollEventLoopGroup(config.getEventLoopGroupWorkerNum(),
                    new DefaultThreadFactory("netty-worker-epoll"));
            log.info("epoll is used");
        }else {
            this.eventLoopGroupWorker = new NioEventLoopGroup(config.getEventLoopGroupWorkerNum(),
                    new DefaultThreadFactory("netty-worker-nio"));
            log.info("nio is used");
        }
        final DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder()
                .setEventLoopGroup(eventLoopGroupWorker)
                .setAllocator(PooledByteBufAllocator.DEFAULT) // 池化byteBuf分配器， 提升性能
                .setCompressionEnforced(true);
        this.asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
    }

    @Override
    public void start() {
        AsyncHttpHelper.getInstance().initialized(asyncHttpClient);
    }

    @Override
    public void shutdown() {
        if(asyncHttpClient != null) {
            try{
                asyncHttpClient.close();
                log.debug("asyncHttpClient has closed");
            }catch (IOException e) {
                log.error("NettyHttpClient shutdown error", e);
            }
        }
        if(eventLoopGroupWorker != null) {
            eventLoopGroupWorker.shutdownGracefully();
            log.debug("eventLoopGroupWorker has shutdown");
        }
    }


    /**
     * 当前环境是否可以使用epoll模式
     * @return
     */
    public boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }
}
