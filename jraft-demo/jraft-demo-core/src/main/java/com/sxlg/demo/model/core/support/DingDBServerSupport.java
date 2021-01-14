package com.sxlg.demo.model.core.support;

import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.sxlg.demo.model.core.log.DingDBStateMachine;

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

    public DingDBServerSupport() {
        this.stateMachine = new DingDBStateMachine();
        this.nodeOptions = new NodeOptions();
        if (nodeOptions.getFsm() == null)
            this.nodeOptions.setFsm(stateMachine);

    }

    public <T> DingDBServerSupport registerProcessor(RpcProcessor<T> rpcProcessor) {
        if (rpcProcessor == null)
            throw new NullPointerException();
        rpcServer.registerProcessor(rpcProcessor);
        return this;
    }

    private DingDBServerSupport groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    private DingDBServerSupport serverId(PeerId peerId) {
        this.serverId = peerId;
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
    //TODO nodeOptions参数

}
