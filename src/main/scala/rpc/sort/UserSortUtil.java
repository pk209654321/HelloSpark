package rpc.sort;

/**
 * Created by lenovo on 2018/7/24.
 */
public class UserSortUtil {
    //对数据进行归一化处理
    //(x-min) / (max-min)数据归一化处理
    /* normalizedFunction(num:Int,min:Int,max:Int): Int ={
        num-min/max-min
    }*/
    //对数据进行归一化处理
    public static int normalizedFunction(int num,int min,int max){
        return num-min/max-min;
    }

    //找到最小值
    public static int findMin(){
        return 0;
    }

    //找到最大值
    public static int findMax(){
        return 0;
    }
}
