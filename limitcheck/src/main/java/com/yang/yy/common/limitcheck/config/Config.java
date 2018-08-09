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

        limitFacade.limitConfigMap.put("test-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.TOKEN_BUCKET,"test-resource",1000L,5L,1000));

        limitFacade.limitConfigMap.put("user-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.FIXED_TIME,"user-resource",1000L,2L,1000));

        return limitFacade;
    }
}
