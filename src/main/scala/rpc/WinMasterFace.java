package rpc;

/**
 * Created by lenovo on 2018/7/24.
 */
public interface WinMasterFace {
    long versionID = 1;//该字段必须要有，不然会报java.lang.NoSuchFieldException: versionID异常
    public String doSomething(String str);
    //public String

}
