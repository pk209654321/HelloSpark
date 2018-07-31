package rpc;

/**
 * Created by lenovo on 2018/7/24.
 */
public interface WinMasterFace {
    long versionID = 1;//该字段必须要有，不然会报java.lang.NoSuchFieldException: versionID异常
    public String doSomething(String str);
    //public String

    //点击老师获得最佳用户
    public String getBestUserForAd();

    //点击课程获得最佳用户
    public String getBestUserForCourse();

    //点击用户获得最佳课程和Ad
    public String getBestCourseAndAdForUser();


}
