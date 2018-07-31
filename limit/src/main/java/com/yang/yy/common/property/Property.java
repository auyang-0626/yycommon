package com.yang.yy.common.property;

/**
 * Created by yang on 2018/5/27.
 */
public interface Property<T> {

    T getValue();

    T getDefaultValue();

    String getName();

    long getChangeTimestamp();

    void addCallback(Runnable callback);

    void removeAllCallbacks();
}
