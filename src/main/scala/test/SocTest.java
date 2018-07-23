package test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lenovo on 2018/7/16.
 */
public class SocTest {
    public static void main(String[] args) throws Exception{
        // 要连接的服务端IP地址和端口
        try {
            Socket s = new Socket("188.180.0.121",44444);

            //构建IO
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            //向服务器端发送一条消息
                bw.write("aaaaasdfkjaslkdfja;lksdjflk;asjdlkf; abcd abcd \n");
                //bw.flush();
                //bw.close();
            bw.flush();
            bw.close();

            //读取服务器返回的消息
            //BufferedReader br = new BufferedReader(new InputStreamReader(is));
           // String mess = br.readLine();
            //System.out.println("服务器："+mess);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
