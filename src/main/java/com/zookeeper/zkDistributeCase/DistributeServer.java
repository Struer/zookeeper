package com.zookeeper.zkDistributeCase;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Random;

public class DistributeServer {

    private ZooKeeper zkClient ;
    private int sessinTimeOut = 2000; // 2s超时
    private String parentNode = "/servers";

    /**
     * 1获取连接
     * @throws IOException
     */
    public void getConnection() throws IOException {
        zkClient = new ZooKeeper("node02:2181,node03:2181,node04:2181", sessinTimeOut, new Watcher() {

            public void process(WatchedEvent event) {

            }
        });
    }

    /**
     * 2注册（创建节点）
     * @throws Exception
     */
    public void regist(String hostname) throws KeeperException, InterruptedException {
        String createNode = zkClient.create(parentNode + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online,nodename: " + createNode);
    }

    /**
     * 3具体业务逻辑
     * @throws Exception
     */
    public void bussiness() throws InterruptedException {
        System.out.println("DistributeServer 具体业务逻辑");

        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception{
        // 1获取连接
        DistributeServer zkServer = new DistributeServer();
        zkServer.getConnection();

        // 2注册（创建节点）
        Random random = new Random();
        zkServer.regist("server" + random.nextInt(100));

        // 3具体业务逻辑
        zkServer.bussiness();

    }


}
