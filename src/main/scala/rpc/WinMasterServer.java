package rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 * Created by lenovo on 2018/7/24.
 */
public class WinMasterServer implements WinMasterFace {
    @Override
    public String doSomething(String str) {
        return str;
    }

    @Override
    public String getBestUserForAd() {
        return null;
    }

    @Override
    public String getBestUserForCourse() {
        return null;
    }

    @Override
    public String getBestCourseAndAdForUser() {
        return null;
    }

    public static void main(String[] args) throws  Exception {
        RPC.Server server = new RPC.Builder(new Configuration())
                .setProtocol(WinMasterFace.class)
                .setInstance(new WinMasterServer())
                .setBindAddress("188.180.0.121")
                .setPort(44444)
                .build();
        server.start();
    }

}
