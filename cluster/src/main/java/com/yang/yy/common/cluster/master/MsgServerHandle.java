package com.yang.yy.common.cluster.master;

import com.yang.yy.common.cluster.msg.BaseMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MsgServerHandle extends SimpleChannelInboundHandler<BaseMsg> {

    private Map<String,Channel> slaveMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) {

        slaveMap.putIfAbsent(msg.getClientId(),ctx.channel());

       // this.getClass().getm

    }
}
