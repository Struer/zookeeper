package com.zookeeper.distributed_lock;

/**
 *
 */
public interface DistributedLock {

    void lock() throws Exception;
    Boolean tryLock() throws Exception;
    Boolean tryLock(long millisecond) throws Exception;
    void unlock() throws Exception;
}
