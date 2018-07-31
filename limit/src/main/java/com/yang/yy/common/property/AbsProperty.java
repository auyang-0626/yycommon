package com.yang.yy.common.property;

/**
 * @auther 杨光跃
 * @date 2018/5/27
 */
public abstract class AbsProperty<V> implements Property<V> {
    String name;
    V value;
    V defaultValue;
    Long changeTimestamp;

    public AbsProperty(String name, V defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        changeTimestamp = System.currentTimeMillis();
    }

    @Override
    public V getValue() {
        return value == null ? defaultValue : value;
    }

    @Override
    public V getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getChangeTimestamp() {
        return changeTimestamp;
    }

    @Override
    public void addCallback(Runnable callback) {

    }

    @Override
    public void removeAllCallbacks() {

    }
}
