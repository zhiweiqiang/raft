package com.sxlg.demo.model.core.command;

import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;

public class GetRequestProcessor implements RpcProcessor<GetRequest> {
    @Override
    public void handleRequest(RpcContext rpcCtx, GetRequest request) {

    }

    @Override
    public String interest() {
        return getClass().getName();
    }
}
