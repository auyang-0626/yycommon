package com.yang.yy.common.limit;

import com.yang.yy.common.limit.config.LimitAlgorithm;
import com.yang.yy.common.limit.config.LimitConfig;
import com.yang.yy.common.limit.exception.ConfigInValidExecption;
import com.yang.yy.common.limit.exception.ResourceNotExistExecption;
import com.yang.yy.common.limit.status.FixedQpsStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class LimitFacade {

    //资源的限流配置信息 key resource, value config
    public  Map<String,LimitConfig> limitConfigMap = new ConcurrentHashMap<>(32);
    public  Map<String,FixedQpsStatus> fixedQpsRecordMap = new ConcurrentHashMap<>();

    public boolean acquire(String resource) throws ResourceNotExistExecption, ConfigInValidExecption {
        LimitConfig limitConfig = limitConfigMap.get(resource);
        if (limitConfig == null ){
            throw new ResourceNotExistExecption("指定的资源不存在！");
        }
        switch (limitConfig.getLimitType()){
            case QPS:
                return qpsLimit(limitConfig);
            default:
                throw new ConfigInValidExecption("无效的配置");
        }
    }

    /**
     * 大多出情况用户不关心异常
     * @param resource 限流的资源唯一限定符
     * @return
     */
    public boolean acquirIgnoreException(String resource) {
        try {
            return acquire(resource);
        } catch (ResourceNotExistExecption | ConfigInValidExecption  e) {
            // 预期的异常，不用core
            log.error(e.getMessage());
        }  catch (Exception e){
            log.error("未知异常",e);
        }
        return true;
    }

    /**
     * 单机QPS请求限流
     * @param limitConfig 限流的配置
     * @return
     */
    private boolean qpsLimit(LimitConfig limitConfig) {


        // 固定时间窗口的限流
        if (limitConfig.getLimitAlgorithm() == LimitAlgorithm.FIXED_TIME){
            return limitConfig.getCapacity() > fixedQpsRecordMap.computeIfAbsent(limitConfig.getResource(),o->new FixedQpsStatus()).incrementAndGet(limitConfig.getTimeWindow());
        }
        return false;
    }
}
