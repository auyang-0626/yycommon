package com.yang.yy.common.limitcheck.config;

import com.yang.yy.common.limit.LimitFacade;
import com.yang.yy.common.limit.config.LimitAlgorithm;
import com.yang.yy.common.limit.config.LimitConfig;
import com.yang.yy.common.limit.config.LimitType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public LimitFacade getLimitFacade(){
        LimitFacade limitFacade = new LimitFacade();
        //   参数含义  QPS限流，令牌桶算法，资源，限流窗口，限流窗口内最多允许通过的流量
        limitFacade.limitConfigMap.put("test-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.TOKEN_BUCKET,"test-resource",1000L,100L));
        limitFacade.limitConfigMap.put("user-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.FIXED_TIME,"user-resource",1000L,20L));
        return limitFacade;
    }
}
