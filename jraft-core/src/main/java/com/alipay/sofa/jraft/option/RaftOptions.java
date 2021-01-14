/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.jraft.option;

import com.alipay.sofa.jraft.util.Copiable;
import com.alipay.sofa.jraft.util.RpcFactoryHelper;

/**
 * Raft options.
 *
 * @author boyan (boyan@alibaba-inc.com)
 *
 * 2018-Apr-03 4:38:40 PM
 */
public class RaftOptions implements Copiable<RaftOptions> {
    /** 节点之间每次文件 RPC (snapshot拷贝）请求的最大大小，默认为 128 K */
    private int            maxByteCountPerRpc                   = 128 * 1024;
    /** 是否在拷贝文件中检查文件空洞，暂时未实现 */
    private boolean        fileCheckHole                        = false;
    /** 从 leader 往 follower 发送的最大日志个数，默认 1024 */
    private int            maxEntriesSize                       = 1024;
    /**从 leader 往 follower 发送日志的最大 body 大小，默认 512K*/
    private int            maxBodySize                          = 512 * 1024;
    /** 日志存储缓冲区最大大小，默认256K */
    private int            maxAppendBufferSize                  = 256 * 1024;
    /** 选举定时器间隔会在指定时间之外随机的最大范围，默认1秒*/
    private int            maxElectionDelayMs                   = 1000;
    /**
     * 指定选举超时时间和心跳间隔时间之间的比值。心跳间隔等于
     * electionTimeoutMs/electionHeartbeatFactor，默认10分之一。
     */
    private int            electionHeartbeatFactor              = 10;
    /** 向 leader 提交的任务累积一个批次刷入日志存储的最大批次大小，默认 32 个任务*/
    private int            applyBatch                           = 32;
    /** 写入日志、元信息的时候必要的时候调用 fsync，通常都应该为 true*/
    private boolean        sync                                 = true;
    /**
     * 写入 snapshot/raft 元信息是否调用 fsync，默认为 false，
     * 在 sync 为 true 的情况下，优选尊重 sync
     */
    private boolean        syncMeta                             = false;
    /**
     * 内部 disruptor buffer 大小，如果是写入吞吐量较高的应用，需要适当调高该值，默认 16384
     */
    private int            disruptorBufferSize                  = 16384;
    /** 是否启用复制的 pipeline 请求优化，默认打开*/
    private boolean        replicatorPipeline                   = true;
    /** 在启用 pipeline 请求情况下，最大 in-flight 请求数，默认256*/
    private int            maxReplicatorInflightMsgs            = 256;
    /** 是否启用 LogEntry checksum*/
    private boolean        enableLogEntryChecksum               = false;

    /** ReadIndex 请求级别，默认 ReadOnlySafe，具体含义参见线性一致读章节*/
    private ReadOnlyOption readOnlyOptions                      = ReadOnlyOption.ReadOnlySafe;

    /** Statistics to analyze the performance of db */
    private boolean        openStatistics                       = true;

    /**
     * The maximum timeout in seconds to wait when publishing events into disruptor, default is 10 seconds.
     * If the timeout happens, it may halt the node.
     * */
    private int            disruptorPublishEventWaitTimeoutSecs = 10;
    /**
     * Candidate steps down when election reaching timeout, default is true(enabled).
     * @since 1.3.0
     */
    private boolean        stepDownWhenVoteTimedout             = true;

    public boolean isStepDownWhenVoteTimedout() {
        return this.stepDownWhenVoteTimedout;
    }

    public void setStepDownWhenVoteTimedout(final boolean stepDownWhenVoteTimeout) {
        this.stepDownWhenVoteTimedout = stepDownWhenVoteTimeout;
    }

    public int getDisruptorPublishEventWaitTimeoutSecs() {
        return this.disruptorPublishEventWaitTimeoutSecs;
    }

    public void setDisruptorPublishEventWaitTimeoutSecs(final int disruptorPublishEventWaitTimeoutSecs) {
        this.disruptorPublishEventWaitTimeoutSecs = disruptorPublishEventWaitTimeoutSecs;
    }

    public boolean isEnableLogEntryChecksum() {
        return this.enableLogEntryChecksum;
    }

    public void setEnableLogEntryChecksum(final boolean enableLogEntryChecksumValidation) {
        this.enableLogEntryChecksum = enableLogEntryChecksumValidation;
    }

    public ReadOnlyOption getReadOnlyOptions() {
        return this.readOnlyOptions;
    }

    public void setReadOnlyOptions(final ReadOnlyOption readOnlyOptions) {
        this.readOnlyOptions = readOnlyOptions;
    }

    public boolean isReplicatorPipeline() {
        return this.replicatorPipeline && RpcFactoryHelper.rpcFactory().isReplicatorPipelineEnabled();
    }

    public void setReplicatorPipeline(final boolean replicatorPipeline) {
        this.replicatorPipeline = replicatorPipeline;
    }

    public int getMaxReplicatorInflightMsgs() {
        return this.maxReplicatorInflightMsgs;
    }

    public void setMaxReplicatorInflightMsgs(final int maxReplicatorPiplelinePendingResponses) {
        this.maxReplicatorInflightMsgs = maxReplicatorPiplelinePendingResponses;
    }

    public int getDisruptorBufferSize() {
        return this.disruptorBufferSize;
    }

    public void setDisruptorBufferSize(final int disruptorBufferSize) {
        this.disruptorBufferSize = disruptorBufferSize;
    }

    public int getMaxByteCountPerRpc() {
        return this.maxByteCountPerRpc;
    }

    public void setMaxByteCountPerRpc(final int maxByteCountPerRpc) {
        this.maxByteCountPerRpc = maxByteCountPerRpc;
    }

    public boolean isFileCheckHole() {
        return this.fileCheckHole;
    }

    public void setFileCheckHole(final boolean fileCheckHole) {
        this.fileCheckHole = fileCheckHole;
    }

    public int getMaxEntriesSize() {
        return this.maxEntriesSize;
    }

    public void setMaxEntriesSize(final int maxEntriesSize) {
        this.maxEntriesSize = maxEntriesSize;
    }

    public int getMaxBodySize() {
        return this.maxBodySize;
    }

    public void setMaxBodySize(final int maxBodySize) {
        this.maxBodySize = maxBodySize;
    }

    public int getMaxAppendBufferSize() {
        return this.maxAppendBufferSize;
    }

    public void setMaxAppendBufferSize(final int maxAppendBufferSize) {
        this.maxAppendBufferSize = maxAppendBufferSize;
    }

    public int getMaxElectionDelayMs() {
        return this.maxElectionDelayMs;
    }

    public void setMaxElectionDelayMs(final int maxElectionDelayMs) {
        this.maxElectionDelayMs = maxElectionDelayMs;
    }

    public int getElectionHeartbeatFactor() {
        return this.electionHeartbeatFactor;
    }

    public void setElectionHeartbeatFactor(final int electionHeartbeatFactor) {
        this.electionHeartbeatFactor = electionHeartbeatFactor;
    }

    public int getApplyBatch() {
        return this.applyBatch;
    }

    public void setApplyBatch(final int applyBatch) {
        this.applyBatch = applyBatch;
    }

    public boolean isSync() {
        return this.sync;
    }

    public void setSync(final boolean sync) {
        this.sync = sync;
    }

    public boolean isSyncMeta() {
        return this.sync || this.syncMeta;
    }

    public void setSyncMeta(final boolean syncMeta) {
        this.syncMeta = syncMeta;
    }

    public boolean isOpenStatistics() {
        return this.openStatistics;
    }

    public void setOpenStatistics(final boolean openStatistics) {
        this.openStatistics = openStatistics;
    }

    @Override
    public RaftOptions copy() {
        final RaftOptions raftOptions = new RaftOptions();
        raftOptions.setMaxByteCountPerRpc(this.maxByteCountPerRpc);
        raftOptions.setFileCheckHole(this.fileCheckHole);
        raftOptions.setMaxEntriesSize(this.maxEntriesSize);
        raftOptions.setMaxBodySize(this.maxBodySize);
        raftOptions.setMaxAppendBufferSize(this.maxAppendBufferSize);
        raftOptions.setMaxElectionDelayMs(this.maxElectionDelayMs);
        raftOptions.setElectionHeartbeatFactor(this.electionHeartbeatFactor);
        raftOptions.setApplyBatch(this.applyBatch);
        raftOptions.setSync(this.sync);
        raftOptions.setSyncMeta(this.syncMeta);
        raftOptions.setOpenStatistics(this.openStatistics);
        raftOptions.setReplicatorPipeline(this.replicatorPipeline);
        raftOptions.setMaxReplicatorInflightMsgs(this.maxReplicatorInflightMsgs);
        raftOptions.setDisruptorBufferSize(this.disruptorBufferSize);
        raftOptions.setDisruptorPublishEventWaitTimeoutSecs(this.disruptorPublishEventWaitTimeoutSecs);
        raftOptions.setEnableLogEntryChecksum(this.enableLogEntryChecksum);
        raftOptions.setReadOnlyOptions(this.readOnlyOptions);
        return raftOptions;
    }

    @Override
    public String toString() {
        return "RaftOptions{" + "maxByteCountPerRpc=" + this.maxByteCountPerRpc + ", fileCheckHole="
               + this.fileCheckHole + ", maxEntriesSize=" + this.maxEntriesSize + ", maxBodySize=" + this.maxBodySize
               + ", maxAppendBufferSize=" + this.maxAppendBufferSize + ", maxElectionDelayMs="
               + this.maxElectionDelayMs + ", electionHeartbeatFactor=" + this.electionHeartbeatFactor
               + ", applyBatch=" + this.applyBatch + ", sync=" + this.sync + ", syncMeta=" + this.syncMeta
               + ", openStatistics=" + this.openStatistics + ", replicatorPipeline=" + this.replicatorPipeline
               + ", maxReplicatorInflightMsgs=" + this.maxReplicatorInflightMsgs + ", disruptorBufferSize="
               + this.disruptorBufferSize + ", disruptorPublishEventWaitTimeoutSecs="
               + this.disruptorPublishEventWaitTimeoutSecs + ", enableLogEntryChecksum=" + this.enableLogEntryChecksum
               + ", readOnlyOptions=" + this.readOnlyOptions + '}';
    }
}
