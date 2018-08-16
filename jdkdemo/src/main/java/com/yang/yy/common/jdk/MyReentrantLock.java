package com.yang.yy.common.jdk;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyReentrantLock implements Lock,Serializable {

    private Sync sync;

    public MyReentrantLock(Sync sync) {
        this.sync = sync;
    }

    public  class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean isHeldExclusively() {
            return Thread.currentThread() == getExclusiveOwnerThread();
        }

        @Override
        protected boolean tryAcquire(int arg) {
            int status = getState();
            if (status == 0 && compareAndSetState(0,arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else if (isHeldExclusively()){
                int nextc = status + arg;
                if (nextc <0 ){
                    throw new Error("Maximum lock count exceeded");
                }
                // 当前线程不可因有锁竞争
                setState(nextc);
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {

            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();

            int nextc = getState() - arg;
            setState(nextc);
            if (nextc == 0) {
                setExclusiveOwnerThread(null);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
