package com.yang.yy.common.limit.impl;

import com.yang.yy.common.limit.Limit;
import com.yang.yy.common.limit.exception.LimitExecption;

public class QpsLimit implements Limit {


    public boolean limit(String resource) throws LimitExecption {
        return false;
    }
}
