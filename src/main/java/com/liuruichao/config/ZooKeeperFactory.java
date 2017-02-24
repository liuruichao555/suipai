package com.liuruichao.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * ZooKeeperFactory
 *
 * @author liuruichao
 * @date 15/11/27 上午10:56
 */
public final class ZooKeeperFactory {
    public static CuratorFramework newInstance(int baseSleepTimems, int maxRetries, String connStr) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimems, maxRetries);
        CuratorFramework client =
                CuratorFrameworkFactory.newClient(connStr, retryPolicy);
        client.start();
        return client;
    }
}