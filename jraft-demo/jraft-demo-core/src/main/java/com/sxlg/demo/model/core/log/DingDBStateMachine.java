package com.sxlg.demo.model.core.log;

import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.core.StateMachineAdapter;

public class DingDBStateMachine extends StateMachineAdapter {


    /**
     *
     * @param iter iterator of states
     *
     *
     * 最核心的方法，应用任务列表到状态机，任务将按照提交顺序应用。请注意，当这个方法返回的时候，
     * 我们就认为这一批任务都已经成功应用到状态机上，如果你没有完全应用（比如错误、异常），将会被当做一个 critical 级别的错误，
     * 报告给状态机的 onError 方法，错误类型为 ERROR_TYPE_STATE_MACHINE
     */
    @Override
    public void onApply(Iterator iter) {

    }
}
