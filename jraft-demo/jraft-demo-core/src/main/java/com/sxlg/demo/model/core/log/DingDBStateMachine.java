package com.sxlg.demo.model.core.log;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.alipay.sofa.jraft.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class DingDBStateMachine extends StateMachineAdapter {

    /**
     * Leader term
     */
    private final AtomicLong leaderTerm = new AtomicLong(-1);
    /**
     * @param iter iterator of states
     *             <p>
     *             <p>
     *             最核心的方法，应用任务列表到状态机，任务将按照提交顺序应用。请注意，当这个方法返回的时候，
     *             我们就认为这一批任务都已经成功应用到状态机上，如果你没有完全应用（比如错误、异常），将会被当做一个 critical 级别的错误，
     *             报告给状态机的 onError 方法，错误类型为 ERROR_TYPE_STATE_MACHINE
     */
    @Override
    public void onApply(Iterator iter) {

    }

    @Override
    public boolean onSnapshotLoad(final SnapshotReader reader) {
        if (isLeader()) {
            return false;
        }
        return true;
    }
    @Override
    public void onSnapshotSave(SnapshotWriter writer, Closure done) {
        done.run(Status.OK());
    }

    @Override
    public void onLeaderStart(final long term) {
        this.leaderTerm.set(term);
        super.onLeaderStart(term);

    }

    @Override
    public void onLeaderStop(final Status status) {
        this.leaderTerm.set(-1);
        super.onLeaderStop(status);
    }

    public boolean isLeader() {
        return this.leaderTerm.get() > 0;
    }

}
