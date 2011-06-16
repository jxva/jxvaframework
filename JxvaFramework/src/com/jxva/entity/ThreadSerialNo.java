package com.jxva.entity;

public abstract class ThreadSerialNo{

    private static volatile int nextNum = 0;
    
    private static ThreadLocal<Integer> threadNo = new ThreadLocal<Integer>() {
        public synchronized Integer initialValue() {
            return nextNum++;
        }
    };

    public static int next() {
        return threadNo.get();
    }
}