package bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/7/12.
 */
@Alias("AccountDetail")
public class AccountDetail implements Serializable{
    private	 Integer iAccountId;
    private	 String	sPhone;
    private String sDUA;
    public String getsDUA() {
        return sDUA;
    }
    public void setsDUA(String sDUA) {
        this.sDUA = sDUA;
    }
    public Integer getiAccountId() {
        return iAccountId;
    }

    public void setiAccountId(Integer iAccountId) {
        this.iAccountId = iAccountId;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
}
