package com.sxlg.demo.model.core.support;

import com.alipay.sofa.jraft.option.NodeOptions;

public interface InitializeOptions {
    void init(NodeOptions nodeOptions);
}
