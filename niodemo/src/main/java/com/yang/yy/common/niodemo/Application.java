package com.yang.yy.common.niodemo;

public class Application {

    public static void main(String[] args) {
        new Server(9999,2).start();
    }
}
