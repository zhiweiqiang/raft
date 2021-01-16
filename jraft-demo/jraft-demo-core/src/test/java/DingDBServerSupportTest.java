import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.sxlg.demo.model.core.command.GetRequestProcessor;
import com.sxlg.demo.model.core.command.PutRequestProcessor;
import com.sxlg.demo.model.core.log.DingDBStateMachine;
import com.sxlg.demo.model.core.support.DingDBServerSupport;
import com.sxlg.demo.model.core.support.InitializeOptions;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class DingDBServerSupportTest {

    @Test
    public void supportTest() throws IOException, InterruptedException {

        String dataPath1 = "/Users/qiangzhiwei/reft/test1";
        String dataPath2 = "/Users/qiangzhiwei/reft/test2";
        String dataPath3 = "/Users/qiangzhiwei/reft/test3";
        FileUtils.forceMkdir(new File(dataPath1));
        FileUtils.forceMkdir(new File(dataPath2));
        FileUtils.forceMkdir(new File(dataPath3));
        DingDBServerSupport dingDBServerSupport = new DingDBServerSupport("127.0.0.1:8080");
        dingDBServerSupport
                .groupId("test")
                .cluster("127.0.0.1:8080,127.0.0.1:8081,127.0.0.1:8082")
                .nodeOptions(new InitializeOptions() {
                    @Override
                    public void init(NodeOptions nodeOptions) {
                        // 为了测试,调整 snapshot 间隔等参数
                        // 设置选举超时时间为 1 秒
                        nodeOptions.setElectionTimeoutMs(1000);
                        // 关闭 CLI 服务。
                        nodeOptions.setDisableCli(false);
                        // 每隔30秒做一次 snapshot
                        nodeOptions.setSnapshotIntervalSecs(30);
                        nodeOptions.setFsm(new DingDBStateMachine());
                        // 设置存储路径
                        // 日志, 必须
                        nodeOptions.setLogUri(dataPath1 + File.separator + "log");
                        // 元信息, 必须
                        nodeOptions.setRaftMetaUri(dataPath1 + File.separator + "raft_meta");
                        // snapshot, 可选, 一般都推荐
                        nodeOptions.setSnapshotUri(dataPath1 + File.separator + "snapshot");
                    }
                })
                .registerProcessor(new PutRequestProcessor())
                .registerProcessor(new GetRequestProcessor())
                .build()
                .start();

        DingDBServerSupport dingDBServerSupport1 = new DingDBServerSupport("127.0.0.1:8081");
        dingDBServerSupport1
                .groupId("test")
                .cluster("127.0.0.1:8080,127.0.0.1:8081,127.0.0.1:8082")
                .nodeOptions(new InitializeOptions() {
                    @Override
                    public void init(NodeOptions nodeOptions) {
                        // 为了测试,调整 snapshot 间隔等参数
                        // 设置选举超时时间为 1 秒
                        nodeOptions.setElectionTimeoutMs(1000);
                        // 关闭 CLI 服务。
                        nodeOptions.setDisableCli(false);
                        // 每隔30秒做一次 snapshot
                        nodeOptions.setSnapshotIntervalSecs(30);
                        nodeOptions.setFsm(new DingDBStateMachine());
                        // 设置存储路径
                        // 日志, 必须
                        nodeOptions.setLogUri(dataPath2 + File.separator + "log");
                        // 元信息, 必须
                        nodeOptions.setRaftMetaUri(dataPath2 + File.separator + "raft_meta");
                        // snapshot, 可选, 一般都推荐
                        nodeOptions.setSnapshotUri(dataPath2 + File.separator + "snapshot");
                    }
                })
                .registerProcessor(new PutRequestProcessor())
                .registerProcessor(new GetRequestProcessor())
                .build()
                .start();
        DingDBServerSupport dingDBServerSupport2 = new DingDBServerSupport("127.0.0.1:8082");
        dingDBServerSupport2
                .groupId("test")
                .cluster("127.0.0.1:8080,127.0.0.1:8081,127.0.0.1:8082")
                .nodeOptions(new InitializeOptions() {
                    @Override
                    public void init(NodeOptions nodeOptions) {
                        // 为了测试,调整 snapshot 间隔等参数
                        // 设置选举超时时间为 1 秒
                        nodeOptions.setElectionTimeoutMs(1000);
                        // 关闭 CLI 服务。
                        nodeOptions.setDisableCli(false);
                        // 每隔30秒做一次 snapshot
                        nodeOptions.setSnapshotIntervalSecs(30);
                        nodeOptions.setFsm(new DingDBStateMachine());
                        // 设置存储路径
                        // 日志, 必须
                        nodeOptions.setLogUri(dataPath3 + File.separator + "log");
                        // 元信息, 必须
                        nodeOptions.setRaftMetaUri(dataPath3 + File.separator + "raft_meta");
                        // snapshot, 可选, 一般都推荐
                        nodeOptions.setSnapshotUri(dataPath3 + File.separator + "snapshot");
                    }
                })
                .registerProcessor(new PutRequestProcessor())
                .registerProcessor(new GetRequestProcessor())
                .build()
                .start();
                new CountDownLatch(1).await();
    }
}
