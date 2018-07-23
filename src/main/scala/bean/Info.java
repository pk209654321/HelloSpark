package bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/6/27.
 */
public class Info implements Serializable{
   /* accountId	bigint
    userName	varchar
    phone	varchar
    brand	varchar
    model	varchar
    channel	varchar
    timeStr	varchar*/

    private Long accountId;
    private String userName;
    private String phone;
    private String brand;
    private String model;
    private String channel;
    private String timeStr;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Info() {

    }

    public Info(Long accountId, String userName, String phone, String brand, String model, String channel, String timeStr) {
        this.accountId = accountId;
        this.userName = userName;
        this.phone = phone;
        this.brand = brand;
        this.model = model;
        this.channel = channel;
        this.timeStr = timeStr;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
