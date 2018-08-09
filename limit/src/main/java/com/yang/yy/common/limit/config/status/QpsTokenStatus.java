package com.yang.yy.common.limit.config.status;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class QpsTokenStatus extends AbsStatus{

    private double rate;// 产生令牌的速率，毫秒


    public QpsTokenStatus(Long timeWindow, Long capacity) {
        super(timeWindow,capacity);
        rate = capacity * 1.0 /timeWindow;
    }

    @Override
    protected long calSleepTime() {
        if (permit > 0) {
            return 0;
        }

        return (long) ((-permit + 1) / rate);
    }

    @Override
    protected void refreshPermitAndTimestamp() {
        long currTimestamp = System.currentTimeMillis();
        permit = Math.min(permit + (currTimestamp - timestamp) * rate, capacity);
        timestamp = currTimestamp;
    }

}
