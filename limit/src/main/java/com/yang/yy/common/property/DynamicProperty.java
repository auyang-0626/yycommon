package com.yang.yy.common.property;

/**
 * @auther 杨光跃
 * @date 2018/5/27
 */
public class DynamicProperty<V> extends AbsProperty<V> {

    public DynamicProperty(String name, V defaultValue) {
        super(name, defaultValue);
    }
}
