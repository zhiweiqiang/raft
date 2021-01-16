package com.sxlg.demo.model.core.command;

import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;

public class PutRequestProcessor implements RpcProcessor<PutRequest> {
    @Override
    public void handleRequest(RpcContext rpcCtx, PutRequest request) {

    }

    @Override
    public String interest() {
        return  getClass().getName();
    }
}
