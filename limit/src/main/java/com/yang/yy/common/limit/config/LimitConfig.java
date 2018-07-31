package com.yang.yy.common.limit.config;

import lombok.Data;

@Data
public class LimitConfig {

    private LimitType limitType = LimitType.QPS;

    private LimitAlgorithm limitAlgorithm = LimitAlgorithm.FIXED_TIME;
    //资源标识符，保证唯一
    private String resource;

    private Long timeWindow = 1000L; //毫秒

    private Long limit = -1L;

    public enum LimitType {
        QPS, QPS_CLUSTER, TPS, TPS_CLUSTER
    }

    public enum LimitAlgorithm {
        FIXED_TIME, FLOW_TIME, TOKEN_BOCKET, LEAKY_BOCKET
    }
}
