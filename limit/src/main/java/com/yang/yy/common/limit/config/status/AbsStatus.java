package com.yang.yy.common.limit.config.status;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbsStatus implements Status {

    Long timeWindow; //毫秒

    Long capacity;

    long timestamp = 0;

    double permit;


    public AbsStatus(Long timeWindow, Long capacity) {
        this.timeWindow = timeWindow;
        this.capacity = capacity;
    }

    @Override
    public boolean acquire(long waitTime) {
        long sleepTime = 0;
        synchronized (this) {
            refreshPermitAndTimestamp();

            if (permit > 0) {
                permit--;
                return true;
            } else if (waitTime == 0) {
                //没有许可并且不愿意等待
                return false;
            } else {
                // 计算最小睡眠时间
                sleepTime = calSleepTime();

                //等待一段时间
                if (waitTime > 0 && sleepTime > waitTime) {
                    return false;
                }
                permit--;
            }
            log.info(" 当前的令牌数：{}", permit);
        }
        if (sleepTime > 0) {
            sleepUninterruptibly(sleepTime, TimeUnit.MILLISECONDS);
        }
        return true;
    }

    protected abstract long calSleepTime();

    protected abstract void refreshPermitAndTimestamp();
}
