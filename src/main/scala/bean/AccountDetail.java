package bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/7/12.
 */
public class AccountDetail implements Serializable{
    private	 Integer iAccountId;
    private	 String	sUserName;
    private	 String	sPhone;
    private	 String	sWxOpenId;
    private	 String	sQqOpenId;
    private	 String	sWbOpenId;
    private	 String	sWxUnionId;
    private	 String	sQqUnionId;
    private	 String	sFaceUrl;
    private	 String	iUpdateTime;
    private	 Integer	iGender;
    private	 String	sProvince;
    private	 String	sCity;
    private	 String	sAddress;
    private	 String	sProfile;
    private	 String	sVerifyDesc;
    private	 Integer iUserType;
    private	 String	stMember;
    private	 String	sUserRealName;
    private	 String	sUserIDNumber;
    private	 String iGenTimeStamp;
    private	 String	sXcxPhoneNum;
    private	 Integer iSharesAge;
    private	 String	sEmail;
    private	 String	sSource;
    private String timeStr;

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

    public Integer getiGender() {
        return iGender;
    }

    public void setiGender(Integer iGender) {
        this.iGender = iGender;
    }

    public Integer getiUserType() {
        return iUserType;
    }

    public void setiUserType(Integer iUserType) {
        this.iUserType = iUserType;
    }

    public String getiGenTimeStamp() {
        return iGenTimeStamp;
    }

    public void setiGenTimeStamp(String iGenTimeStamp) {
        this.iGenTimeStamp = iGenTimeStamp;
    }

    public Integer getiSharesAge() {
        return iSharesAge;
    }

    public void setiSharesAge(Integer iSharesAge) {
        this.iSharesAge = iSharesAge;
    }

    public AccountDetail() {
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

    public String getsWxOpenId() {
        return sWxOpenId;
    }

    public void setsWxOpenId(String sWxOpenId) {
        this.sWxOpenId = sWxOpenId;
    }

    public String getsQqOpenId() {
        return sQqOpenId;
    }

    public void setsQqOpenId(String sQqOpenId) {
        this.sQqOpenId = sQqOpenId;
    }

    public String getsWbOpenId() {
        return sWbOpenId;
    }

    public void setsWbOpenId(String sWbOpenId) {
        this.sWbOpenId = sWbOpenId;
    }

    public String getsWxUnionId() {
        return sWxUnionId;
    }

    public void setsWxUnionId(String sWxUnionId) {
        this.sWxUnionId = sWxUnionId;
    }

    public String getsQqUnionId() {
        return sQqUnionId;
    }

    public void setsQqUnionId(String sQqUnionId) {
        this.sQqUnionId = sQqUnionId;
    }

    public String getsFaceUrl() {
        return sFaceUrl;
    }

    public void setsFaceUrl(String sFaceUrl) {
        this.sFaceUrl = sFaceUrl;
    }

    public String getiUpdateTime() {
        return iUpdateTime;
    }

    public void setiUpdateTime(String iUpdateTime) {
        this.iUpdateTime = iUpdateTime;
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

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsProfile() {
        return sProfile;
    }

    public void setsProfile(String sProfile) {
        this.sProfile = sProfile;
    }

    public String getsVerifyDesc() {
        return sVerifyDesc;
    }

    public void setsVerifyDesc(String sVerifyDesc) {
        this.sVerifyDesc = sVerifyDesc;
    }

    public String getStMember() {
        return stMember;
    }

    public void setStMember(String stMember) {
        this.stMember = stMember;
    }

    public String getsUserRealName() {
        return sUserRealName;
    }

    public void setsUserRealName(String sUserRealName) {
        this.sUserRealName = sUserRealName;
    }

    public String getsUserIDNumber() {
        return sUserIDNumber;
    }

    public void setsUserIDNumber(String sUserIDNumber) {
        this.sUserIDNumber = sUserIDNumber;
    }


    public String getsXcxPhoneNum() {
        return sXcxPhoneNum;
    }

    public void setsXcxPhoneNum(String sXcxPhoneNum) {
        this.sXcxPhoneNum = sXcxPhoneNum;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsSource() {
        return sSource;
    }

    public void setsSource(String sSource) {
        this.sSource = sSource;
    }
}
