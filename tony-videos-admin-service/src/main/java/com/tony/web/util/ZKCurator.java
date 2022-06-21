package com.tony.web.util;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKCurator {
    //通过bean自动创建实例
    private CuratorFramework client = null;

    final static Logger log = LoggerFactory.getLogger(ZKCurator.class);

    public ZKCurator(CuratorFramework client){
        this.client = client;
    }

    public void init(){
        client = client.usingNamespace("admin");

        try {
            //判断在admin命名空间下是否有bgm节点，/admin/bgm
            if (client.checkExists().forPath("/bgm") == null) {
                /**
                 * 对于zk来讲，有两种类型的节点：
                 * 持久节点：当客户端断开连接时，znode 不会被自动删除，
                 * 临时节点：znode 将在客户端断开连接时被删除，
                 */
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT) //持久化节点
                        .withACL(Ids.OPEN_ACL_UNSAFE) //acl:匿名权限，完全开发
                        .forPath("/bgm");
                log.info("zookeeper初始化成功");


                log.info("zookeeper服务器状态：{}",client.isStarted());
            }

        } catch (Exception e) {
            log.error("zookeeper客户端连接、初始化错误...");
            e.printStackTrace();
        }

    }

    /**
     * @Descrption: 增加或刪除bgm,向zookeeper创建子节点，供小程序监听
     * @param bgmId
     * @param operaObj
     */
    public void sendBgmOperator(String bgmId, String operaObj){

        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(Ids.OPEN_ACL_UNSAFE)
                    .forPath("/bgm/" + bgmId, operaObj.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
