package cn.itcast.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/6/26.
 */
public class TAccountResigterInfo  implements Serializable{
    /*iAccountId	int
    sUserName	varchar
    sPhone	varchar
    sWbOpenId	varchar
    sWxOpenId	varchar
    sQQOpenId	varchar
    sQqUnionId	varchar
    sWxUnionId	varchar
    sProvince	varchar
    sCity	varchar
    iFrom	int
    iUserType	int
    sDUA	varchar
    sIMEI	varchar
    time	int
    sIP	char*/
    private Integer iAccountId;
    private String sUserName;
    private String sPhone;
    private String sWbOpenId;
    private String sWxOpenId;
    private String sQQOpenId;
    private String sQqUnionId;
    private String sProvince;
    private String sCity;
    private Integer iFrom;
    private Integer iUserType;
    private String sDUA;
    private String sIMEI;
    private Integer time;
    private String timeStr;
    private String sIP;
    private String brand;
    private String model;
    private String channel;


    public TAccountResigterInfo() {

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

    public Integer getiAccountId() {
        return iAccountId;
    }

    public void setiAccountId(Integer iAccountId) {
        this.iAccountId = iAccountId;
    }

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsWbOpenId() {
        return sWbOpenId;
    }

    public void setsWbOpenId(String sWbOpenId) {
        this.sWbOpenId = sWbOpenId;
    }

    public String getsWxOpenId() {
        return sWxOpenId;
    }

    public void setsWxOpenId(String sWxOpenId) {
        this.sWxOpenId = sWxOpenId;
    }

    public String getsQQOpenId() {
        return sQQOpenId;
    }

    public void setsQQOpenId(String sQQOpenId) {
        this.sQQOpenId = sQQOpenId;
    }

    public String getsQqUnionId() {
        return sQqUnionId;
    }

    public void setsQqUnionId(String sQqUnionId) {
        this.sQqUnionId = sQqUnionId;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public Integer getiFrom() {
        return iFrom;
    }

    public void setiFrom(Integer iFrom) {
        this.iFrom = iFrom;
    }

    public Integer getiUserType() {
        return iUserType;
    }

    public void setiUserType(Integer iUserType) {
        this.iUserType = iUserType;
    }

    public String getsDUA() {
        return sDUA;
    }

    public void setsDUA(String sDUA) {
        this.sDUA = sDUA;
    }

    public String getsIMEI() {
        return sIMEI;
    }

    public void setsIMEI(String sIMEI) {
        this.sIMEI = sIMEI;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getsIP() {
        return sIP;
    }

    public void setsIP(String sIP) {
        this.sIP = sIP;
    }
}
