package com.sxlg.demo.model.core.support;

import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.sxlg.demo.model.core.command.GetRequestProcessor;
import com.sxlg.demo.model.core.command.PutRequestProcessor;
import com.sxlg.demo.model.core.log.DingDBStateMachine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DingDBServerSupport {
    private final static String DEFAULT_GROUP_ID = "DingDB";

    private String groupId;

    /**
     * raft分组
     */
    private RaftGroupService group;
    /**
     * raft节点
     */
    private Node node;
    /**
     * 状态机
     */
    private DingDBStateMachine stateMachine;

    private RpcServer rpcServer;

    private NodeOptions nodeOptions;

    private  PeerId serverId;

    private Configuration cluster;




    public DingDBServerSupport(String address) {
        this.stateMachine = new DingDBStateMachine();
        this.nodeOptions = new NodeOptions();
        this.cluster = new Configuration();
        PeerId serverId = new PeerId();

        if (!serverId.parse(address)) {
            throw new IllegalArgumentException("Fail to parse serverId: + serverIdStr");
        }
        this.serverId = serverId;
        this.rpcServer = RaftRpcServerFactory.createRaftRpcServer(serverId.getEndpoint());
    }

    public <T> DingDBServerSupport registerProcessor(RpcProcessor<T> rpcProcessor) {
        if (rpcProcessor == null)
            throw new NullPointerException();

        rpcServer.registerProcessor(rpcProcessor);

        return this;
    }

    public DingDBServerSupport groupId(String groupId) {

        this.groupId = groupId;

        return this;
    }

    public DingDBServerSupport serverId(String address) {


        return this;
    }

    public DingDBServerSupport nodeOptions(InitializeOptions initializeOptions){
        if (initializeOptions == null) {
            throw new NullPointerException();
        }
        initializeOptions.init(nodeOptions);

        return this;
    }
    public DingDBServerSupport cluster(String address){
        if (!cluster.parse(address)) {
            throw new IllegalArgumentException("Fail to parse initConf:" + address);
        }

        nodeOptions.setInitialConf(cluster);

        return this;
    }

    public RaftGroupService build() {
        if (groupId == null) {
            groupId = DEFAULT_GROUP_ID;
        }
        if (serverId == null)
            throw new NullPointerException();

        this.group = new RaftGroupService(groupId,serverId,nodeOptions,rpcServer);

        return group;
    }
}
