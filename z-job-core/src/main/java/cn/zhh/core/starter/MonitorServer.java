package cn.zhh.core.starter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 17:43
 */
@Slf4j
public class MonitorServer extends Thread {

    private String ip;
    private int port;

    private MonitorServer() {
        super("MonitorServer-T");
    }

    public static MonitorServer newInstance(String ip, int port) {
        MonitorServer monitorServer = new MonitorServer();
        monitorServer.ip = ip;
        monitorServer.port = port;
        return monitorServer;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            // 添加编码、解码、业务处理的handler
                            channel.pipeline()
                                    .addLast(new Encoder())
                                    .addLast(new Decoder())
                                    .addLast(new JobProcessor());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(ip, port).sync();
            log.info("Netty-MonitorServer started on {}:{}", ip, port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
