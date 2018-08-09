package com.yang.yy.common.limit.config;

import com.yang.yy.common.limit.config.status.QpsFixedStatus;
import com.yang.yy.common.limit.config.status.QpsTokenStatus;
import com.yang.yy.common.limit.config.status.Status;
import com.yang.yy.common.limit.exception.ConfigInValidExecption;
import lombok.Data;

@Data
public class LimitConfig {

    private LimitType limitType = LimitType.QPS;

    private LimitAlgorithm limitAlgorithm = LimitAlgorithm.FIXED_TIME;
    //资源标识符，保证唯一
    private String resource;

    private Long timeWindow = 1000L; //毫秒

    private Long capacity = -1L;

    // todo AtomicReference 使用这个修饰可以去掉同步吗 ？
    private Status status;


    public LimitConfig(LimitType limitType, LimitAlgorithm limitAlgorithm, String resource, Long timeWindow, Long capacity) {
        this.limitType = limitType;
        this.limitAlgorithm = limitAlgorithm;
        this.resource = resource;
        this.timeWindow = timeWindow;
        this.capacity = capacity;

    }

    //阻塞等待时间，等于0 不阻塞，小于0无限阻塞，大于零标识阻塞时间，单位毫秒
    public boolean acquire(long waitTime) throws ConfigInValidExecption {
        if (status == null) {
            synchronized (this) {
                if (status == null) {
                    switch (this.limitType) {
                        case QPS:
                            if (this.limitAlgorithm == LimitAlgorithm.FIXED_TIME) {
                                this.status = new QpsFixedStatus(timeWindow, capacity);
                            }
                            if (this.limitAlgorithm == LimitAlgorithm.TOKEN_BUCKET) {
                                this.status = new QpsTokenStatus(timeWindow, capacity);
                            }
                            break;
                        default:
                            throw new ConfigInValidExecption("无效的配置");
                    }
                }
            }
        }
        return status.acquire(waitTime);
    }

}
