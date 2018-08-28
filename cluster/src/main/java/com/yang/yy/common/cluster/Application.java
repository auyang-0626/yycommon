package com.yang.yy.common.cluster;

import com.yang.yy.common.cluster.master.Master;

public class Application {

    public static void main(String[] args) {

        new Master().run();
    }
}
