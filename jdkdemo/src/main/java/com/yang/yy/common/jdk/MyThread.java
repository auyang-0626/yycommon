package com.yang.yy.common.jdk;

public class MyThread {

    public static void main(String[] args){


        System.out.println(Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println("hhehe");

    }
}
