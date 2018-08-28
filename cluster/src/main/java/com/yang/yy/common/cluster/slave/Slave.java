package com.yang.yy.common.cluster.slave;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Slave {

    public void run() throws InterruptedException {

        EventLoopGroup worker = new NioEventLoopGroup(3);
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new OwnClientChannelInitializer());
            ChannelFuture future = b.connect("127.0.0.1",8888).sync();
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }

    }
}
