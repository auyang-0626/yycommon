package com.yang.yy.common.limit;

import com.yang.yy.common.limit.exception.LimitExecption;

public interface Limit {

    boolean limit(String resource) throws LimitExecption;
}
