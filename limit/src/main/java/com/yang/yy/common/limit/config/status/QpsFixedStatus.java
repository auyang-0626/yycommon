package com.yang.yy.common.limit.config.status;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.NANOSECONDS;


@Slf4j
@Data
public class QpsFixedStatus extends AbsStatus {



    public QpsFixedStatus(Long timeWindow, Long capacity) {
        super(timeWindow, capacity);
    }



    @Override
    protected void refreshPermitAndTimestamp() {
        long currTimestamp = System.currentTimeMillis();
        if (currTimestamp > (timestamp + timeWindow)) {
            long cycle = (currTimestamp - timestamp) / timeWindow;
            permit += capacity * cycle;
            timestamp += timestamp * cycle;
            if (permit > 0) {
                permit = Math.min(permit, capacity);
            }
        }
    }

    // 计算最小睡眠时间
    @Override
    protected long calSleepTime() {
        if (permit > 0) {
            return 0;
        }
        return Double.doubleToLongBits(-permit + 1) / capacity * timeWindow + (timestamp + timeWindow - System.currentTimeMillis() );
    }

}
