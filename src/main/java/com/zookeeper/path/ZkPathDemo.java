package com.zookeeper.path;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkPathDemo {

    ZooKeeper zkClient ;
    private int sessinTimeOut = 2000; // 2s超时


    /**
     * 创建客户端
     * @throws IOException
     */
    @Before
    public void initZk() throws IOException {
        zkClient = new ZooKeeper("node02:2181,node03:2181,node04:2181", sessinTimeOut, new Watcher() {

            public void process(WatchedEvent event) {
                // 监听发生后触发的事件
                System.out.println("监听事件：" + event.getType() + "--" + event.getPath());

                // 监听一次之后继续监听
                try {
                    zkClient.exists("/idea", true);
                    zkClient.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建子节点
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        // 数据的增删改查
        // 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：创建节点后节点权限 ；参数4：节点的类型
        String nodeCreated = zkClient.create("/idea", "hello zk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    /**
     * 获取子节点
     * @throws Exception
     */
    @Test
    public void getChildren() throws Exception {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断znode是否存在
     * @throws Exception
     */
    @Test
    public void exist() throws Exception {
        // 第二个参数为true表示开启监听事件，
        Stat stat = zkClient.exists("/idea", false);
//        Stat stat = zkClient.exists("/idea", true);
        System.out.println(stat == null ? "not exist" : "exist");

        Thread.sleep(Long.MAX_VALUE);
    }

}
