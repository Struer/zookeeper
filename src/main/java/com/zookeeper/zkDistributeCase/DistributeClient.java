package com.zookeeper.zkDistributeCase;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    private ZooKeeper zkClient;
    private int sessinTimeOut = 2000; // 2s超时
    private String parentNode = "/servers";

    /**
     * 1获取连接
     *
     * @throws IOException
     */
    public void getConnection() throws Exception {
        zkClient = new ZooKeeper("node02:2181,node03:2181,node04:2181", sessinTimeOut, new Watcher() {

            public void process(WatchedEvent event) {
                // 监听发生后触发的事件
                System.out.println("监听事件：" + event.getType() + "--" + event.getPath());
                try {
                    // 保证能循环执行，持续监听
                    getServers();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 2监听节点变化
     *
     * @throws Exception
     */
    public void getServers() throws Exception {
        //获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zkClient.getChildren(parentNode, true);
        ArrayList<String> servers = new ArrayList<String>();

        // 获取所有子节点信息
        for (String child: children){
            byte[] data = zkClient.getData(parentNode + "/" + child, false, null);
            //保存信息
            servers.add(new String(data));
        }
        System.out.println(servers);
    }

    /**
     * 3具体业务逻辑
     * @throws Exception
     */
    public void bussiness() throws InterruptedException {
        System.out.println("DistributeClient 具体业务逻辑");

        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 1获取连接
        DistributeClient zkClient = new DistributeClient();
        zkClient.getConnection();

        // 2监听节点变化
        zkClient.getServers();

        // 3具体业务逻辑
        zkClient.bussiness();
    }
}
