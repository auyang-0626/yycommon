package com.yang.yy.common.limit;

import com.yang.yy.common.limit.config.LimitConfig;
import com.yang.yy.common.limit.exception.ConfigInValidExecption;
import com.yang.yy.common.limit.exception.ResourceNotExistExecption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class LimitFacade {

    //资源的限流配置信息 key resource, value config
    // 每个资源的参数是唯一的
    public  Map<String,LimitConfig> limitConfigMap = new ConcurrentHashMap<>(32);



    public boolean acquire(String resource, long waitTime) throws ResourceNotExistExecption, ConfigInValidExecption {
        LimitConfig limitConfig = limitConfigMap.get(resource);
        if (limitConfig == null ){
            throw new ResourceNotExistExecption("指定的资源不存在！");
        }
        return limitConfig.acquire(waitTime);

    }

    /**
     * 大多出情况用户不关心异常
     * @param resource 限流的资源唯一限定符
     * @return
     */
    public boolean acquireIgnoreException(String resource,long waitTime) {
        try {
            return acquire(resource,waitTime);
        } catch (ResourceNotExistExecption | ConfigInValidExecption  e) {
            // 预期的异常，不用core
            log.error(e.getMessage());
        }  catch (Exception e){
            log.error("未知异常",e);
        }
        return true;
    }

    public boolean acquireIgnoreException(String resource) {
       return acquireIgnoreException(resource,0);
    }

}
