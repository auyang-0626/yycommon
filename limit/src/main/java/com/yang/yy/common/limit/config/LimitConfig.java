package com.yang.yy.common.limit.config;

import lombok.Data;

@Data
public class LimitConfig {

    private LimitType limitType = LimitType.QPS;

    private LimitAlgorithm limitAlgorithm = LimitAlgorithm.FIXED_TIME;
    //资源标识符，保证唯一
    private String resource;

    private Long timeWindow = 1000L; //毫秒

    private Long capacity = -1L;

    public LimitConfig(LimitType limitType, LimitAlgorithm limitAlgorithm, String resource, Long timeWindow, Long capacity) {
        this.limitType = limitType;
        this.limitAlgorithm = limitAlgorithm;
        this.resource = resource;
        this.timeWindow = timeWindow;
        this.capacity = capacity;
    }
}
