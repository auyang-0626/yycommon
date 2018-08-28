package com.yang.yy.common.cluster.msg;

import java.io.Serializable;

public abstract class BaseMsg implements Serializable {

    private static final String defId = System.nanoTime()+ "";
    private static final long serialVersionUID = 1L;
    private MsgType type;
    //必须唯一，否者会出现channel调用混乱
    private String clientId;

    //初始化客户端id
    public BaseMsg(MsgType type) {
        this.type = type;
        this.clientId = defId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

}
