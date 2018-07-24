package rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.net.InetSocketAddress;

/**
 * Created by lenovo on 2018/7/24.
 */
public class WinMasterClient {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        WinMasterFace proxy = RPC.getProxy(WinMasterFace.class, 1,new InetSocketAddress("188.180.0.121", 44444) , new Configuration());
        String result = proxy.doSomething("服务端");
        System.out.println(result);
        RPC.stopProxy(proxy);
    }

}
