package com.yang.yy.common.limit.status;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Data
public class FixedQpsStatus {

    private Long timestamp = System.currentTimeMillis();
    private AtomicLong acquireCount = new AtomicLong(0);



    // todo 这个需要做方法级别的同步？后面看看能否优化
    public synchronized long incrementAndGet(long timeWindow) {
        long currTimestamp = System.currentTimeMillis();

        // 只同步这一块会有问题吗？
        if ((timestamp + timeWindow) < currTimestamp) {
            timestamp = currTimestamp;
            acquireCount.set(0);
        }
        return acquireCount.incrementAndGet();
    }
}
